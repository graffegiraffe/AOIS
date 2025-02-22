package by.rublevskaya.model.convertnumber;

public class ComplementConverter implements BinaryConverter {
    private final InverseConverter inverseCodeConverter = new InverseConverter();

    @Override
    public String convert(int number) {
        if (number >= 0) {
            return inverseCodeConverter.convert(number);
        }
        String inverseCode = inverseCodeConverter.convert(number);

        char[] binary = inverseCode.toCharArray();
        for (int i = binary.length - 1; i >= 0; i--) {
            if (binary[i] == '0') {
                binary[i] = '1';
                break;
            } else {
                binary[i] = '0';
            }
        }
        return new String(binary);
    }
}
