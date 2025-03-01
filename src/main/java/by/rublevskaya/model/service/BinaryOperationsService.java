package by.rublevskaya.model.service;

import by.rublevskaya.model.convertnumber.BinaryConverter;
import by.rublevskaya.model.convertnumber.BinaryToDecimalConverter;
import by.rublevskaya.model.convertnumber.IEEE754Converter;
import by.rublevskaya.model.operation.*;

public class BinaryOperationsService {
    private final BinaryConverter directConverter;
    private final BinaryConverter inverseConverter;
    private final BinaryConverter complementConverter;
    private final BinaryToDecimalConverter binaryToDecimalConverter;
    private final BinaryAdder binaryAdder;
    private final BinarySubtractor binarySubtractor;
    private final BinaryMultiplier binaryMultiplier;
    private final BinaryDivider binaryDivider;
    private final IEEE754Converter ieee754Converter;

    public BinaryOperationsService(BinaryConverter directConverter,
                                   BinaryConverter inverseConverter,
                                   BinaryConverter complementConverter,
                                   BinaryToDecimalConverter binaryToDecimalConverter,
                                   BinaryAdder binaryAdder,
                                   BinarySubtractor binarySubtractor,
                                   BinaryMultiplier binaryMultiplier,
                                   BinaryDivider binaryDivider,
                                   IEEE754Converter ieee754Converter) {
        this.directConverter = directConverter;
        this.inverseConverter = inverseConverter;
        this.complementConverter = complementConverter;
        this.binaryToDecimalConverter = binaryToDecimalConverter;
        this.binaryAdder = binaryAdder;
        this.binarySubtractor = binarySubtractor;
        this.binaryMultiplier = binaryMultiplier;
        this.binaryDivider = binaryDivider;
        this.ieee754Converter = ieee754Converter;
    }

    public String getDirectCode(int number) {
        return directConverter.convert(number);
    }

    public String getInverseCode(int number) {
        return inverseConverter.convert(number);
    }

    public String getComplementCode(int number) {
        return complementConverter.convert(number);
    }

    public String addInComplementCode(int number1, int number2) {
        String binary1 = complementConverter.convert(number1);
        String binary2 = complementConverter.convert(number2);
        return binaryAdder.addBinary(binary1, binary2);
    }

    public String subtractInComplementCode(int minuend, int subtrahend) {
        return binarySubtractor.subtract(minuend, subtrahend);
    }

    public String multiplyInDirectCode(int number1, int number2) {
        return binaryMultiplier.multiply(number1, number2);
    }

    public String divideInDirectCode(int dividend, int divisor) {
        return binaryDivider.divide(dividend, divisor);
    }

    public String addFloatingPointNumbers(float number1, float number2) {
        String binary1 = ieee754Converter.convertToIEEE754(number1);
        String binary2 = ieee754Converter.convertToIEEE754(number2);
        return ieee754Converter.addFloatingPoint(binary1, binary2);
    }

    public int convertBinaryToDecimal(String binary) {
        return binaryToDecimalConverter.convert(binary);
    }

    public String formatBinaryCode(String binaryCode) {
        if (binaryCode == null) {
            throw new IllegalArgumentException("Переданная строка бинарного кода не может быть null");
        }
        return String.format("%8s", binaryCode).replace(' ', '0');
    }
}