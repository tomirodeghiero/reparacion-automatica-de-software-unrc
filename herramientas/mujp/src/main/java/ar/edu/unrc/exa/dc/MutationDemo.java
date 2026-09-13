package ar.edu.unrc.exa.dc;

import ar.edu.unrc.exa.dc.collectors.BinaryExpressionCollector;
import ar.edu.unrc.exa.dc.operators.*;
import ar.edu.unrc.exa.dc.printer.MutantPrinter;
import ar.edu.unrc.exa.dc.typechecking.TypeCheck;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MutationDemo {

    public static void main(String[] args) throws IOException {

        String code = ""
            + "class A { "
            + "  int f(int a, int b) { return a + b * 3; } "
            + "}";

        CompilationUnit cu = StaticJavaParser.parse(code);
        TypeCheck.getInstance().updateForNoFileClass(cu);

        // 1. Collect mutation points
        List<BinaryExpr> points = new ArrayList<>();
        BinaryExpressionCollector.arithmeticOperators.visit(cu, points);

        // 2. Mutation operator
        ArithmeticOperatorMutator operator = new ArithmeticOperatorMutator();

        // 3. Printer configuration
        DefaultPrinterConfiguration config = new DefaultPrinterConfiguration();

        // 4. Generate mutants
        int id = 1;
        for (BinaryExpr point : points) {
            List<Mutation> mutations = operator.mutate(point);
            for (Mutation mutation : mutations) {
                MutantPrinter printer = new MutantPrinter(mutation, config);
                printer.visit(cu, null);
                String mutantSource = printer.toString();
                System.out.println("---- MUTANT " + id++ + " ----");
                System.out.println(mutantSource);
            }
        }
    }
}