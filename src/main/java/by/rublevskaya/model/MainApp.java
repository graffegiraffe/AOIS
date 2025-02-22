package by.rublevskaya.model;

import by.rublevskaya.model.addition.BinaryAdder;
import by.rublevskaya.model.convertnumber.BinaryConverter;
import by.rublevskaya.model.convertnumber.BinaryToDecimalConverter;
import by.rublevskaya.model.convertnumber.ComplementConverter;
import by.rublevskaya.model.convertnumber.DirectConverter;
import by.rublevskaya.model.convertnumber.InverseConverter;
import by.rublevskaya.model.service.BinaryOperationsService;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        BinaryConverter directConverter = new DirectConverter();
        BinaryConverter inverseConverter = new InverseConverter();
        BinaryConverter complementConverter = new ComplementConverter();
        BinaryToDecimalConverter binaryToDecimalConverter = new BinaryToDecimalConverter();

        BinaryAdder binaryAdder = new BinaryAdder();

        // Сервис для выполнения операций
        BinaryOperationsService service = new BinaryOperationsService(
                directConverter,
                inverseConverter,
                complementConverter,
                binaryAdder,
                binaryToDecimalConverter
        );

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите опцию:");
            System.out.println("1. Перевод числа из десятичного формата в двоичный (прямой, обратный, доп. коды)");
            System.out.println("2. Сложение двух чисел в дополнительном коде");
            System.out.println("3. Вычитание двух чисел в дополнительном коде");
            System.out.println("4. Умножение двух чисел в прямом коде");
            System.out.println("0. Выход");

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
                    // Ввод уменьшаемого
                    System.out.println("Ввод числа №1");
                    int minuend = scanner.nextInt();
                    System.out.println("Число введено: " + minuend);
                    String directCode1 = service.getDirectCode(minuend);
                    String inverseCode1 = service.getInverseCode(minuend);
                    String complementCode1 = service.getComplementCode(minuend);
                    System.out.println("Прямой код:      " + service.formatBinaryCode(directCode1));
                    System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode1));
                    System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode1));

                    // Ввод вычитаемого
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
                    int resultDecimal = service.convertBinaryToDecimal(resultBinary);

                    System.out.println("Результат умножения: " + resultDecimal);
                    System.out.println("Прямой код: " + service.formatBinaryCode(resultBinary));

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