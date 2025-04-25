package by.rublevskaya.util;

import by.rublevskaya.logic.TruthTableRow;

import java.util.List;

public class NumericRepresentation {
    public static String getNumericFormSDNF(List<TruthTableRow> truthTable) {
        return truthTable.stream()
                .filter(row -> row.result)
                .map(row -> String.valueOf(getRowIndex(row)))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
    public static String getNumericFormSKNF(List<TruthTableRow> truthTable) {
        return truthTable.stream()
                .filter(row -> !row.result)
                .map(row -> String.valueOf(getRowIndex(row)))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
    public static String getIndexForm(List<TruthTableRow> truthTable) {
        return truthTable.stream()
                .map(row -> row.result ? "1" : "0")
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }
    private static int getRowIndex(TruthTableRow row) {
        int index = 0, position = row.variableValues.size() - 1;
        for (Boolean value : row.variableValues.values()) {
            if (value) index += (1 << position);
            position--;
        }
        return index;
    }
}
