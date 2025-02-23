package by.rublevskaya.model.service;

import by.rublevskaya.model.operation.BinaryAdder;
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
    /**
     * Сложение двух положительных чисел с плавающей точкой по стандарту IEEE-754-2008 (32 бита).
     *
     * @param number1 Первое число в десятичном формате.
     * @param number2 Второе число в десятичном формате.
     * @return Результат сложения в формате IEEE-754 (32 бита) в двоичном виде.
     */
    public String addFloatingPointNumbers(float number1, float number2) {
        // Проверка на положительные числа
        if (number1 < 0 || number2 < 0) {
            throw new IllegalArgumentException("Числа должны быть положительными!");
        }

        // Преобразование чисел в формат IEEE-754
        String ieeeNumber1 = convertToIEEE754(number1);
        String ieeeNumber2 = convertToIEEE754(number2);

        // Разбор IEEE-формата на знак, экспоненту и мантиссу
        int exponent1 = Integer.parseInt(ieeeNumber1.substring(1, 9), 2);
        int exponent2 = Integer.parseInt(ieeeNumber2.substring(1, 9), 2);
        String mantissa1 = "1" + ieeeNumber1.substring(9); // Предполагаем скрытую 1
        String mantissa2 = "1" + ieeeNumber2.substring(9);

        // Приведение к общей экспоненте
        if (exponent1 > exponent2) {
            int shift = exponent1 - exponent2;
            mantissa2 = shiftRight(mantissa2, shift);
            exponent2 = exponent1;
        } else if (exponent2 > exponent1) {
            int shift = exponent2 - exponent1;
            mantissa1 = shiftRight(mantissa1, shift);
            exponent1 = exponent2;
        }

        // Сложение мантисс
        String mantissaResult = addBinaryStrings(mantissa1, mantissa2);

        // Нормализация результата
        if (mantissaResult.startsWith("10")) { // Переполнение мантиссы
            mantissaResult = shiftRight(mantissaResult, 1);
            exponent1++;
        }

        // Обрезаем лишние символы мантиссы до 23 бит
        mantissaResult = mantissaResult.substring(1, 24);

        // Перепаковка результата в IEEE-754
        String exponentResult = String.format("%8s", Integer.toBinaryString(exponent1)).replace(' ', '0');
        return "0" + exponentResult + mantissaResult; // Всегда положительный результат, знак '0'
    }

    /**
     * Преобразует число из десятичного формата в IEEE-754.
     */
    private String convertToIEEE754(float number) {
        int intBits = Float.floatToRawIntBits(number); // Только для генерации числа, аналогов не будет после.
        String binary = Integer.toBinaryString(intBits);
        return ("00000000000000000000000000000000" + binary).substring(binary.length());
    }

    /**
     * Побитовый сдвиг мантиссы вправо.
     */
    private String shiftRight(String binaryString, int shift) {
        for (int i = 0; i < shift; i++) {
            binaryString = "0" + binaryString.substring(0, binaryString.length() - 1);
        }
        return binaryString;
    }

    /**
     * Побитовое сложение двух бинарных строк.
     */
    private String addBinaryStrings(String binary1, String binary2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;

        for (int i = binary1.length() - 1; i >= 0; i--) {
            int bit1 = binary1.charAt(i) - '0';
            int bit2 = binary2.charAt(i) - '0';

            int sum = bit1 + bit2 + carry;
            result.append(sum % 2);
            carry = sum / 2;
        }

        if (carry > 0) result.append(carry);

        return result.reverse().toString();
    }
    /**
     * Метод выполняет деление двух чисел в прямом коде.
     *
     * @param dividend Делимое в десятичном формате.
     * @param divisor  Делитель в десятичном формате.
     * @return Результат деления в десятичном формате с прямым кодом, с точностью до 5 знаков.
     */
    public String divideInDirectCode(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Деление на ноль недопустимо.");
        }

        // Определяем знак результата
        boolean isNegativeResult = (dividend < 0) ^ (divisor < 0);

        // Преобразуем делимое и делитель в абсолютные значения
        double absDividend = Math.abs(dividend);
        double absDivisor = Math.abs(divisor);

        // Выполняем деление
        double divisionResult = absDividend / absDivisor;

        // Ограничиваем точность до 5 знаков
        String formattedResult = String.format("%.5f", divisionResult);

        // Если результат отрицательный, добавляем знак "-"
        if (isNegativeResult) {
            formattedResult = "-" + formattedResult;
        }

        return formattedResult;
    }
}