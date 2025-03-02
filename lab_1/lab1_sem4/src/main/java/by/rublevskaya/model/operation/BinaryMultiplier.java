package by.rublevskaya.model.operation;

import by.rublevskaya.model.convertnumber.BinaryConverter;

public class BinaryMultiplier {

    private final BinaryConverter directConverter;

    public BinaryMultiplier(BinaryConverter directConverter) {
        this.directConverter = directConverter;
    }

    public String multiply(int num1, int num2) {
        String binary1 = directConverter.convert(num1);
        String binary2 = directConverter.convert(num2);
        String resultBinary = multiplyBinary(binary1, binary2);
        return resultBinary;
    }

    private String multiplyBinary(String binary1, String binary2) {
        String result = "0";
        int shift = 0;
        for (int i = binary2.length() - 1; i >= 0; i--) {
            if (binary2.charAt(i) == '1') {
                String shiftedBinary = shiftLeft(binary1, shift);
                result = addBinary(result, shiftedBinary);
            }
            shift++;
        }
        return result;
    }

    private String shiftLeft(String binary, int shift) {
        StringBuilder shifted = new StringBuilder(binary);
        for (int i = 0; i < shift; i++) {
            shifted.append("0");
        }
        return shifted.toString();
    }

    private String addBinary(String binary1, String binary2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int maxLength = Math.max(binary1.length(), binary2.length());
        binary1 = String.format("%" + maxLength + "s", binary1).replace(' ', '0');
        binary2 = String.format("%" + maxLength + "s", binary2).replace(' ', '0');
        for (int i = maxLength - 1; i >= 0; i--) {
            int bit1 = binary1.charAt(i) - '0';
            int bit2 = binary2.charAt(i) - '0';
            int sum = bit1 + bit2 + carry;
            result.insert(0, sum % 2);
            carry = sum / 2;
        }
        if (carry > 0) {
            result.insert(0, carry);
        }
        return result.toString();
    }
}