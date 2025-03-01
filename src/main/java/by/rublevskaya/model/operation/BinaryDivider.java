package by.rublevskaya.model.operation;

public class BinaryDivider {
    public String divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Деление на ноль недопустимо");
        }
        String binaryDividend = decimalToBinary(dividend);
        String binaryDivisor = decimalToBinary(divisor);
        String result = binaryDivision(binaryDividend, binaryDivisor);
        return result;
    }

    private String decimalToBinary(int number) {
        StringBuilder binary = new StringBuilder();
        while (number > 0) {
            binary.insert(0, number % 2);
            number /= 2;
        }
        return binary.length() == 0 ? "0" : binary.toString();
    }

    private String binaryDivision(String dividend, String divisor) {
        StringBuilder result = new StringBuilder();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < dividend.length(); i++) {
            current.append(dividend.charAt(i));
            if (compareBinary(current.toString(), divisor) >= 0) {
                result.append("1");
                current = subtractBinary(current.toString(), divisor);
            } else {
                result.append("0");
            }
        }
        result.append('.');
        int fractionalBits = 0;
        while (fractionalBits < 5) {
            current.append("0");
            if (compareBinary(current.toString(), divisor) >= 0) {
                result.append("1");
                current = subtractBinary(current.toString(), divisor);
            } else {
                result.append("0");
            }
            fractionalBits++;
        }

        return result.toString();
    }

    private int compareBinary(String binary1, String binary2) {
        while (binary1.length() < binary2.length()) {
            binary1 = "0" + binary1;
        }
        while (binary2.length() < binary1.length()) {
            binary2 = "0" + binary2;
        }
        return binary1.compareTo(binary2);
    }

    private StringBuilder subtractBinary(String binary1, String binary2) {
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int maxLength = Math.max(binary1.length(), binary2.length());
        binary1 = "0".repeat(maxLength - binary1.length()) + binary1;
        binary2 = "0".repeat(maxLength - binary2.length()) + binary2;

        for (int i = maxLength - 1; i >= 0; i--) {
            int bit1 = binary1.charAt(i) - '0';
            int bit2 = binary2.charAt(i) - '0' + borrow;

            if (bit1 < bit2) {
                bit1 += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.insert(0, bit1 - bit2);
        }

        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }

        return result;
    }
}