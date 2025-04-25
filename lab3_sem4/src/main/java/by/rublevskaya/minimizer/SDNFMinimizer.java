package by.rublevskaya.minimizer;

import by.rublevskaya.logic.TruthTableRow;

import java.util.*;
import java.util.stream.Collectors;

public class SDNFMinimizer {

    public static void igniteMinimizationProcess(List<TruthTableRow> truthTable) {
        System.out.println("\n***********************************************");
        System.out.println("*                                             *");
        System.out.println("*      МИНИМИЗАЦИЯ СДНФ: РАСЧЁТНЫЙ МЕТОД      *");
        System.out.println("*                                             *");
        System.out.println("***********************************************\n");
        System.out.println("=== ШАГ 1: ПОДГОТОВКА ===");
        List<String> terms = enumerateTermBlocks(truthTable);
        displaySimplificationStage(1, terms);
        int step = 2;
        while (true) {
            System.out.println("\n=== ШАГ " + step + ": УПРОЩЕНИЕ ===");
            List<String> simplifiedTerms = refineAndCluster(terms);
            displaySimplificationStage(step, simplifiedTerms);
            if (simplifiedTerms.equals(terms)) {
                System.out.println("\n*** Минимизация завершена. Дальнейшее упрощение невозможно. ***");
                break;
            }
            terms = simplifiedTerms;
            step++;
        }
        String minimizedSDNF = terms.stream().collect(Collectors.joining(" | "));
        System.out.println("\n--------------------------------------------");
        System.out.println("Минимизированная СДНФ:");
        System.out.println(minimizedSDNF);
        System.out.println("--------------------------------------------");
    }

    private static List<String> enumerateTermBlocks(List<TruthTableRow> truthTable) {
        return truthTable.stream()
                .filter(row -> row.result)
                .map(row -> row.variableValues.entrySet().stream()
                        .map(entry -> entry.getValue() ? entry.getKey().toString() : "!" + entry.getKey())
                        .collect(Collectors.joining(" & ", "(", ")")))
                .collect(Collectors.toList());
    }

    private static List<String> refineAndCluster(List<String> terms) {
        Map<String, Set<String>> groupedTerms = new HashMap<>();
        for (String term : terms) {
            String key = term.replaceAll("!|\\(|\\)|\\s+|&", "");
            groupedTerms.computeIfAbsent(key, k -> new HashSet<>()).add(term);
        }
        return groupedTerms.values().stream()
                .map(group -> {
                    String minimized = group.iterator().next();
                    for (String term : group) {
                        minimized = compressWithOverlap(minimized, term);
                    }
                    return minimized;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    private static void displaySimplificationStage(int stage, List<String> terms) {
        System.out.println("\n--- ТЕРМЫ (ШАГ " + stage + ") ---");
        terms.forEach(term -> System.out.println("  " + term));
        String result = terms.stream().collect(Collectors.joining(" | "));
        System.out.println("\nРЕЗУЛЬТАТ: " + result);
    }

    private static String compressWithOverlap(String term1, String term2) {
        String cleanTerm1 = term1.replaceAll("\\(|\\)", "");
        String cleanTerm2 = term2.replaceAll("\\(|\\)", "");
        Set<String> components1 = new LinkedHashSet<>(Arrays.asList(cleanTerm1.split("&")));
        Set<String> components2 = new LinkedHashSet<>(Arrays.asList(cleanTerm2.split("&")));
        components1.retainAll(components2);
        return "(" + String.join(" & ", components1) + ")";
    }
}