package by.rublevskaya.convert;

public class IndexToDecimalConverter {
    public static int convertToDecimal(String indexForm) {
        if (indexForm == null || indexForm.isEmpty()) {
            throw new IllegalArgumentException("Индексная форма не должна быть пустой");
        }
        String binary = indexForm.replace(" ", "");
        int decimalValue = 0;
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            if (bit != '0' && bit != '1') {
                throw new IllegalArgumentException("Индексная форма содержит недопустимые символы: " + bit);
            }
            decimalValue = decimalValue * 2 + (bit - '0');
        }
        return decimalValue;
    }
}
