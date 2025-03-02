package by.rublevskaya.model.convertnumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectConverterTest {

    private final DirectConverter converter = new DirectConverter();

    @Test
    public void convert_positiveNumber() {
        assertEquals("00000001", converter.convert(1));
        assertEquals("01000000", converter.convert(64));
        assertEquals("01111111", converter.convert(127));
    }

    @Test
    public void convert_negativeNumber() {
        assertEquals("10000001", converter.convert(-1));
        assertEquals("11000000", converter.convert(-64));
        assertEquals("11111111", converter.convert(-127));
    }

    @Test
    public void convert_zero() {
        assertEquals("00000000", converter.convert(0));
    }

    @Test
    public void convert_edgeCases() {
        assertEquals("01111111", converter.convert(127));
        assertEquals("11111111", converter.convert(-127));
    }
}