package ar.edu.unrc.exa.dc.operators;

import com.github.javaparser.ast.Node;

public class Mutation {
    public final Node original;
    public final Node mutant;

    public Mutation(Node original, Node mutant) {
        this.original = original;
        this.mutant = mutant;
    }
}