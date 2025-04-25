package by.rublevskaya.logic;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TruthTableRowTest {
    @Test
    public void testTruthTableRow_Initialization() {
        Map<Character, Boolean> variableValues = new HashMap<>();
        variableValues.put('a', true);
        variableValues.put('b', false);
        variableValues.put('c', true);
        boolean result = true;
        TruthTableRow row = new TruthTableRow(variableValues, result);
        assertEquals(variableValues, row.variableValues);
        assertEquals(result, row.result);
    }

    @Test
    public void testTruthTableRow_EmptyVariables() {
        Map<Character, Boolean> variableValues = Collections.emptyMap();
        boolean result = false;
        TruthTableRow row = new TruthTableRow(variableValues, result);
        assertEquals(variableValues, row.variableValues);
        assertEquals(result, row.result);
    }

    @Test
    public void testTruthTableRow_SingleVariable() {
        Map<Character, Boolean> variableValues = Collections.singletonMap('x', true);
        boolean result = true;
        TruthTableRow row = new TruthTableRow(variableValues, result);
        assertEquals(variableValues, row.variableValues);
        assertEquals(result, row.result);
    }
}