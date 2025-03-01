package by.rublevskaya.model.view;

import by.rublevskaya.model.convertnumber.IEEE754Converter;
import by.rublevskaya.model.operation.*;
import by.rublevskaya.model.service.BinaryOperationsService;
import by.rublevskaya.model.convertnumber.BinaryToDecimalConverter;
import by.rublevskaya.model.convertnumber.ComplementConverter;
import by.rublevskaya.model.convertnumber.DirectConverter;
import by.rublevskaya.model.convertnumber.InverseConverter;


import java.util.Scanner;

public class MainApp {
    private static BinaryOperationsService service;
    public static void main(String[] args) {

        DirectConverter directConverter = new DirectConverter();
        InverseConverter inverseConverter = new InverseConverter();
        ComplementConverter complementConverter = new ComplementConverter();
        BinaryToDecimalConverter binaryToDecimalConverter = new BinaryToDecimalConverter();
        BinaryAdder binaryAdder = new BinaryAdder();
        BinarySubtractor binarySubtractor = new BinarySubtractor();
        BinaryMultiplier binaryMultiplier = new BinaryMultiplier(directConverter);
        BinaryDivider binaryDivider = new BinaryDivider();
        IEEE754Converter ieee754Converter = new IEEE754Converter();

        service = new BinaryOperationsService(
                directConverter,
                inverseConverter,
                complementConverter,
                binaryToDecimalConverter,
                binaryAdder,
                binarySubtractor,
                binaryMultiplier,
                binaryDivider,
                ieee754Converter
        );
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите опцию:");
            System.out.println("1. Перевод числа из десятичного формата в двоичный (прямой, обратный, доп. коды)");
            System.out.println("2. Сложение двух чисел в дополнительном коде");
            System.out.println("3. Вычитание двух чисел в дополнительном коде");
            System.out.println("4. Умножение двух чисел в прямом коде");
            System.out.println("5. Деление в прямом коде (с точностью до 5 знаков)");
            System.out.println("6. Сложение чисел с плавающей точкой (IEEE-754)");
            System.out.println("0. Выход");
            System.out.println("Ваш выбор: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите число для перевода:");
                    int number = scanner.nextInt();
                    String directCode = service.getDirectCode(number);
                    String inverseCode = service.getInverseCode(number);
                    String complementCode = service.getComplementCode(number);
                    System.out.println("Перевод числа " + number + ":");
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode));
                }
                case 2 -> {
                    System.out.println("Ввод числа №1");
                    int number1 = scanner.nextInt();
                    System.out.println("Число введено: " + number1);
                    String directCode1 = service.getDirectCode(number1);
                    String inverseCode1 = service.getInverseCode(number1);
                    String complementCode1 = service.getComplementCode(number1);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode1));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode1));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode1));
                    System.out.println("\nВвод числа №2");
                    int number2 = scanner.nextInt();
                    System.out.println("Число введено: " + number2);
                    String directCode2 = service.getDirectCode(number2);
                    String inverseCode2 = service.getInverseCode(number2);
                    String complementCode2 = service.getComplementCode(number2);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode2));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode2));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode2));
                    String sumBinary = service.addInComplementCode(number1, number2);
                    int sumDecimal = service.convertBinaryToDecimal(sumBinary);
                    System.out.println("\nРезультат: " + sumDecimal);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(service.getDirectCode(sumDecimal)));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(service.getInverseCode(sumDecimal)));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(sumBinary));
                }
                case 3 -> {
                    System.out.println("Ввод числа №1");
                    int minuend = scanner.nextInt();
                    System.out.println("Число введено: " + minuend);
                    String directCode1 = service.getDirectCode(minuend);
                    String inverseCode1 = service.getInverseCode(minuend);
                    String complementCode1 = service.getComplementCode(minuend);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode1));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode1));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode1));
                    System.out.println("\nВвод числа №2");
                    int subtrahend = scanner.nextInt();
                    System.out.println("Число введено: " + subtrahend);
                    String directCode2 = service.getDirectCode(subtrahend);
                    String inverseCode2 = service.getInverseCode(subtrahend);
                    String complementCode2 = service.getComplementCode(subtrahend);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode2));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode2));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode2));
                    String resultBinary = service.subtractInComplementCode(minuend, subtrahend);
                    int resultDecimal = service.convertBinaryToDecimal(resultBinary);
                    System.out.println("\nРезультат: " + resultDecimal);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(service.getDirectCode(resultDecimal)));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(service.getInverseCode(resultDecimal)));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(resultBinary));
                }
                case 4 -> {
                    System.out.println("Введите первое число:");
                    int number1 = scanner.nextInt();
                    System.out.println("Число введено: " + number1);
                    String directCode1 = service.getDirectCode(number1);
                    String inverseCode1 = service.getInverseCode(number1);
                    String complementCode1 = service.getComplementCode(number1);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode1));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode1));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode1));
                    System.out.println("Введите второе число:");
                    int number2 = scanner.nextInt();
                    System.out.println("Число введено: " + number2);
                    String directCode2 = service.getDirectCode(number2);
                    String inverseCode2 = service.getInverseCode(number2);
                    String complementCode2 = service.getComplementCode(number2);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode2));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode2));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode2));
                    String resultBinary = service.multiplyInDirectCode(number1, number2);
                    String formattedResultBinary = service.formatBinaryCode(resultBinary);
                    int resultDecimal = service.convertBinaryToDecimal(formattedResultBinary);
                    System.out.println("Результат умножения: " + resultDecimal);
                    System.out.println("Прямой код: " + formattedResultBinary);

                }
                case 5 -> {
                    System.out.print("Введите делимое (в десятичной форме): ");
                    int dividend = scanner.nextInt();
                    System.out.println("Число введено: " + dividend);
                    String directCode1 = service.getDirectCode(dividend);
                    String inverseCode1 = service.getInverseCode(dividend);
                    String complementCode1 = service.getComplementCode(dividend);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode1));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode1));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode1));
                    System.out.print("Введите делитель (в десятичной форме): ");
                    int divisor = scanner.nextInt();
                    System.out.println("Число введено: " + divisor);
                    String directCode2 = service.getDirectCode(divisor);
                    String inverseCode2 = service.getInverseCode(divisor);
                    String complementCode2 = service.getComplementCode(divisor);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode2));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode2));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode2));
                    try {
                        String divisionResult = service.divideInDirectCode(dividend, divisor);
                        double decimalResult = binaryToDecimalConverter.convertWithFraction(divisionResult);
                        System.out.println("Результат деления в десятичном формате: " + decimalResult);
                        System.out.println("Результат деления с точностью до 5 знаков: " + divisionResult);
                    } catch (ArithmeticException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }

                }
                case 6 -> {
                    System.out.print("Введите первое число с плавающей точкой: ");
                    float floatNumber1 = scanner.nextFloat();
                    System.out.println("Число введено: " + floatNumber1);
                    System.out.print("Введите второе число с плавающей точкой: ");
                    float floatNumber2 = scanner.nextFloat();
                    System.out.println("Число введено: " + floatNumber2);
                    try {
                       String ieeeResult = service.addFloatingPointNumbers(floatNumber1, floatNumber2);
                        System.out.println("Результат сложения в формате IEEE-754: " + ieeeResult);
                        float decimalResult = Float.intBitsToFloat((int) Long.parseLong(ieeeResult, 2));
                        System.out.println("Результат сложения в десятичном формате: " + decimalResult);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}