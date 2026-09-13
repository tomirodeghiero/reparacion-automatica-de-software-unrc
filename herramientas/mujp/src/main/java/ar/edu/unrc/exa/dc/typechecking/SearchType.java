package ar.edu.unrc.exa.dc.typechecking;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;

/**
 * This class represents a type objective for collectors such as {@link ar.edu.unrc.exa.dc.collectors.BinaryExpressionCollector}
 */
public final class SearchType {

    /**
     * search for any node
     */
    public static final SearchType ANY = new SearchType(NodeType.ANY, TypeKind.COMPATIBLE, Object.class, false);
    
    /**
     * search for any expression node
     */
    public static final SearchType ANY_EXPRESSION = new SearchType(NodeType.EXPRESSION, TypeKind.COMPATIBLE, Object.class, false);
    
    /**
     * search for any declaration node
     * @see {@link ar.edu.unrc.exa.dc.typechecking.SearchType.NodeType}
     */
    public static final SearchType ANY_DECLARATION = new SearchType(NodeType.TYPED_DECLARATION, TypeKind.COMPATIBLE, Object.class, false);

    /**
     * The kind of SearchType to do
     */
    public enum TypeKind {
        /**
         * Search for boolean types
         */
        BOOLEAN,
        /**
         * Search for arithmetic types
         */
        ARITHMETIC,
        /**
         * Strict Type search
         */
        STRICT,
        /**
         * Compatible Type search
         */
        COMPATIBLE
    }

    /**
     * The type of nodes to consider
     */
    public enum NodeType {
        /**
         * Only expressions
         */
        EXPRESSION,
        /**
         * Only declarations that have an associated type
         * (methods, variable declarations, method arguments, and field declarations)
         */
        TYPED_DECLARATION,
        /**
         * Any type of node
         */
        ANY
    }

    private final TypeKind kind;
    private final Class<?> clazz;
    private final boolean strict;
    private final NodeType nodeType; 

    private SearchType(NodeType nodeType, TypeKind kind, Class<?> clazz, boolean strict) {
        this.nodeType = nodeType;
        this.kind = kind;
        this.clazz = clazz;
        this.strict = strict;
    }

    /**
     * Creates a {@code SearchType} instance for boolean expressions with non-strict
     * type checking
     * 
     * @return an instance of {@code SearchType} for boolean expressions
     */
    public static SearchType booleanType() {
        return new SearchType(NodeType.EXPRESSION, TypeKind.BOOLEAN, null, false);
    }

    /**
     * Creates a {@code SearchType} instance for arithmetic expressions with non-strict
     * type checking
     * 
     * @return an instance of {@code SearchType} for arithmetic expressions
     */
    public static SearchType arithmeticType() {
        return new SearchType(NodeType.EXPRESSION, TypeKind.ARITHMETIC, null, false);
    }

    /**
     * Creates a {@code SearchType} instance for nodes with an exact type
     * 
     * @param nodeType : what nodes to consider
     * @param clazz : the type a node must have
     * @param primitivesOnly : allows primitive wrapping and unwrapping
     * @return an instance of {@code SearchType} for nodes with type {@code clazz}
     */
    public static SearchType strict(NodeType nodeType, Class<?> clazz, boolean primitivesOnly) {
        return new SearchType(nodeType, TypeKind.STRICT, clazz, primitivesOnly);
    }

    /**
     * Creates a {@code SearchType} instance for nodes compatible with a given type
     * 
     * @param nodeType : what nodes to consider
     * @param clazz : the type an expression must be compatible with
     * @param primitivesOnly : allows primitive wrapping and unwrapping
     * @return an instance of {@code SearchType} for nodes with type compatible with {@code clazz}
     */
    public static SearchType compatible(NodeType nodeType, Class<?> clazz, boolean primitivesOnly) {
        return new SearchType(nodeType, TypeKind.COMPATIBLE, clazz, primitivesOnly);
    }

    /**
     * Determines if a given node has a type acceptable with this {@code SearchType} instance
     * 
     * @param n an ast node
     * @return {@code true} iff {@code n} is acceptable according to this {@code SearchType} instance
     */
    public boolean isAcceptable(Node n) {
        if (nodeType == NodeType.TYPED_DECLARATION && !isTypedDeclaration(n)) {
            return false;
        }
        if (nodeType == NodeType.EXPRESSION && !(n instanceof Expression)) {
            return false;
        }
        if (kind == TypeKind.BOOLEAN) {
            return TypeCheck.getInstance().isCompatibleWith(n, boolean.class, true);
        } else if (kind == TypeKind.ARITHMETIC) {
            return TypeCheck.getInstance().isCompatibleWith(n, Number.class, false);
        } else {
            return TypeCheck.getInstance().isCompatibleWith(n, clazz, strict);
        }
    }

    /**
     * Determines whether an ast node is a typed declaration or not
     * the current accepted typed declarations are: methods, fields, variables, and parameters
     * 
     * @param n the ast node to check
     * @return {@code true} iff {@code n} is a declaration
     */
    public static boolean isTypedDeclaration(Node n) {
        if (n instanceof FieldDeclaration) {
            return true;
        } else if (n instanceof VariableDeclarator) {
            return true;
        } else if (n instanceof Parameter) {
            return true;
        } else if (n instanceof MethodDeclaration) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the node type to search
     */
    public NodeType getNodeType() {
        return nodeType;
    }

    /**
     * @return the type kind to use
     */
    public TypeKind getTypeKind() {
        return kind;
    }

}