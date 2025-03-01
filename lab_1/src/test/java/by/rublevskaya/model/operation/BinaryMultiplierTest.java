package by.rublevskaya.model.operation;

import by.rublevskaya.model.convertnumber.BinaryConverter;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryMultiplierTest {

    private final BinaryConverter directConverter = new BinaryConverter() {
        @Override
        public String convert(int number) {
            if (number < 0) {
                number = (1 << 8) + number;
            }
            return Integer.toBinaryString(number);
        }
    };

    private final BinaryMultiplier binaryMultiplier = new BinaryMultiplier(directConverter);

    @Test
    public void testMultiply_positiveNumbers() {
        assertEquals("110", binaryMultiplier.multiply(3, 2));
        assertEquals("101101", binaryMultiplier.multiply(15, 3));
    }

    @Test
    public void testMultiply_zero() {
        assertEquals("000", binaryMultiplier.multiply(0, 5));
        assertEquals("0", binaryMultiplier.multiply(5, 0));
        assertEquals("0", binaryMultiplier.multiply(0, 0));
    }

    @Test
    public void testMultiply_largeNumbers() {
        assertEquals("1111101000", binaryMultiplier.multiply(20, 50));
    }

    @Test
    public void testMultiply_edgeCases() {
        assertEquals("0000000000000000000000000000000", binaryMultiplier.multiply(0, Integer.MAX_VALUE));
        assertEquals("0", binaryMultiplier.multiply(Integer.MAX_VALUE, 0));
    }
}