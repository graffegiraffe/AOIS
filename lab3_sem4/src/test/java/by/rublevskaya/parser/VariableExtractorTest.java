package by.rublevskaya.parser;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VariableExtractorTest {

    @Test
    public void testExtractVariables_SimpleExpression() {
        String function = "a & b | c";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertEquals(List.of('a', 'b', 'c'), variables);
    }

    @Test
    public void testExtractVariables_Duplicates() {
        String function = "a & a | b & c | c";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertEquals(List.of('a', 'b', 'c'), variables);
    }

    @Test
    public void testExtractVariables_MixedCase() {
        String function = "A & b | C & a";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertEquals(List.of('A', 'C', 'a', 'b'), variables);
    }

    @Test
    public void testExtractVariables_NoVariables() {
        String function = "! & | -> ~";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertTrue(variables.isEmpty());
    }

    @Test
    public void testExtractVariables_EmptyFunction() {
        String function = "";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertTrue(variables.isEmpty());
    }

    @Test
    public void testExtractVariables_WithSpacesAndSpecialChars() {
        String function = " a & b | c -> !d ";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertEquals(List.of('a', 'b', 'c', 'd'), variables);
    }

    @Test
    public void testExtractVariables_RepeatedVariables() {
        String function = "a & b & a & c & b";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertEquals(List.of('a', 'b', 'c'), variables);
    }

    @Test
    public void testExtractVariables_OnlyLetters() {
        String function = "a1 & b2 | 3c -> 4d";
        List<Character> variables = VariableExtractor.extractVariables(function);
        assertEquals(List.of('a', 'b', 'c', 'd'), variables);
    }
}