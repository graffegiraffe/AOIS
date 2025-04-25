package by.rublevskaya.logic;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class TruthTableRow {
    public final Map<Character, Boolean> variableValues;
    public final boolean result;

    public TruthTableRow(Map<Character, Boolean> variableValues, boolean result) {
        this.variableValues = new LinkedHashMap<>(variableValues);
        this.result = result;
    }

    public List<Integer> getVariables() {
        List<Integer> vars = new ArrayList<>();
        for (Boolean value : variableValues.values()) {
            vars.add(value ? 1 : 0);
        }
        return vars;
    }

    public int getResult() {
        return result ? 1 : 0;
    }

    public boolean getBooleanResult() {
        return result;
    }

    public int getVariableCount() {
        return variableValues.size();
    }

    public int getVariableValue(char variable) {
        Boolean value = variableValues.get(variable);
        return (value != null && value) ? 1 : 0;
    }

    public Map<Character, Boolean> getVariableValues() {
        return new LinkedHashMap<>(variableValues);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Boolean> entry : variableValues.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue() ? 1 : 0).append(" ");
        }
        sb.append("-> ").append(result ? 1 : 0);
        return sb.toString();
    }
}
