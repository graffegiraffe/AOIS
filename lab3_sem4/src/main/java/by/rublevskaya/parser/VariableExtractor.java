package by.rublevskaya.parser;

import java.util.List;
import java.util.stream.Collectors;

public class VariableExtractor {
    public static List<Character> extractVariables(String function) {
        return function.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}