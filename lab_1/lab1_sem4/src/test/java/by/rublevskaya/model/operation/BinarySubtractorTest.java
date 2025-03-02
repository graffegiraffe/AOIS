package by.rublevskaya.model.operation;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySubtractorTest {

    private final BinarySubtractor binarySubtractor = new BinarySubtractor();

    @Test
    public void subtract_positiveNumbers() {
        assertEquals("00000001", binarySubtractor.subtract(3, 2));
        assertEquals("00000110", binarySubtractor.subtract(10, 4));
        assertEquals("00000000", binarySubtractor.subtract(2, 2));
    }

    @Test
    public void subtract_negativeNumbers() {
        assertEquals("00000101", binarySubtractor.subtract(-3, -8));
        assertEquals("11110011", binarySubtractor.subtract(-8, 5));
        assertEquals("00000000", binarySubtractor.subtract(-4, -4));
    }

    @Test
    public void subtract_mixOfPositiveAndNegative() {
        assertEquals("00001010", binarySubtractor.subtract(6, -4));
        assertEquals("11101100", binarySubtractor.subtract(-12, 8));
    }

    @Test
    public void subtract_edgeCases() {
        assertEquals("11111110", binarySubtractor.subtract(0, 2));
        assertEquals("00000010", binarySubtractor.subtract(2, 0));
        assertEquals("00000000", binarySubtractor.subtract(0, 0));
    }
}