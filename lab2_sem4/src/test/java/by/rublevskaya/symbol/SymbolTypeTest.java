package by.rublevskaya.symbol;

import org.junit.Test;

import static org.junit.Assert.*;

public class SymbolTypeTest {

    @Test
    public void testNumberOfEnumValues() {
        SymbolType[] values = SymbolType.values();
        assertEquals("SymbolType should have exactly 7 values", 7, values.length);
    }

    @Test
    public void testEnumValuesOrder() {
        SymbolType[] values = SymbolType.values();
        assertArrayEquals(
                "SymbolType values should be in the correct order",
                new SymbolType[]{
                        SymbolType.VARIABLE,
                        SymbolType.OPENING_BRACKET,
                        SymbolType.CLOSING_BRACKET,
                        SymbolType.NOT_OPERATOR,
                        SymbolType.BINARY_OPERATOR,
                        SymbolType.IMPLICATION,
                        SymbolType.UNKNOWN
                },
                values
        );
    }

    @Test
    public void testValueOf_WithValidNames() {
        assertEquals("Should return VARIABLE for 'VARIABLE'", SymbolType.VARIABLE, SymbolType.valueOf("VARIABLE"));
        assertEquals("Should return OPENING_BRACKET for 'OPENING_BRACKET'", SymbolType.OPENING_BRACKET, SymbolType.valueOf("OPENING_BRACKET"));
        assertEquals("Should return CLOSING_BRACKET for 'CLOSING_BRACKET'", SymbolType.CLOSING_BRACKET, SymbolType.valueOf("CLOSING_BRACKET"));
        assertEquals("Should return NOT_OPERATOR for 'NOT_OPERATOR'", SymbolType.NOT_OPERATOR, SymbolType.valueOf("NOT_OPERATOR"));
        assertEquals("Should return BINARY_OPERATOR for 'BINARY_OPERATOR'", SymbolType.BINARY_OPERATOR, SymbolType.valueOf("BINARY_OPERATOR"));
        assertEquals("Should return IMPLICATION for 'IMPLICATION'", SymbolType.IMPLICATION, SymbolType.valueOf("IMPLICATION"));
        assertEquals("Should return UNKNOWN for 'UNKNOWN'", SymbolType.UNKNOWN, SymbolType.valueOf("UNKNOWN"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOf_InvalidName() {
        SymbolType.valueOf("INVALID");
    }

    @Test(expected = NullPointerException.class)
    public void testValueOf_NullName() {
        SymbolType.valueOf(null);
    }
}