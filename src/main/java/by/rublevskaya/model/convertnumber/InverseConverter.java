package by.rublevskaya.model.convertnumber;

public class InverseConverter implements BinaryConverter {
    private final DirectConverter directCodeConverter = new DirectConverter();

    @Override
    public String convert(int number) {
        if (number >= 0) {
            return directCodeConverter.convert(number);
        }

        String directCode = directCodeConverter.convert(number);
        StringBuilder inverseCode = new StringBuilder();
        inverseCode.append("1"); // Знаковый бит
        for (int i = 1; i < directCode.length(); i++) {
            inverseCode.append(directCode.charAt(i) == '0' ? '1' : '0');
        }

        return inverseCode.toString();
    }
}
