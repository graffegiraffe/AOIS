package by.rublevskaya.logic;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TruthTableGeneratorTest {
    @Test
    public void testGenerateTruthTable_ForAndExpression() {
        String function = "a & b";
        List<Character> variables = Arrays.asList('a', 'b');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        assertEquals(4, truthTable.size());
        assertFalse(truthTable.get(0).result); // 0 & 0 = 0
        assertFalse(truthTable.get(1).result); // 0 & 1 = 0
        assertFalse(truthTable.get(2).result); // 1 & 0 = 0
        assertTrue(truthTable.get(3).result);  // 1 & 1 = 1
    }

    @Test
    public void testGenerateTruthTable_ForOrExpression() {
        String function = "a | b | c";
        List<Character> variables = Arrays.asList('a', 'b', 'c');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        assertEquals(8, truthTable.size());
        assertFalse(truthTable.get(0).result); // 0 | 0 | 0 = 0
        assertTrue(truthTable.get(1).result);  // 0 | 0 | 1 = 1
        assertTrue(truthTable.get(7).result);  // 1 | 1 | 1 = 1
    }

    @Test
    public void testGenerateTruthTable_ForNotExpression() {
        String function = "!a";
        List<Character> variables = Collections.singletonList('a');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        assertEquals(2, truthTable.size());
        assertTrue(truthTable.get(0).result);  // !0 = 1
        assertFalse(truthTable.get(1).result); // !1 = 0
    }

    @Test
    public void testPrintTruthTable() {
        String function = "a & b";
        List<Character> variables = Arrays.asList('a', 'b');
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        generator.printTruthTable(truthTable);
        String expectedOutput =
                        "a\tb\tF\n" +
                        "0\t0\t0\n" +
                        "0\t1\t0\n" +
                        "1\t0\t0\n" +
                        "1\t1\t1\n";
        assertEquals(expectedOutput, outContent.toString());
        System.setOut(System.out);
    }

    @Test
    public void testGenerateTruthTable_NoVariables() {
        String function = "1";
        List<Character> variables = Collections.emptyList();
        TruthTableGenerator generator = new TruthTableGenerator(function, variables);
        List<TruthTableRow> truthTable = generator.generateTruthTable();
        assertEquals(1, truthTable.size());
        assertTrue(truthTable.get(0).result);
    }
}