package ar.edu.unrc.exa.dc.operators;

import java.util.List;

import com.github.javaparser.ast.Node;

public interface MutationOperator<T extends Node> {
    List<Mutation> mutate(T original);
}