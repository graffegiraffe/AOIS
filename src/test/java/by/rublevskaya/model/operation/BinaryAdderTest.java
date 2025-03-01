package by.rublevskaya.model.operation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BinaryAdderTest {

    private final BinaryAdder binaryAdder = new BinaryAdder();

    @Test
    public void addBinary_noCarry() {
        assertEquals("00000010", binaryAdder.addBinary("00000001", "00000001")); // 1 + 1 = 2
        assertEquals("00000100", binaryAdder.addBinary("00000010", "00000010")); // 2 + 2 = 4
    }

    @Test
    public void addBinary_withCarry() {
        assertEquals("00000000", binaryAdder.addBinary("00000001", "11111111")); // 1 + 255 = 256 (результат в 8 битах)
        assertEquals("00000010", binaryAdder.addBinary("00000011", "11111111")); // 3 + 255 = 258
        assertEquals("10000000", binaryAdder.addBinary("01000000", "01000000")); // 64 + 64 = 128
    }

    @Test
    public void addBinary_allZeros() {
        assertEquals("00000000", binaryAdder.addBinary("00000000", "00000000")); // 0 + 0 = 0
    }

    @Test
    public void addBinary_incorrectLength() {
        assertThrows(IllegalArgumentException.class, () -> binaryAdder.addBinary("00001", "00000001")); // Первая строка слишком короткая
        assertThrows(IllegalArgumentException.class, () -> binaryAdder.addBinary("00000001", "000000000")); // Вторая строка слишком длинная
    }

    @Test
    public void addBinary_edgeCases() {
        assertEquals("11111110", binaryAdder.addBinary("11111110", "00000000")); // 254 + 0 = 254
        assertEquals("11111111", binaryAdder.addBinary("11111110", "00000001")); // 254 + 1 = 255
    }
}