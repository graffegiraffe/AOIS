package by.rublevskaya.model.convertnumber;

import by.rublevskaya.constants.Constants;

public class BinaryToDecimalConverter {

    public int convert(String binary) {
        if (binary == null) {
            throw new IllegalArgumentException("Число не может быть null.");
        }
        if (binary.length() < Constants.MAX_BINARY_LENGTH) {
            binary = String.format(Constants.FORMAT_8_BIT_BINARY, binary).replace(' ', '0');
        } else if (binary.length() > Constants.MAX_BINARY_LENGTH) {
            binary = binary.substring(binary.length() - Constants.MAX_BINARY_LENGTH);
        }
        boolean isNegative = binary.charAt(0) == '1';
        int decimalValue = 0;
        for (int i = 1; i < binary.length(); i++) {
            decimalValue = decimalValue * Constants.BINARY_BASE + (binary.charAt(i) - '0');
        }
        if (isNegative) {
            decimalValue -= Math.pow(2, binary.length() - Constants.NEGATIVE_SIGN_BIT);
        }
        return decimalValue;
    }

    public double convertWithFraction(String binary) {
        if (binary == null || binary.isEmpty()) {
            throw new IllegalArgumentException("Двоичное число не должно быть null или пустым.");
        }
        String[] parts = binary.split("\\.");
        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "";
        int integerDecimal = 0;
        for (int i = 0; i < integerPart.length(); i++) {
            integerDecimal = integerDecimal * Constants.BINARY_BASE + (integerPart.charAt(i) - '0');
        }
        double fractionalDecimal = 0.0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            fractionalDecimal += (fractionalPart.charAt(i) - '0') / Math.pow(Constants.BINARY_BASE, i + 1);
        }
        return integerDecimal + fractionalDecimal;
    }

    public String convertToBinary(int number) {
        if (number == 0) return "0";
        boolean isNegative = number < 0;
        number = Math.abs(number);
        StringBuilder binary = new StringBuilder();
        while (number > 0) {
            binary.insert(0, number % 2);
            number /= 2;
        }
        if (isNegative) {
            while (binary.length() < Constants.MAX_BINARY_LENGTH - 1) {
                binary.insert(0, '0');
            }
            binary.insert(0, '1');
        } else {
            while (binary.length() < Constants.MAX_BINARY_LENGTH) {
                binary.insert(0, '0');
            }
        }
        return binary.toString();
    }
}