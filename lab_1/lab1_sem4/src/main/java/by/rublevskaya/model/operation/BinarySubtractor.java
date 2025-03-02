package by.rublevskaya.model.operation;

import by.rublevskaya.model.convertnumber.ComplementConverter;

public class BinarySubtractor {
    private final ComplementConverter complementConverter = new ComplementConverter();
    private final BinaryAdder binaryAdder = new BinaryAdder();

    public String subtract(int minuend, int subtrahend) {
        String minuendBinary = complementConverter.convert(minuend);
        String subtrahendBinary = complementConverter.convert(-subtrahend);
        return binaryAdder.addBinary(minuendBinary, subtrahendBinary);
    }
}