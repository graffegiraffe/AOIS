package by.rublevskaya.model.convertnumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryConverterTest {

    @Test
    public void testConvert() {
        BinaryConverter converter = new BinaryConverter() {
            @Override
            public String convert(int number) {
                return Integer.toBinaryString(number);
            }
        };
        assertEquals("1010", converter.convert(10));
        assertEquals("1111", converter.convert(15));
        assertEquals("0", converter.convert(0));
        assertEquals(Integer.toBinaryString(-10), converter.convert(-10));
    }
}