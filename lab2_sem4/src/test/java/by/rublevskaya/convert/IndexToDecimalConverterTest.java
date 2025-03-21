package by.rublevskaya.convert;

import org.junit.Test;

import static org.junit.Assert.*;

public class IndexToDecimalConverterTest {

    @Test
    public void testConvertToDecimal_ValidInput() {
        String input = "0 0 1 0 1 0 1 0";
        int expected = 42;
        assertEquals(expected, IndexToDecimalConverter.convertToDecimal(input));
    }

    @Test
    public void testConvertToDecimal_WithoutSpaces() {
        String input = "110";
        int expected = 6;
        assertEquals(expected, IndexToDecimalConverter.convertToDecimal(input));
    }

    @Test
    public void testConvertToDecimal_AllZeros() {
        String input = "0 0 0 0";
        int expected = 0;
        assertEquals(expected, IndexToDecimalConverter.convertToDecimal(input));
    }

    @Test
    public void testConvertToDecimal_AllOnes() {
        String input = "1 1 1 1 1 1 1 1";
        int expected = 255;
        assertEquals(expected, IndexToDecimalConverter.convertToDecimal(input));
    }

    @Test
    public void testConvertToDecimal_SingleDigitOne() {
        String input = "1";
        int expected = 1;
        assertEquals(expected, IndexToDecimalConverter.convertToDecimal(input));
    }

    @Test
    public void testConvertToDecimal_SingleDigitZero() {
        String input = "0";
        int expected = 0;
        assertEquals(expected, IndexToDecimalConverter.convertToDecimal(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToDecimal_EmptyString() {
        IndexToDecimalConverter.convertToDecimal("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToDecimal_NullInput() {
        IndexToDecimalConverter.convertToDecimal(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToDecimal_InvalidCharacters() {
        String input = "1 0 2 0";
        IndexToDecimalConverter.convertToDecimal(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToDecimal_LetterInBinary() {
        String input = "1 0 a 1";
        IndexToDecimalConverter.convertToDecimal(input);
    }
}