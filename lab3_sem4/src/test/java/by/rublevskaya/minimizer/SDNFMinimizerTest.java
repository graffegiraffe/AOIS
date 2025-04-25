package by.rublevskaya.minimizer;
import by.rublevskaya.logic.TruthTableRow;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SDNFMinimizerTest {

    @Test
    public void testCompressWithOverlap() throws Exception {
        Method method = SDNFMinimizer.class.getDeclaredMethod("compressWithOverlap", String.class, String.class);
        method.setAccessible(true);
        String term1 = "A∧B";
        String term2 = "A∧¬B";
        String result = (String) method.invoke(null, term1, term2);
        assertNotNull(result);
        assertEquals("()", result);
    }

    @Test
    public void testMinimizeSingleVariable() {
        List<List<Object>> sdnf = Arrays.asList(
                Collections.singletonList('A'),
                Arrays.asList('A', false)
        );
        List<String> variables = Collections.singletonList("A");
        List<List<Object>> minimized = SDNFMinimizer.minimizeSDNF(sdnf, variables);
        List<List<Object>> expected = Arrays.asList(
                Collections.singletonList('A'),
                Arrays.asList('A', false)
        );
        assertEquals("Минимизация выражения с одной переменной некорректна", expected, minimized);
    }

    @Test
    public void testRefineAndCluster() throws Exception {
        Method method = SDNFMinimizer.class.getDeclaredMethod("refineAndCluster", List.class);
        method.setAccessible(true);
        List<String> input = Arrays.asList("A∧B∧C", "A∧B∧¬C", "¬A∧B", "A∧¬B");
        List<String> result = (List<String>) method.invoke(null, input);
        assertNotNull(result);
        assertTrue(result.contains("A∧B"));
        assertTrue(result.contains("¬A∧B"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDisplaySimplificationStage() throws Exception {
        Method method = SDNFMinimizer.class.getDeclaredMethod("displaySimplificationStage", int.class, List.class);
        method.setAccessible(true);
        List<String> terms = new ArrayList<>(Arrays.asList("A∧B", "A∧¬B"));
        method.invoke(null, 1, terms);
    }

    @Test
    public void testCompareTerms() {
        List<List<Object>> terms = Arrays.asList(
                Arrays.asList('A', false, 'B', true),
                Arrays.asList('A', true, 'B', false),
                Arrays.asList('A', false, 'B', false)
        );
        List<String> variables = Arrays.asList("A", "B");
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList('A', false, 'B', 'X'),
                Arrays.asList('A', 'X', 'B', false)
        );
    }

    @Test
    public void testEnumerateTermBlocks() throws Exception {
        Method method = SDNFMinimizer.class.getDeclaredMethod("enumerateTermBlocks", List.class);
        method.setAccessible(true);
        TruthTableRow row1 = new TruthTableRow(Map.of('A', true, 'B', true), true);
        TruthTableRow row2 = new TruthTableRow(Map.of('A', true, 'B', false), true);
        TruthTableRow row3 = new TruthTableRow(Map.of('A', false, 'B', true), false);
        List<TruthTableRow> truthTable = Arrays.asList(row1, row2, row3);
        List<String> result = (List<String>) method.invoke(null, truthTable);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}