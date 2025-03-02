package by.rublevskaya.model.convertnumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryToDecimalConverterTest {

    private final BinaryToDecimalConverter converter = new BinaryToDecimalConverter();

    @Test
    public void testConvert_ValidPositive8BitBinary() {
        assertEquals(5, converter.convert("00000101"));
    }

    @Test
    public void testConvert_ValidNegative8BitBinary() {
        assertEquals(-3, converter.convert("11111101"));
    }

    @Test
    public void testConvert_BinaryShorterThan8Bits() {
        assertEquals(5, converter.convert("101"));
    }

    @Test
    public void testConvert_BinaryLongerThan8Bits() {
        assertEquals(-1, converter.convert("111111111"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvert_NullInput() {
        converter.convert(null);
    }

    @Test
    public void testConvertWithFraction_ValidBinary() {
        assertEquals(5.625, converter.convertWithFraction("101.101"), 0.0001);
    }

    @Test
    public void testConvertWithFraction_ValidBinaryWithoutFractionalPart() {
        assertEquals(5.0, converter.convertWithFraction("101"), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithFraction_EmptyString() {
        converter.convertWithFraction("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithFraction_NullInput() {
        converter.convertWithFraction(null);
    }

    @Test
    public void testConvertToBinary_PositiveNumber() {
        assertEquals("00000101", converter.convertToBinary(5));
    }

    @Test
    public void testConvertToBinary_NegativeNumber() {
        assertEquals("10000101", converter.convertToBinary(-5));
    }

    @Test
    public void testConvertToBinary_Zero() {
        assertEquals("0", converter.convertToBinary(0));
    }
}