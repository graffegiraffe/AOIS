package by.rublevskaya.parser;

import by.rublevskaya.symbol.OperatorConfig;
import by.rublevskaya.symbol.SymbolType;

import java.util.*;

public class LogicFunctionEvaluator {
    public static boolean evaluate(String function, Map<Character, Boolean> variableValues) {
        List<String> rpnTokens = convertToRPN(function);
        return evaluateRPN(rpnTokens, variableValues);
    }
    public static boolean isValidFunction(String function) {
        if (function == null) {
            return false;
        }
        function = function.replace(" ", "");
        return function != null && !function.isEmpty() && isValidSymbolOrder(function);
    }
    private static boolean isValidSymbolOrder(String function) {
        for (int i = 0; i < function.length(); i++) {
            SymbolType type = getSymbolType(function.charAt(i), i, function);
            if (type == null) {
                continue;
            }
            if (type == SymbolType.UNKNOWN) {
                System.out.println("Неизвестный символ: " + function.charAt(i) + " на позиции " + i);
                return false;
            }
            if (type == SymbolType.IMPLICATION) {
                i++;
            }
        }
        return true;
    }

    private static SymbolType getSymbolType(char c, int index, String function) {
        if (Character.isWhitespace(c)) {
            return null;
        }
        if (Character.isLetter(c)) {
            return SymbolType.VARIABLE;
        } else if (c == '(') {
            return SymbolType.OPENING_BRACKET;
        } else if (c == ')') {
            return SymbolType.CLOSING_BRACKET;
        } else if (c == '!') {
            return SymbolType.NOT_OPERATOR;
        } else if (c == '&' || c == '|') {
            return SymbolType.BINARY_OPERATOR;
        } else if (c == '-') {
            if (index + 1 < function.length() && function.charAt(index + 1) == '>') {
                return SymbolType.IMPLICATION;
            }
        }
        return SymbolType.UNKNOWN;
    }
    private static List<String> convertToRPN(String function) {
        List<String> output = new ArrayList<>();
        Deque<String> operators = new ArrayDeque<>();
        for (int i = 0; i < function.length(); i++) {
            char c = function.charAt(i);
            SymbolType type = getSymbolType(c, i, function);
            if (type == null) {
                continue;
            }
            switch (type) {
                case VARIABLE -> output.add(String.valueOf(c));
                case OPENING_BRACKET -> operators.push(String.valueOf(c));
                case CLOSING_BRACKET -> processClosingBracket(operators, output);
                case NOT_OPERATOR, BINARY_OPERATOR, IMPLICATION -> {
                    String operator = Character.toString(c);
                    if (type == SymbolType.IMPLICATION) {
                        operator = "->";
                        i++;
                    }
                    handleOperator(operators, output, operator);
                }
            }
        }
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
        return output;
    }
    private static void processClosingBracket(Deque<String> operators, List<String> output) {
        while (!operators.isEmpty() && !"(".equals(operators.peek())) {
            output.add(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop();
        }
    }
    private static void handleOperator(Deque<String> operators, List<String> output, String operator) {
        while (!operators.isEmpty() && OperatorConfig.OPERATOR_PRECEDENCE.getOrDefault(operator, -1) <=
                OperatorConfig.OPERATOR_PRECEDENCE.getOrDefault(operators.peek(), -1)) {
            output.add(operators.pop());
        }
        operators.push(operator);
    }
    private static boolean evaluateRPN(List<String> rpnTokens, Map<Character, Boolean> variableValues) {
        Deque<Boolean> stack = new ArrayDeque<>();
        for (String token : rpnTokens) {
            if (token.length() == 1 && Character.isLetter(token.charAt(0))) {
                stack.push(variableValues.get(token.charAt(0)));
            } else if (OperatorConfig.OPERATOR_PRECEDENCE.containsKey(token)) {
                boolean result;
                switch (token) {
                    case "!" -> stack.push(!stack.pop());
                    case "&" -> {
                        boolean a = stack.pop();
                        boolean b = stack.pop();
                        result = a && b;
                        stack.push(result);
                    }
                    case "|" -> {
                        boolean a = stack.pop();
                        boolean b = stack.pop();
                        result = a || b;
                        stack.push(result);
                    }
                    case "->" -> {
                        boolean a = stack.pop();
                        boolean b = stack.pop();
                        result = !a || b; //импликация
                        stack.push(result);
                    }
                    case "~" -> {
                        boolean a = stack.pop();
                        boolean b = stack.pop();
                        result = a == b; //эквивалентность
                        stack.push(result);
                    }
                }
            }
        }
        return stack.pop();
    }
}