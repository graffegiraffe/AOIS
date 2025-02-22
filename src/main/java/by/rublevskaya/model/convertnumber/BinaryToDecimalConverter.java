package by.rublevskaya.model.convertnumber;

public class BinaryToDecimalConverter {

    public int convert(String binary) {
        if (binary == null || binary.length() != 8) {
            throw new IllegalArgumentException("Число должно быть представлено в виде строки длиной 8 бит.");
        }
        boolean isNegative = binary.charAt(0) == '1';

        int decimalValue = 0;
        for (int i = 1; i < binary.length(); i++) {
            decimalValue = decimalValue * 2 + (binary.charAt(i) - '0');
        }
        if (isNegative) {
            decimalValue -= Math.pow(2, binary.length() - 1); // 2^(n-1)
        }

        return decimalValue;
    }
}
