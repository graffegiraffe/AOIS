package by.rublevskaya.minimizer;

import by.rublevskaya.logic.TruthTableRow;
import java.util.*;

public class KarnaughMapMinimizer {
    private static final String[] VARIABLE_NAMES = {"a", "b", "c", "d", "e"};

    public static void minimizeSKNF(List<TruthTableRow> truthTable) {
        System.out.println("=========== СКНФ ========");
        printTruthTable(truthTable);
        int varCount = truthTable.get(0).getVariables().size();
        if (varCount > 5) {
            System.out.println("Слишком много переменных для визуализации карты Карно");
            return;
        }
        int rows = (varCount >= 2) ? 4 : 2;
        int cols = (varCount >= 3) ? 8 : (varCount == 2) ? 4 : 2;
        int[][] karnaughMap = createKarnaughMap(truthTable, rows, cols, false);
        printKarnaughMap(karnaughMap, rows, cols, varCount);
        Set<Set<Integer>> primeImplicants = findPrimeImplicants(karnaughMap, rows, cols, false);
        String result = buildFinalExpression(primeImplicants, false, varCount);
        System.out.println("ФИНАЛЬНЫЙ РЕЗУЛЬТАТ: ");
        System.out.println(result);
        System.out.println("===");
    }

    public static void minimizeSDNF(List<TruthTableRow> truthTable) {
        System.out.println("======= СДНФ ========");
        printTruthTable(truthTable);
        int varCount = truthTable.get(0).getVariables().size();
        if (varCount > 5) {
            System.out.println("Слишком много переменных для визуализации карты Карно");
            return;
        }
        int rows = (varCount >= 2) ? 4 : 2;
        int cols = (varCount >= 3) ? 8 : (varCount == 2) ? 4 : 2;
        int[][] karnaughMap = createKarnaughMap(truthTable, rows, cols, true);
        printKarnaughMap(karnaughMap, rows, cols, varCount);
        Set<Set<Integer>> primeImplicants = findPrimeImplicants(karnaughMap, rows, cols, true);
        String result = buildFinalExpression(primeImplicants, true, varCount);
        System.out.println("ФИНАЛЬНЫЙ РЕЗУЛЬТАТ: ");
        System.out.println(result);
        System.out.println("===============");
    }

    private static int[][] createKarnaughMap(List<TruthTableRow> truthTable, int rows, int cols, boolean isSDNF) {
        int[][] map = new int[rows][cols];
        for (TruthTableRow row : truthTable) {
            if ((isSDNF && row.getResult() == 1) || (!isSDNF && row.getResult() == 0)) {
                List<Integer> vars = row.getVariables();
                int rowIndex = getRowIndex(vars);
                int colIndex = getColIndex(vars);
                map[rowIndex][colIndex] = 1;
            }
        }
        return map;
    }

    private static int getRowIndex(List<Integer> vars) {
        if (vars.size() == 1) return vars.get(0);
        return (vars.get(0) << 1 | vars.get(1));
    }

    private static int getColIndex(List<Integer> vars) {
        if (vars.size() == 1) return 0;
        if (vars.size() == 2) return (vars.get(1) << 1 | vars.get(0));
        int c = vars.size() > 2 ? vars.get(2) : 0;
        int d = vars.size() > 3 ? vars.get(3) : 0;
        int e = vars.size() > 4 ? vars.get(4) : 0;
        return (c << 2) | (d << 1) | e;
    }

    private static Set<Set<Integer>> findPrimeImplicants(int[][] map, int rows, int cols, boolean isSDNF) {
        Set<Set<Integer>> primeImplicants = new LinkedHashSet<>();
        List<int[]> ones = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 1) {
                    ones.add(new int[]{i, j});
                }
            }
        }
        System.out.println("\n=== Находим обязательные группы ===");
        findGroupsOfSize(8, map, rows, cols, primeImplicants, ones);
        findGroupsOfSize(4, map, rows, cols, primeImplicants, ones);
        findGroupsOfSize(2, map, rows, cols, primeImplicants, ones);
        for (int[] pos : ones) {
            if (map[pos[0]][pos[1]] == 1) {
                Set<Integer> single = new HashSet<>();
                single.add(pos[0] * cols + pos[1]);
                primeImplicants.add(single);
                System.out.println("Добавлена одиночная клетка: [" + pos[0] + "," + pos[1] + "]");
            }
        }
        Set<Set<Integer>> filteredGroups = filterGroups(primeImplicants);
        System.out.println("\nОтфильтрованные группы:");
        for (Set<Integer> group : filteredGroups) {
            List<String> cells = new ArrayList<>();
            for (Integer cell : group) {
                int r = cell / cols;
                int c = cell % cols;
                cells.add("(" + r + "," + c + ")");
            }
            System.out.println(cells);
        }
        return filteredGroups;
    }

    private static void findGroupsOfSize(int size, int[][] map, int rows, int cols,
                                         Set<Set<Integer>> primeImplicants, List<int[]> ones) {
        for (int[] pos : ones) {
            if (map[pos[0]][pos[1]] != 1) continue;
            Set<Integer> group = tryFormGroup(map, pos[0], pos[1], size, rows, cols);
            if (group != null && group.size() == size) {
                primeImplicants.add(group);
                List<String> cells = new ArrayList<>();
                for (Integer cell : group) {
                    int r = cell / cols;
                    int c = cell % cols;
                    cells.add("(" + r + "," + c + ")");
                }
                System.out.println("Добавлена группа: " + cells);
                for (Integer cell : group) {
                    int r = cell / cols;
                    int c = cell % cols;
                    map[r][c] = 2;
                }
            }
        }
    }

    private static Set<Integer> tryFormGroup(int[][] map, int row, int col, int size, int rows, int cols) {
        Set<Integer> group = new HashSet<>();
        group.add(row * cols + col);
        if (size >= 2) {
            if (col + 1 < cols && map[row][col + 1] == 1) {
                group.add(row * cols + (col + 1));
            }
            if (row + 1 < rows && map[row + 1][col] == 1) {
                group.add((row + 1) * cols + col);
            }
        }
        if (size >= 4) {
            if (row + 1 < rows && col + 1 < cols &&
                    map[row][col + 1] == 1 && map[row + 1][col] == 1 && map[row + 1][col + 1] == 1) {
                group.add(row * cols + (col + 1));
                group.add((row + 1) * cols + col);
                group.add((row + 1) * cols + (col + 1));
            }
        }
        if (size >= 8) {
            if (rows >= 4 && cols >= 4) {
                boolean fullRow = true;
                for (int c = 0; c < cols; c++) {
                    if (map[row][c] != 1 && map[row][c] != 2) {
                        fullRow = false;
                        break;
                    }
                }
                if (fullRow) {
                    for (int c = 0; c < cols; c++) {
                        if (map[row][c] == 1) {
                            group.add(row * cols + c);
                        }
                    }
                }
            }
        }
        return group.size() >= size ? group : null;
    }

    private static Set<Set<Integer>> filterGroups(Set<Set<Integer>> groups) {
        Set<Set<Integer>> filtered = new LinkedHashSet<>();
        List<Set<Integer>> sortedGroups = new ArrayList<>(groups);
        sortedGroups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));
        Set<Integer> coveredCells = new HashSet<>();
        for (Set<Integer> group : sortedGroups) {
            boolean isNew = false;
            for (Integer cell : group) {
                if (!coveredCells.contains(cell)) {
                    isNew = true;
                    break;
                }
            }
            if (isNew) {
                filtered.add(group);
                coveredCells.addAll(group);
            }
        }
        return filtered;
    }

    private static String buildFinalExpression(Set<Set<Integer>> primeImplicants, boolean isSDNF, int varCount) {
        List<String> terms = new ArrayList<>();
        for (Set<Integer> group : primeImplicants) {
            List<String> literals = new ArrayList<>();
            boolean[] varPresent = new boolean[varCount];
            boolean[] varValue = new boolean[varCount];
            boolean first = true;
            for (Integer cell : group) {
                int row = cell / 8;
                int col = cell % 8;
                boolean[] cellVars = decodeCell(row, col, varCount);
                if (first) {
                    for (int i = 0; i < varCount; i++) {
                        varPresent[i] = true;
                        varValue[i] = cellVars[i];
                    }
                    first = false;
                } else {
                    for (int i = 0; i < varCount; i++) {
                        if (varPresent[i] && varValue[i] != cellVars[i]) {
                            varPresent[i] = false;
                        }
                    }
                }
            }
            for (int i = 0; i < varCount; i++) {
                if (varPresent[i]) {
                    String varName = VARIABLE_NAMES[i];
                    if (isSDNF) {
                        literals.add(varValue[i] ? varName : "!" + varName);
                    } else {
                        literals.add(varValue[i] ? "!" + varName : varName);
                    }
                }
            }
            if (isSDNF) {
                terms.add(String.join(" & ", literals));
            } else {
                terms.add(String.join(" | ", literals));
            }
        }
        return isSDNF ? String.join(" | ", terms) : String.join(" & ", terms);
    }

    private static boolean[] decodeCell(int row, int col, int varCount) {
        boolean[] vars = new boolean[varCount];
        if (varCount >= 1) vars[0] = (row & 2) != 0;
        if (varCount >= 2) vars[1] = (row & 1) != 0;
        if (varCount >= 3) vars[2] = (col & 4) != 0;
        if (varCount >= 4) vars[3] = (col & 2) != 0;
        if (varCount >= 5) vars[4] = (col & 1) != 0;
        return vars;
    }

    private static void printKarnaughMap(int[][] map, int rows, int cols, int varCount) {
        System.out.println("\nСтроки (a,b)");
        System.out.println("Столбцы (c,d,e)");
        System.out.println();
        System.out.print("       ");
        for (int j = 0; j < cols; j++) {
            System.out.print(String.format("%4d ", j));
        }
        System.out.println();
        System.out.println("       " + "-".repeat(cols * 5 - 1));
        for (int i = 0; i < rows; i++) {
            System.out.print(String.format("%2d    ", i));
            for (int j = 0; j < cols; j++) {
                System.out.print(String.format("%1d | ", map[i][j]));
            }
            System.out.println();
        }
    }

    private static void printTruthTable(List<TruthTableRow> truthTable) {
        System.out.println("a | b | c | d | e | Result");
        System.out.println("--------------------------");
        for (TruthTableRow row : truthTable) {
            List<Integer> vars = row.getVariables();
            System.out.printf("%d | %d | %d | %d | %d | %d%n",
                    vars.get(0), vars.get(1), vars.size() > 2 ? vars.get(2) : 0,
                    vars.size() > 3 ? vars.get(3) : 0, vars.size() > 4 ? vars.get(4) : 0,
                    row.getResult());
        }
    }
}