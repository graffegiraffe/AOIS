package by.rublevskaya.model.convertnumber;

import org.junit.Test;
import static org.junit.Assert.*;

public class ComplementConverterTest {

    private final ComplementConverter complementConverter = new ComplementConverter();

    @Test
    public void testConvertPositiveNumber() {
        int number = 5;
        String expected = "00000101";
        String actual = complementConverter.convert(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertZero() {
        int number = 0;
        String expected = "00000000";
        String actual = complementConverter.convert(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertNegativeNumber() {
        int number = -5;
        String expected = "11111011";
        String actual = complementConverter.convert(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertNegativeBoundary() {
        int number = -1;
        String expected = "11111111";
        String actual = complementConverter.convert(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertPositiveBoundary() {
        int number = 1;
        String expected = "00000001";
        String actual = complementConverter.convert(number);
        assertEquals(expected, actual);
    }
}