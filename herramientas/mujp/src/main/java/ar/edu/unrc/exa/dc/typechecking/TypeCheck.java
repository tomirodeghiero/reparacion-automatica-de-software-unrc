package ar.edu.unrc.exa.dc.typechecking;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import ar.edu.unrc.exa.dc.Config;
import ar.edu.unrc.exa.dc.logging.Logging;
import ar.edu.unrc.exa.dc.typechecking.utils.TypeUtils;

/**
 * This class is responsible for all type checks needed
 */
public final class TypeCheck {

    private static TypeCheck instance;
    private final CombinedTypeSolver typeSolver;
    private final boolean useSourceTempDir;
    private boolean updatedNoFileClassTypeSolver = false;

    private static final Logger logger = Logging.getLogger(TypeCheck.class, Logging.LoggingLevel.OFF, Logging.LoggingLevel.FINE);

    public static TypeCheck getInstance() {
        if (instance == null) {
            instance = new TypeCheck();
        }
        return instance;
    }

    private TypeCheck() {
        logger.info("Initializing TypeCheck");
        typeSolver = new CombinedTypeSolver(
            new ReflectionTypeSolver()  // Basic type solver (JDK classes)
        );
        String sourceRootFolder = Config.getInstance().sourceRootFolder();
        if (sourceRootFolder != null) {
            JavaParserTypeSolver javaParserTypeSolver = new JavaParserTypeSolver(sourceRootFolder);
            typeSolver.add(javaParserTypeSolver);
            logger.info(String.format("Adding JavaParserTypeSolver for %s", sourceRootFolder));
            useSourceTempDir = false;
        } else {
            logger.info("No root folder provided, you will need to call updateForNoFileClass");
            useSourceTempDir = true;
        }
        logger.info("TypeCheck initialized");
    }

    /**
     * When parsing Java code from a string (instead of from a file), this method will
     * update this {@code TypeCheck} instance so it can perform type checks.
     * 
     * @param cu : The {@code CompilationUnit} obtained from parsing a string-based java code
     * @return {@code true} iff it was required to call this method, the method was not called before, and the update process was successful
     * @throws IOException if there was an issue creating either a necessary temporary directory or java file.
     */
    public boolean updateForNoFileClass(CompilationUnit cu) throws IOException {
        if (!useSourceTempDir) {
            logger.warning("This TypeCheck was already built from an existing source root folder");
            return false;
        } else if (updatedNoFileClassTypeSolver) {
            logger.warning("This method was already called");
            return false;
        } else {
            Optional<ClassOrInterfaceDeclaration> firstClass = cu.findFirst(ClassOrInterfaceDeclaration.class);
            if (firstClass.isEmpty()) {
                logger.severe("There is no class or interface declared in the given compilation unit");
                return false;
            } else {
                String className = firstClass.get().getNameAsString();
                Path temp = Files.createTempDirectory("mujp-tmp-sut");
                Path file = temp.resolve(className + ".java");
                DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
                String code = printer.print(cu);
                Files.write(file, code.getBytes(StandardCharsets.UTF_8));
                logger.info(
                    String.format(
                        "Saved code to %s, setting up JavaParseTypeSolver root folder to %s",
                        file.toAbsolutePath().toString(),
                        temp.toAbsolutePath().toString()
                    )
                );
                JavaParserTypeSolver javaParserTypeSolver = new JavaParserTypeSolver(temp);
                typeSolver.add(javaParserTypeSolver);
                logger.info(String.format("Adding JavaParserTypeSolver for %s", temp.toAbsolutePath().toString()));
                updatedNoFileClassTypeSolver = true;
                return true;
            }
        }
        
    }

    /**
     * Checks whether an expression is compatible (i.e.: can be assigned) to a specific class
     * 
     * @param expression : the expression
     * @param clazz : the class to which {@code expression} should be assignable
     * @param strict : if the compatibility check is strict (same type) or compatible (can be assigned)
     * @return {@code true} iff {@code expression} can be assigned to {@code clazz}
     */
    public boolean isCompatibleWith(Node n, Class<?> clazz, boolean strict) {
        ResolvedType t = JavaParserFacade.get(typeSolver).getType(n);
        logger.fine(
            String.format(
                "Node %s to be checked against class %s with %s type checking and %s primitive wrapping",
                n.toString(),
                clazz.getName(),
                strict?"strict":"non-strict",
                Config.getInstance().allowPrimitiveWrapping()?"allowing":"disallowing"
            )
        );
        if (strict) {
            return isExactType(t, clazz, Config.getInstance().allowPrimitiveWrapping());
        } else {
            return isCompatible(t, clazz, Config.getInstance().allowPrimitiveWrapping());
        }
    }


    /*
     * Checks whether the type of a node from the ast tree is compatible with a specific type
     * in the sense that a value for the node's type can be assigned to the provided class
     * @param t : the type of a node from the ast
     * @param with : the class to check compatibility with
     * @param allowWrapping : if wrappers are allows or not
     * @return {@code true} iff a value of type {@code t} can be assigned to the class {@code clazz}
     */
    private boolean isCompatible(ResolvedType t, Class<?> with, boolean allowWrapping) {
        if (TypeUtils.isPrimitive(t, allowWrapping)) {
            return primitiveMatches(t, with, allowWrapping, false);
        } else {
            Class<?> tAsClass = TypeUtils.toClass(t);
            if (tAsClass == null) {
                logger.warning(
                    String.format(
                        "Was not able to get Java class for ResolvedType %s",
                        t.toString()
                    )
                );
                return false;
            }
            return with.isAssignableFrom(tAsClass);
        }
    }

    /*
     * Checks whether the type of a node from the ast tree is the exact same as a specific type
     * @param t : the type of a node from the ast
     * @param clazz : the class to check compatibility with
     * @param allowWrapping : if wrappers are allows or not
     * @return {@code true} iff a type {@code t} is exactly the same as {@code clazz} modulo wrapper allowance
     */
    private boolean isExactType(ResolvedType t, Class<?> clazz, boolean allowWrapping) {
        
        if (TypeUtils.isPrimitive(t, allowWrapping) || clazz.isPrimitive()) {
            logger.fine(
                String.format(
                    "Checking exact type matching for ResolvedType %s against class %s, while %s wrapping",
                    t.toString(),
                    clazz.getName(),
                    allowWrapping?"allowing":"disallowing"
                )
            );
            return primitiveMatches(t, clazz, allowWrapping, true);
        }
    
        if (t.isReferenceType()) {
            String qname = t.asReferenceType().getQualifiedName();
            boolean matches = qname.equals(clazz.getCanonicalName());
            logger.fine(
                String.format(
                    "Checking exact type matching for ResolvedType %s against class %s (%s)",
                    t.toString(),
                    clazz.getName(),
                    matches?"match":"do not match"
                )
            );
            return matches;
        }
    
        return false;
    }
    

    private boolean primitiveMatches(ResolvedType p, Class<?> clazz, boolean allowWrapping, boolean strict) {
        if (clazz == void.class || (allowWrapping && clazz == Void.class)) {
            logger.fine(
                String.format(
                    "ResolvedType %s does not match class %s",
                    p.toString(),
                    clazz.getName()
                )
            );
            return p.isVoid();
        }
        
        if (!TypeUtils.isPrimitive(p, allowWrapping)) {
            logger.fine(
                String.format(
                    "ResolvedType %s is not a primitive, nor a primitive wrapper",
                    p.toString()
                )
            );
            return false;
        } else {
            Class<?> pAsClass = p.isPrimitive()?
                                    allowWrapping?
                                    TypeUtils.toWrapper(TypeUtils.toPrimitiveClass(p))
                                    :TypeUtils.toPrimitiveClass(p)
                                :TypeUtils.toClass(p);
            Class<?> wrappedClazz = clazz.isPrimitive() && allowWrapping?
                                    TypeUtils.toWrapper(clazz)
                                    :clazz;
            boolean nonStrictMatches = !strict && wrappedClazz.isAssignableFrom(pAsClass);
            boolean matches = nonStrictMatches || wrappedClazz.equals(pAsClass);
            logger.fine(
                String.format(
                    "ResolvedType %s is %s, %s %s class %s (wrapping %s)",
                    p.toString(),
                    p.isPrimitive()?"primitive":"primitive wrapper",
                    strict?"strictly":"non-strictly",
                    matches?"matches":"does not match",
                    wrappedClazz.getName(),
                    allowWrapping?"allowed":"disallowed"
                )
            );
            return matches;
        }
    }
    
}
