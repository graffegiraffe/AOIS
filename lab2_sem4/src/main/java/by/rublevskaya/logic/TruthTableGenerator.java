package by.rublevskaya.logic;

import by.rublevskaya.parser.LogicFunctionEvaluator;

import java.util.*;

public class TruthTableGenerator {
    private final String function;
    private final List<Character> variables;
    public TruthTableGenerator(String function, List<Character> variables) {
        this.function = function;
        this.variables = variables;
    }
    public List<TruthTableRow> generateTruthTable() {
        int numRows = (int) Math.pow(2, variables.size());
        List<TruthTableRow> truthTable = new ArrayList<>(numRows);
        if (variables.isEmpty()) {
            boolean result;
            if ("1".equals(function)) {
                result = true;
            } else if ("0".equals(function)) {
                result = false;
            } else {
                throw new IllegalArgumentException("Некорректное выражение для функции без переменных: " + function);
            }
            truthTable.add(new TruthTableRow(Collections.emptyMap(), result));
            return truthTable;
        }
        for (int i = 0; i < numRows; i++) {
            Map<Character, Boolean> values = new HashMap<>();
            for (int j = 0; j < variables.size(); j++) {
                values.put(variables.get(j), (i & (1 << (variables.size() - 1 - j))) != 0);
            }
            boolean result = LogicFunctionEvaluator.evaluate(function, values);
            truthTable.add(new TruthTableRow(values, result));
        }
        return truthTable;
    }
    public void printTruthTable(List<TruthTableRow> truthTable) {
        for (Character var : variables) {
            System.out.print(var + "\t");
        }
        System.out.println("F");
        for (TruthTableRow row : truthTable) {
            for (Character var : variables) {
                System.out.print((row.variableValues.get(var) ? 1 : 0) + "\t");
            }
            System.out.println(row.result ? 1 : 0);
        }
    }
}