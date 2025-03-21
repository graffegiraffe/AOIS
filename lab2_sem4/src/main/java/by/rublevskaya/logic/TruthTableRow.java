package by.rublevskaya.logic;

import java.util.LinkedHashMap;
import java.util.Map;

public class TruthTableRow {
    public final Map<Character, Boolean> variableValues;
    public final boolean result;

    public TruthTableRow(Map<Character, Boolean> variableValues, boolean result) {
        this.variableValues = new LinkedHashMap<>(variableValues);
        this.result = result;
    }
}
