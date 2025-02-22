package by.rublevskaya.model.service;

import by.rublevskaya.model.addition.BinaryAdder;
import by.rublevskaya.model.convertnumber.BinaryConverter;
import by.rublevskaya.model.convertnumber.BinaryToDecimalConverter;

public class BinaryOperationsService {
    private final BinaryConverter directConverter;
    private final BinaryConverter inverseConverter;
    private final BinaryConverter complementConverter;
    private final BinaryAdder binaryAdder;
    private final BinaryToDecimalConverter binaryToDecimalConverter;

    public BinaryOperationsService(BinaryConverter directConverter,
                                   BinaryConverter inverseConverter,
                                   BinaryConverter complementConverter,
                                   BinaryAdder binaryAdder,
                                   BinaryToDecimalConverter binaryToDecimalConverter) {
        this.directConverter = directConverter;
        this.inverseConverter = inverseConverter;
        this.complementConverter = complementConverter;
        this.binaryAdder = binaryAdder;
        this.binaryToDecimalConverter = binaryToDecimalConverter;
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
        String complement1 = complementConverter.convert(number1);
        String complement2 = complementConverter.convert(number2);
        return binaryAdder.addBinary(complement1, complement2);
    }

    public int convertBinaryToDecimal(String binary) {
        return binaryToDecimalConverter.convert(binary);
    }

    public String formatBinaryCode(String binary) {
        return "[" + binary.charAt(0) + " " + binary.substring(1) + "]";
    }

    public String subtractInComplementCode(int minuend, int subtrahend) {
        String subtrahendComplement = complementConverter.convert(subtrahend);

        char[] negateSubtrahend = subtrahendComplement.toCharArray();
        for (int i = 0; i < negateSubtrahend.length; i++) {
            negateSubtrahend[i] = (negateSubtrahend[i] == '0') ? '1' : '0';
        }

        String negativeSubtrahend = binaryAdder.addBinary(new String(negateSubtrahend), "00000001");

        String minuendComplement = complementConverter.convert(minuend);
        return binaryAdder.addBinary(minuendComplement, negativeSubtrahend);
    }
    /**
     * Метод умножает два числа в прямом коде.
     *
     * @param number1 Первое число в десятичной системе.
     * @param number2 Второе число в десятичной системе.
     * @return результат умножения в прямом коде.
     */
    public String multiplyInDirectCode(int number1, int number2) {

        boolean isNegativeResult = (number1 < 0) ^ (number2 < 0);

        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);

        String directCode1 = directConverter.convert(absNumber1);
        String directCode2 = directConverter.convert(absNumber2);

        int result = absNumber1 * absNumber2;

        String directCodeResult = directConverter.convert(result);

        if (isNegativeResult) {
            directCodeResult = "1" + directCodeResult.substring(1);
        }

        return directCodeResult;
    }
}