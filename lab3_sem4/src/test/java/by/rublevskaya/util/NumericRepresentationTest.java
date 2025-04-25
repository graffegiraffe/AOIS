package by.rublevskaya.util;

import by.rublevskaya.logic.TruthTableRow;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class NumericRepresentationTest {
    @Test
    public void testGetNumericFormSDNFPossibleOrder() {
        List<TruthTableRow> truthTable = new ArrayList<>();
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', false), false));
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', true), true));
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', false), true));
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', true), false));
        String numericFormSDNF = NumericRepresentation.getNumericFormSDNF(truthTable);
        System.out.println("Результат SDNF: " + numericFormSDNF);
    }


   @Test
    public void testGetNumericFormSDNF() {
        List<TruthTableRow> truthTable = new ArrayList<>();
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', false), false)); // Индекс 0
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', true), true));  // Индекс 1
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', false), true));  // Индекс 2
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', true), false));  // Индекс 3
        String expected = "1, 2";
        String actual = NumericRepresentation.getNumericFormSDNF(truthTable);
        assertEquals("SDNF должна содержать индексы строк с результатом true", expected, actual);
    }

    @Test
    public void testGetNumericFormSKNF() {
        List<TruthTableRow> truthTable = new ArrayList<>();
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', false), false));
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', true), true));
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', false), true));
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', true), false));
        String expected = "0, 3";
        String actual = NumericRepresentation.getNumericFormSKNF(truthTable);
        assertEquals("SKNF должна содержать индексы строк с результатом false", expected, actual);
    }

    @Test
    public void testGetIndexForm() {
        List<TruthTableRow> truthTable = new ArrayList<>();
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', false), false));
        truthTable.add(new TruthTableRow(Map.of('A', false, 'B', true), true));
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', false), true));
        truthTable.add(new TruthTableRow(Map.of('A', true, 'B', true), false));
        String expected = "0 1 1 0";
        String actual = NumericRepresentation.getIndexForm(truthTable);
        assertEquals("Index Form должна содержать результаты строк в виде 0 и 1", expected, actual);
    }
}