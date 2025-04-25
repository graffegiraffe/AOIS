package by.rublevskaya.minimizer;

import by.rublevskaya.logic.TruthTableRow;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

public class SKNFMinimizerTest {

    @Test
    public void testCompressWithOverlap() throws Exception {
        Method method = SKNFMinimizer.class.getDeclaredMethod("compressWithOverlap", String.class, String.class);
        method.setAccessible(true);
        String term1 = "(A | B)";
        String term2 = "(A | !B)";
        String result = (String) method.invoke(null, term1, term2);
        assertNotNull(result);
        assertEquals("(A )", result);
    }

    @Test
    public void testRefineAndCluster() throws Exception {
        Method method = SKNFMinimizer.class.getDeclaredMethod("refineAndCluster", List.class);
        method.setAccessible(true);
        List<String> input = Arrays.asList("(A | B | C)", "(A | B | !C)", "(D | !C)", "(A | B | C)");
        List<String> result = (List<String>) method.invoke(null, input);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testEnumerateClauseBlocks() throws Exception {
        Method method = SKNFMinimizer.class.getDeclaredMethod("enumerateClauseBlocks", List.class);
        method.setAccessible(true);
        TruthTableRow row1 = new TruthTableRow(Map.of('A', true, 'B', false, 'C', true), false);
        TruthTableRow row2 = new TruthTableRow(Map.of('A', false, 'B', true, 'C', false), false);
        TruthTableRow row3 = new TruthTableRow(Map.of('A', true, 'B', true, 'C', true), true);
        List<TruthTableRow> truthTable = Arrays.asList(row1, row2, row3);
        List<String> result = (List<String>) method.invoke(null, truthTable);
        assertNotNull(result);
        assertTrue(result.contains("(!A | B | !C)"));
        assertTrue(result.contains("(A | !B | C)"));
        assertEquals(2, result.size());
    }

    @Test
    public void testIgniteMinimizationProcess() {
        TruthTableRow row1 = new TruthTableRow(Map.of('A', true, 'B', false), false);
        TruthTableRow row2 = new TruthTableRow(Map.of('A', false, 'B', true), false);
        TruthTableRow row3 = new TruthTableRow(Map.of('A', true, 'B', true), true);
        List<TruthTableRow> truthTable = Arrays.asList(row1, row2, row3);

        try {
            SKNFMinimizer.igniteMinimizationProcess(truthTable);
        } catch (Exception e) {
            fail("The minimization process threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDisplaySimplificationStage() throws Exception {
        Method method = SKNFMinimizer.class.getDeclaredMethod("displaySimplificationStage", int.class, List.class);
        method.setAccessible(true);
        List<String> terms = Arrays.asList("(A | !B)", "(A | B)");
        method.invoke(null, 1, terms);
    }
}