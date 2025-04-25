package by.rublevskaya.util;

import by.rublevskaya.logic.TruthTableRow;

import java.util.List;
import java.util.stream.Collectors;

public class SDNFGenerator {
    public static String getSDNF(List<TruthTableRow> truthTable) {
        String sdnf = truthTable.stream()
                .filter(row -> row.result)
                .map(row -> "(" + row.variableValues.entrySet().stream()
                        .map(entry -> entry.getValue() ? entry.getKey().toString() : "!" + entry.getKey())
                        .collect(Collectors.joining(" & ")) + ")")
                .collect(Collectors.joining(" | "));
        return "(" + sdnf + ")";
    }
}
