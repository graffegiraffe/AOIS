package by.rublevskaya.minimizer;

import by.rublevskaya.logic.TruthTableRow;

import java.util.*;
import java.util.stream.Collectors;

public class TabularMinimization {

    public static void minimizeSKNF(List<TruthTableRow> truthTable) {
        System.out.println("= Минимизация расчетно-табличным методом  =");
        System.out.println("===========================================\n");
        System.out.println("=== РАСЧЁТНО-ТАБЛИЧНЫЙ МЕТОД СКНФ ===\n");
        List<String> terms = extractClausesSKNF(truthTable);
        performTabularMinimization("СКНФ", terms);
    }

    public static void minimizeSDNF(List<TruthTableRow> truthTable) {
        System.out.println("= Минимизация расчетно-табличным методом  =");
        System.out.println("===========================================\n");
        System.out.println("=== РАСЧЁТНО-ТАБЛИЧНЫЙ МЕТОД СДНФ ===\n");
        List<String> terms = extractClausesSDNF(truthTable);
        performTabularMinimization("СДНФ", terms);
    }

    private static void performTabularMinimization(String methodType, List<String> initialTerms) {
        int step = 1;
        List<String> currentTerms = initialTerms;

        while (true) {
            System.out.println("\n=== ШАГ " + step + " СКЛЕЙКИ ===");
            printTable(step, currentTerms);
            List<String> nextTerms = simplify(currentTerms);
            if (nextTerms.equals(currentTerms)) {
                System.out.println("\n*** Больше нечего склеивать, минимизация завершена. ***");
                break;
            }
            currentTerms = nextTerms;
            step++;
        }
        displayFinalResult(methodType, currentTerms);
    }

    private static List<String> extractClausesSKNF(List<TruthTableRow> truthTable) {
        return truthTable.stream()
                .map(row -> row.variableValues.entrySet().stream()
                        .map(entry -> entry.getValue() ? "!" + entry.getKey() : entry.getKey().toString())
                        .collect(Collectors.joining(" | ", "(", ")")))
                .collect(Collectors.toList());
    }

    private static List<String> extractClausesSDNF(List<TruthTableRow> truthTable) {
        return truthTable.stream()
                .filter(row -> row.result)
                .map(row -> row.variableValues.entrySet().stream()
                        .map(entry -> entry.getValue() ? entry.getKey().toString() : "!" + entry.getKey())
                        .collect(Collectors.joining(" & ", "(", ")")))
                .collect(Collectors.toList());
    }

    private static List<String> simplify(List<String> terms) {
        List<String> minimized = new ArrayList<>();
        Set<Integer> merged = new HashSet<>();
        for (int i = 0; i < terms.size(); i++) {
            for (int j = i + 1; j < terms.size(); j++) {
                String combined = mergeTerms(terms.get(i), terms.get(j));
                if (!combined.isEmpty()) {
                    minimized.add(combined);
                    merged.add(i);
                    merged.add(j);
                }
            }
        }
        for (int i = 0; i < terms.size(); i++) {
            if (!merged.contains(i)) {
                minimized.add(terms.get(i));
            }
        }
        return minimized.stream().distinct().collect(Collectors.toList());
    }

    private static String mergeTerms(String term1, String term2) {
        String[] terms1 = term1.replace("(", "").replace(")", "").split("\\|");
        String[] terms2 = term2.replace("(", "").replace(")", "").split("\\|");
        List<String> common = new ArrayList<>();
        boolean singleDifference = false;
        for (int i = 0; i < terms1.length; i++) {
            if (terms1[i].equals(terms2[i])) {
                common.add(terms1[i]);
            } else if (!singleDifference) {
                singleDifference = true;
            } else {
                return "";
            }
        }
        return "(" + String.join(" | ", common) + ")";
    }

    private static void displayFinalResult(String methodType, List<String> finalTerms) {
        String result = finalTerms.stream().collect(Collectors.joining(" & "));
        System.out.println("\nМинимизированная " + methodType + " (табличный метод): " + result);
    }

    private static void printTable(int step, List<String> terms) {
        System.out.println("Таблица шага " + step + ":");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println("  " + (i + 1) + ": " + terms.get(i));
        }
    }
}