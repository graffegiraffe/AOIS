package by.rublevskaya.model.convertnumber;

public class BinaryToDecimalConverter {


    public int convert(String binary) {
        if (binary == null) {
            throw new IllegalArgumentException("Число не может быть null.");
        }

        // Приведение строки к длине 8 символов
        if (binary.length() < 8) {
            binary = String.format("%8s", binary).replace(' ', '0'); // Добавление ведущих нулей
        } else if (binary.length() > 8) {
            binary = binary.substring(binary.length() - 8); // Удаление лишних старших разрядов
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

    public double convertWithFraction(String binary) {
        if (binary == null || binary.isEmpty()) {
            throw new IllegalArgumentException("Двоичное число не должно быть пустым.");
        }

        String[] parts = binary.split("\\.");
        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "";
        int integerDecimal = 0;
        for (int i = 0; i < integerPart.length(); i++) {
            integerDecimal = integerDecimal * 2 + (integerPart.charAt(i) - '0');
        }
        double fractionalDecimal = 0.0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            fractionalDecimal += (fractionalPart.charAt(i) - '0') / Math.pow(2, i + 1);
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
            while (binary.length() < 7) {
                binary.insert(0, "0");
            }
            binary.insert(0, "1");
        } else {
            while (binary.length() < 8) {
                binary.insert(0, "0");
            }
        }
        return binary.toString();
    }
}
