package by.rublevskaya.model.convertnumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class InverseConverterTest {

    private final InverseConverter converter = new InverseConverter();

    @Test
    public void convert_positiveNumbers() {
        assertEquals("00000001", converter.convert(1));
        assertEquals("01111111", converter.convert(127));
        assertEquals("00000000", converter.convert(0));
    }

    @Test
    public void convert_negativeNumbers() {
        assertEquals("11111110", converter.convert(-1));
        assertEquals("10000000", converter.convert(-127));
        assertEquals("10111111", converter.convert(-64));
    }

    @Test
    public void convert_edgeCases() {
        assertEquals("10000001", converter.convert(-126));
        assertEquals("01111110", converter.convert(126));
    }
}