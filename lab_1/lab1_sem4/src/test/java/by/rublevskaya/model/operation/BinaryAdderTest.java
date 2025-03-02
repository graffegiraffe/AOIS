package by.rublevskaya.model.operation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BinaryAdderTest {

    private final BinaryAdder binaryAdder = new BinaryAdder();

    @Test
    public void addBinary_noCarry() {
        assertEquals("00000010", binaryAdder.addBinary("00000001", "00000001"));
        assertEquals("00000100", binaryAdder.addBinary("00000010", "00000010"));
    }

    @Test
    public void addBinary_withCarry() {
        assertEquals("00000000", binaryAdder.addBinary("00000001", "11111111"));
        assertEquals("00000010", binaryAdder.addBinary("00000011", "11111111"));
        assertEquals("10000000", binaryAdder.addBinary("01000000", "01000000"));
    }

    @Test
    public void addBinary_allZeros() {
        assertEquals("00000000", binaryAdder.addBinary("00000000", "00000000"));
    }

    @Test
    public void addBinary_incorrectLength() {
        assertThrows(IllegalArgumentException.class, () -> binaryAdder.addBinary("00001", "00000001"));
        assertThrows(IllegalArgumentException.class, () -> binaryAdder.addBinary("00000001", "000000000"));
    }

    @Test
    public void addBinary_edgeCases() {
        assertEquals("11111110", binaryAdder.addBinary("11111110", "00000000"));
        assertEquals("11111111", binaryAdder.addBinary("11111110", "00000001"));
    }
}