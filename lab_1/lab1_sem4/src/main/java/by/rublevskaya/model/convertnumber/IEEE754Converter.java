package by.rublevskaya.model.convertnumber;

import by.rublevskaya.model.constants.Constants;

public class IEEE754Converter {

    public String convertToIEEE754(float number) {
        if (number < 0) {
            throw new IllegalArgumentException("Сложение поддерживается только для положительных чисел");
        }
        int exponent = 0;
        while (number >= 2) {
            number /= Constants.BINARY_BASE;
            exponent++;
        }
        while (number < 1 && number != 0) {
            number *= Constants.BINARY_BASE;
            exponent--;
        }
        exponent += Constants.EXPONENT_BIAS;
        number -= 1;
        StringBuilder mantissa = new StringBuilder();
        for (int i = 0; i < Constants.MANTISSA_BITS; i++) {
            number *= Constants.BINARY_BASE;
            if (number >= 1) {
                mantissa.append(1);
                number -= 1;
            } else {
                mantissa.append(0);
            }
        }
        String sign = "0";
        String exponentBinary = String.format(Constants.FORMAT_8_BIT_BINARY, Integer.toBinaryString(exponent)).replace(' ', '0');
        return sign + exponentBinary + mantissa.toString();
    }

    public String addFloatingPoint(String binary1, String binary2) {
        int sign1 = binary1.charAt(0) - '0';
        int sign2 = binary2.charAt(0) - '0';
        int exponent1 = Integer.parseInt(binary1.substring(1, 9), Constants.BINARY_BASE);
        int exponent2 = Integer.parseInt(binary2.substring(1, 9), Constants.BINARY_BASE);
        String mantissa1 = "1" + binary1.substring(9);
        String mantissa2 = "1" + binary2.substring(9);
        if (sign1 != sign2) {
            throw new IllegalArgumentException("Сложение отрицательных чисел не поддерживается");
        }
        if (exponent1 > exponent2) {
            int shift = exponent1 - exponent2;
            mantissa2 = shiftRight(mantissa2, shift);
            exponent2 = exponent1;
        } else if (exponent2 > exponent1) {
            int shift = exponent2 - exponent1;
            mantissa1 = shiftRight(mantissa1, shift);
            exponent1 = exponent2;
        }
        String sumMantissa = addBinaryStrings(mantissa1, mantissa2);
        if (sumMantissa.length() > Constants.MANTISSA_BITS + 1) {
            sumMantissa = sumMantissa.substring(1);
            exponent1++;
        }
        String resultMantissa = sumMantissa.substring(1);
        String resultExponent = String.format(Constants.FORMAT_8_BIT_BINARY, Integer.toBinaryString(exponent1)).replace(' ', '0');
        return "0" + resultExponent + resultMantissa;
    }

    private String shiftRight(String binary, int shift) {
        if (shift <= 0) {
            return binary;
        }
        StringBuilder shifted = new StringBuilder(binary.length() + shift);
        for (int i = 0; i < shift; i++) {
            shifted.append('0');
        }
        shifted.append(binary, 0, binary.length() - shift);
        return shifted.toString();
    }

    private String addBinaryStrings(String binary1, String binary2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int maxLength = Math.max(binary1.length(), binary2.length());
        binary1 = String.format("%" + maxLength + "s", binary1).replace(' ', '0');
        binary2 = String.format("%" + maxLength + "s", binary2).replace(' ', '0');
        for (int i = maxLength - 1; i >= 0; i--) {
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