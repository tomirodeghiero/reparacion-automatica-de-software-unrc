package ar.edu.unrc.exa.dc.operators;

import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.ast.expr.BinaryExpr;

import ar.edu.unrc.exa.dc.collectors.BinaryExpressionCollector;

public class ArithmeticOperatorMutator implements MutationOperator<BinaryExpr> {

    @Override
    public List<Mutation> mutate(BinaryExpr original) {
        return generateMutants(original);
    }

    private List<Mutation> generateMutants(BinaryExpr original) {
        List<Mutation> mutations = new LinkedList<>();
        for (BinaryExpr.Operator op : BinaryExpressionCollector.arithmeticOperators()) {
            if (op != original.getOperator()) {
                BinaryExpr mutated = original.clone();
                mutated.setOperator(op);
                mutations.add(new Mutation(original, mutated));
            }
        }
        return mutations;
    }
}
