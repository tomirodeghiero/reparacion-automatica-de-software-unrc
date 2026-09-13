package ar.edu.unrc.exa.dc.collectors;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.logging.Logger;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import ar.edu.unrc.exa.dc.logging.Logging;
import ar.edu.unrc.exa.dc.typechecking.SearchType;

/**
 * A binary expressions collector
 * This will collect any binary expression that matches a given set of operators and are
 * compatible with a given type.
 */
public class BinaryExpressionCollector extends VoidVisitorAdapter<List<BinaryExpr>> {

    /**
     * An arithmetic binary expression collector
     */
    public static final BinaryExpressionCollector arithmeticOperators = new BinaryExpressionCollector(SearchType.arithmeticType(), arithmeticOperators());
    
    /**
     * A boolean binary expression collector
     */
    public static final BinaryExpressionCollector booleanOperators = new BinaryExpressionCollector(SearchType.booleanType(), booleanOperators());
    
    /**
     * A conditional binary expression collector
     */
    public static final BinaryExpressionCollector conditionalOperators = new BinaryExpressionCollector(SearchType.booleanType(), conditionalOperators());
    
    /**
     * A logical binary expression collector
     */
    public static final BinaryExpressionCollector logicalOperators = new BinaryExpressionCollector(SearchType.booleanType(), logicalOperators()); 

    private final SearchType searchType;
    private final Set<BinaryExpr.Operator> operators;

    private static final Logger logger = Logging.getLogger(BinaryExpressionCollector.class, Logging.LoggingLevel.FINE, Logging.LoggingLevel.INFO);

    /**
     * Collect all binary expression nodes
     */
    public BinaryExpressionCollector() {
        this(SearchType.ANY_EXPRESSION, Set.of(BinaryExpr.Operator.values()));
    }

    /**
     * Collect all binary expression nodes that have certain operators
     * 
     * @param operators : the set of allows operators
     */
    public BinaryExpressionCollector(Set<BinaryExpr.Operator> operators) {
        this(SearchType.ANY_EXPRESSION, operators);
    }

    /**
     * Collect all binary expression nodes that have certain operators and
     * are compatible with a certain type
     * 
     * @param searchType : type restrictions
     * @param operators : the set of allows operators
     * @see {@link ar.edu.unrc.exa.dc.typechecking.SearchType}
     */
    public BinaryExpressionCollector(SearchType searchType, Set<BinaryExpr.Operator> operators) {
        if (searchType.getNodeType() != SearchType.NodeType.EXPRESSION) {
            throw new IllegalArgumentException("Only NodeType.EXPRESSION is allowed");
        }
        this.searchType = searchType;
        this.operators = operators;
    }

    @Override
    public void visit(BinaryExpr n, List<BinaryExpr> collector) {
        logger.fine(
            String.format(
                "Visiting %s (%d)",
                n.toString(),
                n.hashCode()
            )
        );
        super.visit(n, collector);
        
        Operator op = n.getOperator();
        if (!operators.contains(op)) {
            logger.fine(
                String.format(
                    "Discarding %s (%d), reason: non-matching operator",
                    n.toString(),
                    n.hashCode()
                )
            );
            return;
        }
        if (!searchType.isAcceptable(n)) {
            logger.fine(
                String.format(
                    "Discarding %s (%d), reason: non-matching type",
                    n.toString(),
                    n.hashCode()
                )
            );
            return;
        }
        logger.fine(
                String.format(
                    "Collected %s (%d)",
                    n.toString(),
                    n.hashCode()
                )
            );
        collector.add(n);
    } 
    
    // Some utility methods to improve usage

    /**
     * @return an unmodifiable set of all binary expression operators 
     */
    public static Set<BinaryExpr.Operator> allOperators() {
        return Set.of(BinaryExpr.Operator.values());
    }

    /**
     * @return an unmodifiable set of all binary expression arithmetic operators 
     */
    public static Set<BinaryExpr.Operator> arithmeticOperators() {
        return Set.of(
            BinaryExpr.Operator.PLUS,
            BinaryExpr.Operator.MINUS,
            BinaryExpr.Operator.MULTIPLY,
            BinaryExpr.Operator.DIVIDE,
            BinaryExpr.Operator.REMAINDER
        );
    }

    /**
     * @return an unmodifiable set of all binary expression boolean operators 
     */
    public static Set<BinaryExpr.Operator> booleanOperators() {
        return Set.of(
            BinaryExpr.Operator.AND,
            BinaryExpr.Operator.OR,
            BinaryExpr.Operator.XOR
        );
    }

    /**
     * @return an unmodifiable set of all binary expression comparison operators 
     */
    public static Set<BinaryExpr.Operator> comparisonOperators() {
        return Set.of(
            BinaryExpr.Operator.EQUALS,
            BinaryExpr.Operator.GREATER,
            BinaryExpr.Operator.GREATER_EQUALS,
            BinaryExpr.Operator.LESS,
            BinaryExpr.Operator.LESS_EQUALS,
            BinaryExpr.Operator.NOT_EQUALS
        );
    }

    /**
     * @return an unmodifiable set of all binary expression conditional operators 
     */
    public static Set<BinaryExpr.Operator> conditionalOperators() {
        HashSet<BinaryExpr.Operator> conditionalOperators = new HashSet<>();
        conditionalOperators.addAll(booleanOperators());
        conditionalOperators.addAll(comparisonOperators());
        return Set.copyOf(conditionalOperators);
    }

    /**
     * @return an unmodifiable set of all binary expression logical operators 
     */
    public static Set<BinaryExpr.Operator> logicalOperators() {
        return Set.of(
            BinaryExpr.Operator.BINARY_AND,
            BinaryExpr.Operator.BINARY_OR,
            BinaryExpr.Operator.LEFT_SHIFT,
            BinaryExpr.Operator.SIGNED_RIGHT_SHIFT,
            BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT,
            BinaryExpr.Operator.XOR
        );
    }

    /**
     * @return an unmodifiable set of all binary expression string append operators 
     */
    public static Set<Operator> stringOperators() {
        return Set.of(BinaryExpr.Operator.PLUS);
    }

    /**
     * @return an unmodifiable set of all binary expression bitwise operators 
     */
    public static Set<Operator> bitwiseBooleanOperators() {
        return Set.of(
            BinaryExpr.Operator.BINARY_AND,
            BinaryExpr.Operator.BINARY_OR,
            BinaryExpr.Operator.XOR
        );
    }

    /**
     * @return an unmodifiable set of all binary expression shift operators 
     */
    public static Set<Operator> shiftOperators() {
        return Set.of(
            BinaryExpr.Operator.LEFT_SHIFT,
            BinaryExpr.Operator.SIGNED_RIGHT_SHIFT,
            BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT
        );
    }

}