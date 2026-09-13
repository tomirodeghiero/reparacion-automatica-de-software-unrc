package ar.edu.unrc.exa.dc.typechecking.utils;

import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;

/**
 * Type utilities
 */
public final class TypeUtils {

    private TypeUtils() {}

    /**
     * Given a resolved type, this will return the associated Java class
     * 
     * @param t : ResolvedType to transform
     * @return a class {@code c} such that {@code t} represents {@code c}
     * @see {@link com.github.javaparser.resolution.types.ResolvedType}
     */
    public static Class<?> toClass(ResolvedType t) {
        // void
        if (t.isVoid()) {
            return void.class;
        }

        // primitives
        if (t.isPrimitive()) {
            return toPrimitiveClass(t);
        }

        // arrays
        if (t.isArray()) {
            Class<?> component = toClass(t.asArrayType().getComponentType());
            if (component != null) {
                return java.lang.reflect.Array.newInstance(component, 0).getClass();
            }
            return null;
        }

        // reference types
        if (t.isReferenceType()) {
            String qname = t.asReferenceType().getQualifiedName();
            try {
                return Class.forName(qname);
            } catch (ClassNotFoundException e) {
                return null; // not loadable by JVM
            }
        }

        // type variables, wildcards, intersections, etc.
        return null;
    }

    /**
     * Given a {@code ResolvedType} representing a primitive type, this will return
     * the associated Java class.
     * 
     * @param t : the {@code ResolvedType} to transform
     * @return a class {@code c} such that {@code c} represents a primitive class
     * and {@code c} matches the type represented by {@code t}
     * @throws IllegalArgumemntException if {@code t} is not primitive
     */
    public static Class<?> toPrimitiveClass(ResolvedType t) {
        if (t.isPrimitive()) {
            if (t == ResolvedPrimitiveType.INT) return int.class;
            if (t == ResolvedPrimitiveType.BOOLEAN) return boolean.class;
            if (t == ResolvedPrimitiveType.DOUBLE) return double.class;
            if (t == ResolvedPrimitiveType.LONG) return long.class;
            if (t == ResolvedPrimitiveType.FLOAT) return float.class;
            if (t == ResolvedPrimitiveType.CHAR) return char.class;
            if (t == ResolvedPrimitiveType.BYTE) return byte.class;
            if (t == ResolvedPrimitiveType.SHORT) return short.class;
        }
        throw new IllegalArgumentException(
            String.format(
                "ResolvedType %s is either not primitive or not a supported one",
                t.toString()
            )
        );
    }

    /**
     * Given a Java class representing a primitive type this method will
     * return the associated wrapper class
     * 
     * @param clazz : the primitive type for which to get the wrapper class
     * @return a class {@code w} such that {@code w} is a wrapper class for primitive type {@code clazz}
     * @throws IllegalArgumentException if {@code clazz} is not a primitive type
     */
    public static Class<?> toWrapper(Class<?> clazz) {
        if (isWrapper(clazz)) {
            return clazz;
        }
        if (clazz.isPrimitive()) {
            if (clazz.equals(int.class)) return Integer.class;
            if (clazz.equals(boolean.class)) return Boolean.class;
            if (clazz.equals(double.class)) return Double.class;
            if (clazz.equals(long.class)) return Long.class;
            if (clazz.equals(float.class)) return Float.class;
            if (clazz.equals(char.class)) return Character.class;
            if (clazz.equals(byte.class)) return Byte.class;
            if (clazz.equals(short.class)) return Short.class;
        }
        throw new IllegalArgumentException(
            String.format(
                "%s is either not a primitive or not a supported one",
                clazz.toString()
            )
        );
    }

    /**
     * Checks whether a Java class is a primitive wrapper or not
     * 
     * @param clazz : the Java class to check
     * @return {@code true} iff {@code clazz} represents a primitive wrapper
     */
    public static boolean isWrapper(Class<?> clazz) {
        return (clazz.equals(Integer.class))
                || (clazz.equals(Boolean.class))
                || (clazz.equals(Double.class))
                || (clazz.equals(Long.class))
                || (clazz.equals(Float.class)) 
                || (clazz.equals(Character.class))
                || (clazz.equals(Byte.class))
                || (clazz.equals(Short.class));
    }

    /**
     * Returns whether a {@code ResolvedType} is a primitive type, it can also consider
     * wrapper classes if needed.
     * 
     * @param t : the {@code ResolvedType} to check
     * @param allowWrapping : if wrapper classes are considered or not
     * @return {@code true} iff {@code t} is a primitive type or a wrapper for one if {@code allowWrapping} is {@code true}
     */
    public static boolean isPrimitive(ResolvedType t, boolean allowWrapping) {
        if (t.isPrimitive()) {
            return true;
        } else if (allowWrapping) {
            Class<?> tAsClass = toClass(t);
            return  tAsClass.equals(Integer.class)
                || tAsClass.equals(Double.class)
                || tAsClass.equals(Short.class)
                || tAsClass.equals(Long.class)
                || tAsClass.equals(Byte.class)
                || tAsClass.equals(Float.class)
                || tAsClass.equals(Boolean.class)
                || tAsClass.equals(Character.class);
        } else {
            return false;
        }
    }

}

