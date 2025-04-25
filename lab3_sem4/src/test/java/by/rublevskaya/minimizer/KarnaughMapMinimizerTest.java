package by.rublevskaya.minimizer;

import by.rublevskaya.logic.TruthTableGenerator;
import by.rublevskaya.logic.TruthTableRow;
import org.junit.Test;

import java.util.List;

public class KarnaughMapMinimizerTest {

    @Test
    public void testMinimizeSDNF_SimpleFunction() {
        String function = "a & b";
        List<Character> variables = List.of('a', 'b');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        KarnaughMapMinimizer.minimizeSDNF(truthTable);
    }

    @Test
    public void testMinimizeSKNF_SimpleFunction() {
        String function = "a & b";
        List<Character> variables = List.of('a', 'b');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        KarnaughMapMinimizer.minimizeSKNF(truthTable);
    }

    @Test
    public void testMinimizeSDNF_ComplexFunction() {
        String function = "(a & b) | (!a & !b)";
        List<Character> variables = List.of('a', 'b');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        KarnaughMapMinimizer.minimizeSDNF(truthTable);
    }

    @Test
    public void testMinimizeSKNF_ComplexFunction() {
        String function = "a | !b";
        List<Character> variables = List.of('a', 'b');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        KarnaughMapMinimizer.minimizeSKNF(truthTable);
    }
}