package by.rublevskaya.model.operation;

public class BinaryAdder {
    public String addBinary(String bin1, String bin2) {
        if (bin1.length() != 8 || bin2.length() != 8) {
            throw new IllegalArgumentException("Оба числа должны быть представлены в 8-битных форматах.");
        }
        char[] result = new char[8];
        int carry = 0;
        for (int i = 7; i >= 0; i--) {
            int bit1 = bin1.charAt(i) - '0';
            int bit2 = bin2.charAt(i) - '0';
            int sum = bit1 + bit2 + carry;
            result[i] = (char) ((sum % 2) + '0');
            carry = sum / 2;
        }
        return new String(result);
    }
}