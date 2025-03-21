package by.rublevskaya.symbol;

import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class OperatorConfigTest {

    @Test
    public void testOperatorPrecedence_NotOperator() {
        int precedence = OperatorConfig.OPERATOR_PRECEDENCE.get("!");
        assertEquals("NOT operator ('!') should have the highest precedence", 3, precedence);
    }

    @Test
    public void testOperatorPrecedence_AndOperator() {
        int precedence = OperatorConfig.OPERATOR_PRECEDENCE.get("&");
        assertEquals("AND operator ('&') should have a precedence of 2", 2, precedence);
    }

    @Test
    public void testOperatorPrecedence_OrOperator() {
        int precedence = OperatorConfig.OPERATOR_PRECEDENCE.get("|");
        assertEquals("OR operator ('|') should have a precedence of 1", 1, precedence);
    }

    @Test
    public void testOperatorPrecedence_ImplicationOperator() {
        int precedence = OperatorConfig.OPERATOR_PRECEDENCE.get("->");
        assertEquals("Implication operator ('->') should have a precedence of 0", 0, precedence);
    }

    @Test
    public void testOperatorPrecedence_EquivalenceOperator() {
        int precedence = OperatorConfig.OPERATOR_PRECEDENCE.get("~");
        assertEquals("Equivalence operator ('~') should have a precedence of 0", 0, precedence);
    }

    @Test
    public void testOperatorPrecedence_UnknownOperator() {
        Integer precedence = OperatorConfig.OPERATOR_PRECEDENCE.get("?");
        assertNull("Unknown operators should not have a precedence defined", precedence);
    }

    @Test
    public void testOperatorPrecedence_MapSize() {
        Map<String, Integer> operatorPrecedence = OperatorConfig.OPERATOR_PRECEDENCE;
        assertEquals("The precedence map should have 5 defined operators", 5, operatorPrecedence.size());
    }
}