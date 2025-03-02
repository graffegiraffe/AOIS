package by.rublevskaya.model.convertnumber;

public class DirectConverter implements BinaryConverter {
    @Override
    public String convert(int number) {
        boolean isNegative = number < 0;
        number = Math.abs(number);
        StringBuilder binary = new StringBuilder();
        while (number > 0) {
            binary.append(number % 2);
            number /= 2;
        }
        while (binary.length() < 8) {
            binary.append("0");
        }
        binary.reverse();
        return isNegative ? "1" + binary.substring(1) : binary.toString();
    }
}

