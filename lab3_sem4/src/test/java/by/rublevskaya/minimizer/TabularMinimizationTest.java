package by.rublevskaya.minimizer;

import by.rublevskaya.logic.TruthTableRow;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

public class TabularMinimizationTest {

    @Test
    public void testMergeTerms() throws Exception {
        Method method = TabularMinimization.class.getDeclaredMethod("mergeTerms", String.class, String.class);
        method.setAccessible(true);
        String term1 = "A∧B";
        String term2 = "A∧¬B";
        String result = (String) method.invoke(null, term1, term2);
        assertNotNull(result);
        assertEquals("()", result);
    }

    @Test
    public void testSimplify() throws Exception {
        Method method = TabularMinimization.class.getDeclaredMethod("simplify", List.class);
        method.setAccessible(true);
        List<String> terms = Arrays.asList("A∧B∧C", "A∧B∧¬C", "¬A∧B", "A∧¬B");
        List<String> result = (List<String>) method.invoke(null, terms);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testExtractClausesSKNF() throws Exception {
        Method method = TabularMinimization.class.getDeclaredMethod("extractClausesSKNF", List.class);
        method.setAccessible(true);
        TruthTableRow row1 = new TruthTableRow(Map.of('A', true, 'B', false), false);
        TruthTableRow row2 = new TruthTableRow(Map.of('A', false, 'B', true), false);
        TruthTableRow row3 = new TruthTableRow(Map.of('A', true, 'B', true), true);
        List<TruthTableRow> truthTable = Arrays.asList(row1, row2, row3);
        List<String> result = (List<String>) method.invoke(null, truthTable);
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testExtractClausesSDNF() throws Exception {
        Method method = TabularMinimization.class.getDeclaredMethod("extractClausesSDNF", List.class);
        method.setAccessible(true);
        TruthTableRow row1 = new TruthTableRow(Map.of('A', true, 'B', false), true);
        TruthTableRow row2 = new TruthTableRow(Map.of('A', false, 'B', true), true);
        TruthTableRow row3 = new TruthTableRow(Map.of('A', true, 'B', true), false);
        List<TruthTableRow> truthTable = Arrays.asList(row1, row2, row3);
        List<String> result = (List<String>) method.invoke(null, truthTable);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testMinimizeSKNF() {
        List<TruthTableRow> truthTable = List.of(
                new TruthTableRow(Map.of('A', true, 'B', false), false),
                new TruthTableRow(Map.of('A', false, 'B', true), false),
                new TruthTableRow(Map.of('A', true, 'B', true), true)
        );
        try {
            TabularMinimization.minimizeSKNF(truthTable);
        } catch (Exception e) {
            fail("Метод minimumSKNF выдал исключение: " + e.getMessage());
        }
    }

    @Test
    public void testMinimizeSDNF() {
        List<TruthTableRow> truthTable = List.of(
                new TruthTableRow(Map.of('A', true, 'B', false), true),
                new TruthTableRow(Map.of('A', false, 'B', true), true),
                new TruthTableRow(Map.of('A', true, 'B', true), false)
        );
        try {
            TabularMinimization.minimizeSDNF(truthTable);
        } catch (Exception e) {
            fail("Метод minimumSKNF выдал исключение: " + e.getMessage());
        }
    }

    @Test
    public void testPerformTabularMinimizationForSKNF() throws Exception {
        Method method = TabularMinimization.class.getDeclaredMethod("performTabularMinimization", String.class, List.class);
        method.setAccessible(true);
        List<String> terms = Arrays.asList("(!A ∨ B)", "(!A ∨ !B)", "(A ∨ !B)");
        method.invoke(null, "СКНФ", terms);
    }

    @Test
    public void testPerformTabularMinimizationForSDNF() throws Exception {
        Method method = TabularMinimization.class.getDeclaredMethod("performTabularMinimization", String.class, List.class);
        method.setAccessible(true);
        List<String> terms = Arrays.asList("(A ∧ !B)", "(A ∧ B)", "(!A ∧ B)");
        method.invoke(null, "СДНФ", terms);
    }
}