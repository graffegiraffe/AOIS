package by.rublevskaya.model.operation;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryDividerTest {

    @Test
    public void testDivide() {
        BinaryDivider divider = new BinaryDivider();
        assertEquals("010.00000", divider.divide(4, 2));
        assertEquals("0101.00000", divider.divide(10, 2));
        assertEquals("01.10000", divider.divide(3, 2));
        assertEquals("0.01010", divider.divide(1, 3));
        assertEquals("010.10000", divider.divide(5, 2));
        assertEquals("0.00100", divider.divide(1, 7));
        assertEquals("1111.00000", divider.divide(15, 1));
        assertEquals("001.00000", divider.divide(7, 7));
    }

    @Test
    public void testDivideByZero() {
        BinaryDivider divider = new BinaryDivider();
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            divider.divide(5, 0);
        });
        assertEquals("Деление на ноль недопустимо", exception.getMessage());
    }

    @Test
    public void testDivideZero() {
        BinaryDivider divider = new BinaryDivider();
        assertEquals("0.00000", divider.divide(0, 5));
        assertEquals("0.00000", divider.divide(0, 10));
        assertEquals("0.00000", divider.divide(0, 1));
    }
}