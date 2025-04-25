package by.rublevskaya.parser;

import by.rublevskaya.symbol.SymbolType;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

public class LogicFunctionEvaluatorTest {

    @Test
    public void testIsValidFunction_ValidFunction() {
        String validFunction = "a & b | !c";
        assertTrue("Function should be valid", LogicFunctionEvaluator.isValidFunction(validFunction));
    }

    @Test
    public void testIsValidFunction_InvalidCharacters() {
        String invalidFunction = "a & b | @c";
        assertFalse("Function with invalid characters should not be valid", LogicFunctionEvaluator.isValidFunction(invalidFunction));
    }

    @Test
    public void testIsValidFunction_EmptyString() {
        String emptyFunction = "";
        assertFalse("Empty string should not be valid", LogicFunctionEvaluator.isValidFunction(emptyFunction));
    }

    @Test
    public void testIsValidFunction_NullInput() {
        String nullFunction = null;
        assertFalse("Null input should not be valid", LogicFunctionEvaluator.isValidFunction(nullFunction));
    }

    @Test
    public void testGetSymbolType_Variable() {
        assertEquals("Character 'a' should be recognized as VARIABLE", SymbolType.VARIABLE,
                invokeGetSymbolType('a', 0, "a & b"));
    }

    @Test
    public void testGetSymbolType_OpeningBracket() {
        assertEquals("Character '(' should be recognized as OPENING_BRACKET", SymbolType.OPENING_BRACKET,
                invokeGetSymbolType('(', 0, "(a & b)"));
    }

    @Test
    public void testGetSymbolType_ClosingBracket() {
        assertEquals("Character ')' should be recognized as CLOSING_BRACKET", SymbolType.CLOSING_BRACKET,
                invokeGetSymbolType(')', 5, "(a & b)"));
    }

    @Test
    public void testGetSymbolType_BinaryOperator() {
        assertEquals("Character '&' should be recognized as BINARY_OPERATOR", SymbolType.BINARY_OPERATOR,
                invokeGetSymbolType('&', 2, "a & b"));
    }

    @Test
    public void testGetSymbolType_Implication() {
        assertEquals("Characters '->' should be recognized as IMPLICATION", SymbolType.IMPLICATION,
                invokeGetSymbolType('-', 2, "a -> b"));
    }

    @Test
    public void testGetSymbolType_Unknown() {
        assertEquals("Character '@' should be recognized as UNKNOWN", SymbolType.UNKNOWN,
                invokeGetSymbolType('@', 2, "a @ b"));
    }
    @Test
    public void testConvertToRPN_Private() throws Exception {
        Method method = LogicFunctionEvaluator.class.getDeclaredMethod("convertToRPN", String.class);
        method.setAccessible(true);
        String function = "a & (b | !c)";
        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) method.invoke(null, function);
        List<String> expected = Arrays.asList("a", "b", "c", "!", "|", "&");
        assertEquals("RPN conversion failed for complex expression", expected, result);
    }

    @Test
    public void testEvaluateRPN_Private() throws Exception {
        Method method = LogicFunctionEvaluator.class.getDeclaredMethod("evaluateRPN", List.class, Map.class);
        method.setAccessible(true);
        List<String> rpnTokens = Arrays.asList("a", "b", "&");
        Map<Character, Boolean> variableValues = new HashMap<>();
        variableValues.put('a', true);
        variableValues.put('b', false);
        @SuppressWarnings("unchecked")
        boolean result = (boolean) method.invoke(null, rpnTokens, variableValues);
        assertFalse("Evaluation of RPN should return false", result);
    }
    private SymbolType invokeGetSymbolType(char c, int index, String function) {
        try {
            java.lang.reflect.Method method = LogicFunctionEvaluator.class.getDeclaredMethod("getSymbolType", char.class, int.class, String.class);
            method.setAccessible(true);
            return (SymbolType) method.invoke(null, c, index, function);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke getSymbolType", e);
        }
    }
}