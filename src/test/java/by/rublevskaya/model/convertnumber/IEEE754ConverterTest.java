package by.rublevskaya.model.convertnumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class IEEE754ConverterTest {

    private final IEEE754Converter converter = new IEEE754Converter();


    @Test
    public void testConvertToIEEE754_withPositiveNumbers() {
    // 5.0 -> 0 10000001 01000000000000000000000
        String ieee754ForFive = converter.convertToIEEE754(5.0f);
        assertEquals("01000000101000000000000000000000", ieee754ForFive);

        // 0.75 -> 0 01111110 10000000000000000000000
        String ieee754ForPointSevenFive = converter.convertToIEEE754(0.75f);
        assertEquals("00111111010000000000000000000000", ieee754ForPointSevenFive);

        //  11.25 -> 0 10000010 01101000000000000000000
        String ieee754ForElevenPointTwoFive = converter.convertToIEEE754(11.25f);
        assertEquals("01000001001101000000000000000000", ieee754ForElevenPointTwoFive);
    }

    @Test
    public void testConvertToIEEE754_withNegativeNumberThrowsException() {
        try {
            converter.convertToIEEE754(-4.5f);
            fail("Expected IllegalArgumentException for negative number");
        } catch (IllegalArgumentException e) {
            assertEquals("Сложение поддерживается только для положительных чисел", e.getMessage());
        }
    }

    @Test
    public void testAddFloatingPoint_withEqualExponents() {

        String binary1 = "01000000101000000000000000000000"; // 5.0
        String binary2 = "01000000100000000000000000000000"; // 3.0

        String result = converter.addFloatingPoint(binary1, binary2);
        assertEquals("01000001001000000000000000000000", result);
    }

    @Test
    public void testAddFloatingPoint_withNormalizedExponents() {
        String binary1 = "01000000101000000000000000000000"; // 5.0
        String binary2 = "00111111010000000000000000000000"; // 0.75
        String result = converter.addFloatingPoint(binary1, binary2);
        assertEquals("01000000101110000000000000000000", result);
    }

    @Test
    public void testAddFloatingPoint_withCarryInMantissa() {
        String binary1 = "00111111110000000000000000000000"; // 1.5
        String binary2 = "00111111110000000000000000000000"; // 1.5

        String result = converter.addFloatingPoint(binary1, binary2);
        assertEquals("01000000000000000000000000000000", result);
    }

    @Test
    public void testAddFloatingPoint_withDifferentSignsThrowsException() {
        String binary1 = "01000000101000000000000000000000"; // 5.0
        String binary2 = "11000000100000000000000000000000"; // -3.0

        try {
            converter.addFloatingPoint(binary1, binary2);
            fail("Expected IllegalArgumentException for addition of numbers with different signs");
        } catch (IllegalArgumentException e) {
            assertEquals("Сложение отрицательных чисел не поддерживается", e.getMessage());
        }
    }
}