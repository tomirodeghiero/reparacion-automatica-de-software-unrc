package ar.edu.unrc.exa.dc.printer;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.CompactConstructorDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.comments.MarkdownComment;
import com.github.javaparser.ast.comments.TraditionalJavadocComment;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MatchAllPatternExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.RecordPatternExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.TypePatternExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.printer.DefaultPrettyPrinterVisitor;
import com.github.javaparser.printer.configuration.PrinterConfiguration;

import ar.edu.unrc.exa.dc.operators.Mutation;

public class MutantPrinter extends DefaultPrettyPrinterVisitor {

    private final Mutation mutation;

    public MutantPrinter(Mutation mutation, PrinterConfiguration configuration) {
        super(configuration);
        this.mutation = mutation;
    }

    @Override
    public void visit(final CompilationUnit n, final Void arg) {
        super.visit(n, arg);
    }

    @Override
    public void visit(NameExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(Name n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SimpleName n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(RecordDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(TraditionalJavadocComment n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ClassOrInterfaceType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(TypeParameter n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(PrimitiveType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ArrayType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ArrayCreationLevel n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(IntersectionType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(UnionType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(WildcardType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(UnknownType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(VariableDeclarator n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ArrayInitializerExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(VoidType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(VarType n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(Modifier n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ArrayAccessExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ArrayCreationExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(AssignExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(BinaryExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(CastExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ClassExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ConditionalExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(EnclosedExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(FieldAccessExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(InstanceOfExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(TypePatternExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(RecordPatternExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MatchAllPatternExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(CharLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(DoubleLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(IntegerLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(LongLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(StringLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(TextBlockLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(BooleanLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(NullLiteralExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ThisExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SuperExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(UnaryExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(CompactConstructorDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(Parameter n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ReceiverParameter n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ExplicitConstructorInvocationStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(LocalClassDeclarationStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(LocalRecordDeclarationStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(AssertStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(BlockStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(LabeledStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(EmptyStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ExpressionStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SwitchExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SwitchEntry n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(BreakStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(YieldStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ReturnStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(EnumConstantDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(InitializerDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(IfStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ContinueStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(DoStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ForEachStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(ThrowStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SynchronizedStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(TryStmt n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(CatchClause n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(AnnotationDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(AnnotationMemberDeclaration n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(SingleMemberAnnotationExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MemberValuePair n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(LineComment n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(BlockComment n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MarkdownComment n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(LambdaExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(MethodReferenceExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(TypeExpr n, Void arg) {
        if (n == mutation.original) {
            mutation.mutant.accept(this, arg);
        } else {
            super.visit(n, arg);
        }
    }

    @Override
    public void visit(NodeList n, Void arg) {
        for (Object node : n) {
            Node nn = (Node) node;
            if (nn == mutation.original) {
                mutation.mutant.accept(this, arg);
            } else {
                nn.accept(this, arg);
            }
        }
    }
    
}
