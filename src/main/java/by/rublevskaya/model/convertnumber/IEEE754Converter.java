package by.rublevskaya.model.convertnumber;

public class IEEE754Converter {

    private static final int EXPONENT_BIAS = 127;

    public String convertToIEEE754(float number) {
        if (number < 0) {
            throw new IllegalArgumentException("Сложение поддерживается только для положительных чисел");
        }
        int exponent = 0;
        while (number >= 2) {
            number /= 2;
            exponent++;
        }
        while (number < 1 && number != 0) {
            number *= 2;
            exponent--;
        }

        exponent += EXPONENT_BIAS;

        // 2. Определим мантиссу
        number -= 1;
        StringBuilder mantissa = new StringBuilder();
        for (int i = 0; i < 23; i++) {
            number *= 2;
            if (number >= 1) {
                mantissa.append(1);
                number -= 1;
            } else {
                mantissa.append(0);
            }
        }

        String sign = "0";
        String exponentBinary = String.format("%8s", Integer.toBinaryString(exponent)).replace(' ', '0');
        return sign + exponentBinary + mantissa.toString();
    }

    public String addFloatingPoint(String binary1, String binary2) {
        int sign1 = binary1.charAt(0) - '0';
        int sign2 = binary2.charAt(0) - '0';
        int exponent1 = Integer.parseInt(binary1.substring(1, 9), 2);
        int exponent2 = Integer.parseInt(binary2.substring(1, 9), 2);
        String mantissa1 = "1" + binary1.substring(9);
        String mantissa2 = "1" + binary2.substring(9);
        if (sign1 != sign2) {
            throw new IllegalArgumentException("Сложение отрицательных чисел не поддерживается");
        }

        // 2. Нормализуем экспоненты
        if (exponent1 > exponent2) {
            int shift = exponent1 - exponent2;
            mantissa2 = shiftRight(mantissa2, shift);
            exponent2 = exponent1;
        } else if (exponent2 > exponent1) {
            int shift = exponent2 - exponent1;
            mantissa1 = shiftRight(mantissa1, shift);
            exponent1 = exponent2;
        }

        // 3. Складываем мантиссы
        String sumMantissa = addBinaryStrings(mantissa1, mantissa2);

        // 4. Проверяем переполнение мантиссы
        if (sumMantissa.length() > 24) {
            sumMantissa = sumMantissa.substring(1);
            exponent1++;
        }

        String resultMantissa = sumMantissa.substring(1);
        String resultExponent = String.format("%8s", Integer.toBinaryString(exponent1)).replace(' ', '0');
        return "0" + resultExponent + resultMantissa;
    }

    private String shiftRight(String binary, int shift) {
        StringBuilder shifted = new StringBuilder("0".repeat(shift));
        shifted.append(binary, 0, binary.length() - shift);
        return shifted.toString();
    }

    private String addBinaryStrings(String binary1, String binary2) {
        int carry = 0;
        StringBuilder result = new StringBuilder();

        for (int i = binary1.length() - 1; i >= 0; i--) {
            int bit1 = binary1.charAt(i) - '0';
            int bit2 = binary2.charAt(i) - '0';
            int sum = bit1 + bit2 + carry;

            result.append(sum % 2);
            carry = sum / 2;
        }

        if (carry > 0) {
            result.append(carry);
        }

        return result.reverse().toString();
    }
}