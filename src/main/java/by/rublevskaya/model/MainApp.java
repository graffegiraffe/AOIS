package by.rublevskaya.model;

import by.rublevskaya.model.operation.BinaryAdder;
import by.rublevskaya.model.convertnumber.BinaryConverter;
import by.rublevskaya.model.convertnumber.BinaryToDecimalConverter;
import by.rublevskaya.model.convertnumber.ComplementConverter;
import by.rublevskaya.model.convertnumber.DirectConverter;
import by.rublevskaya.model.convertnumber.InverseConverter;
import by.rublevskaya.model.service.BinaryOperationsService;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        BinaryOperationsService service = getBinaryOperationsService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> processSingleNumberConversion(scanner, service);
                case 2 -> processTwoNumberOperation(scanner, service, "Сложение", service::addInComplementCode);
                case 3 -> processTwoNumberOperation(scanner, service, "Вычитание", service::subtractInComplementCode);
                case 4 -> processTwoNumberOperationInDirectCode(scanner, service, "Умножение", service::multiplyInDirectCode);
                case 5 -> processDivisionInDirectCode(scanner, service);
                case 6 -> processFloatingPointSum(scanner, service);
                case 0 -> {
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите опцию:");
        System.out.println("1. Перевод числа из десятичного формата в двоичный (прямой, обратный, доп. коды)");
        System.out.println("2. Сложение двух чисел в дополнительном коде");
        System.out.println("3. Вычитание двух чисел в дополнительном коде");
        System.out.println("4. Умножение двух чисел в прямом коде");
        System.out.println("5. Деление в прямом коде (с точностью до 5 знаков)");
        System.out.println("6. Сложение чисел с плавающей точкой (IEEE-754)");
        System.out.println("0. Выход");
        System.out.println("Ваш выбор: ");
    }

    private static void processSingleNumberConversion(Scanner scanner, BinaryOperationsService service) {
        System.out.println("Введите число для перевода:");
        int number = scanner.nextInt();
        printNumberCodes(number, service);
    }

    private static void processTwoNumberOperation(Scanner scanner, BinaryOperationsService service, String operationName, BinaryOperation operation) {
        System.out.println("**** " + operationName + " двух чисел ****");

        int number1 = getInputAndPrintCodes(scanner, service, 1);
        int number2 = getInputAndPrintCodes(scanner, service, 2);

        String resultBinary = operation.apply(number1, number2);
        int resultDecimal = service.convertBinaryToDecimal(resultBinary);

        System.out.println("\nРезультат " + operationName.toLowerCase() + ": " + resultDecimal);
        printNumberCodes(resultDecimal, service, resultBinary);
    }

    private static void processTwoNumberOperationInDirectCode(Scanner scanner, BinaryOperationsService service, String operationName, BinaryOperation operation) {
        System.out.println("**** " + operationName + " двух чисел ****");

        int number1 = getInputAndPrintCodes(scanner, service, 1);
        int number2 = getInputAndPrintCodes(scanner, service, 2);

        String resultBinary = operation.apply(number1, number2);
        int resultDecimal = service.convertBinaryToDecimal(resultBinary);

        System.out.println("Результат " + operationName.toLowerCase() + ": " + resultDecimal);
        System.out.println("Прямой код: " + service.formatBinaryCode(resultBinary));
    }

    private static void processDivisionInDirectCode(Scanner scanner, BinaryOperationsService service) {
        System.out.println("**** Деление двух чисел ****");

        int number1 = getInputAndPrintCodes(scanner, service, 1);
        int number2 = getInputAndPrintCodes(scanner, service, 2);

        try {
            String divisionResult = service.divideInDirectCode(number1, number2);
            System.out.println("Результат деления с точностью до 5 знаков: " + divisionResult);
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void processFloatingPointSum(Scanner scanner, BinaryOperationsService service) {
        System.out.print("Введите первое число с плавающей точкой: ");
        float floatNumber1 = scanner.nextFloat();
        System.out.print("Введите второе число с плавающей точкой: ");
        float floatNumber2 = scanner.nextFloat();

        try {
            String ieeeResult = service.addFloatingPointNumbers(floatNumber1, floatNumber2);
            System.out.println("Результат сложения в формате IEEE-754: " + ieeeResult);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static int getInputAndPrintCodes(Scanner scanner, BinaryOperationsService service, int numberIndex) {
        System.out.println("Ввод числа №" + numberIndex);
        int number = scanner.nextInt();
        printNumberCodes(number, service);
        return number;
    }

    private static void printNumberCodes(int number, BinaryOperationsService service) {
        String directCode = service.getDirectCode(number);
        String inverseCode = service.getInverseCode(number);
        String complementCode = service.getComplementCode(number);

        System.out.println("Число " + number + ":");
        System.out.println("Прямой код:      " + service.formatBinaryCode(directCode));
        System.out.println("Обратный код:    " + service.formatBinaryCode(inverseCode));
        System.out.println("Дополнительный код: " + service.formatBinaryCode(complementCode));
    }

    private static void printNumberCodes(int number, BinaryOperationsService service, String binaryCode) {
        System.out.println("Прямой код:      " + service.formatBinaryCode(service.getDirectCode(number)));
        System.out.println("Обратный код:    " + service.formatBinaryCode(service.getInverseCode(number)));
        System.out.println("Дополнительный код: " + service.formatBinaryCode(binaryCode));
    }

    private static BinaryOperationsService getBinaryOperationsService() {
        BinaryConverter directConverter = new DirectConverter();
        BinaryConverter inverseConverter = new InverseConverter();
        BinaryConverter complementConverter = new ComplementConverter();
        BinaryAdder binaryAdder = new BinaryAdder();
        BinaryToDecimalConverter binaryToDecimalConverter = new BinaryToDecimalConverter();

        return new BinaryOperationsService(
                directConverter,
                inverseConverter,
                complementConverter,
                binaryAdder,
                binaryToDecimalConverter
        );
    }

    @FunctionalInterface
    interface BinaryOperation {
        String apply(int number1, int number2);
    }
}