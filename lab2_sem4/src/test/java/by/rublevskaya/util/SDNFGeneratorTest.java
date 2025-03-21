package by.rublevskaya.util;

import by.rublevskaya.logic.TruthTableRow;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SDNFGeneratorTest {

    @Test
    public void getSDNF() {
        List<TruthTableRow> truthTable = Arrays.asList(
                new TruthTableRow(createVariableValues('a', true, 'b', true, 'c', false), true),  // (a & b & !c)
                new TruthTableRow(createVariableValues('a', false, 'b', true, 'c', true), true), // (!a & b & c)
                new TruthTableRow(createVariableValues('a', true, 'b', false, 'c', false), false)
        );
        String expectedSDNF = "((a & b & !c) | (!a & b & c))";
        String actualSDNF = SDNFGenerator.getSDNF(truthTable);
        assertEquals(expectedSDNF, actualSDNF);
    }
    private Map<Character, Boolean> createVariableValues(Object... data) {
        Map<Character, Boolean> map = new LinkedHashMap<>();
        for (int i = 0; i < data.length; i += 2) {
            map.put((Character) data[i], (Boolean) data[i + 1]);
        }
        return map;
    }
}