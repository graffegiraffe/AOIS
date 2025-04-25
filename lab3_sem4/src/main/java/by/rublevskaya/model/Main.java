package by.rublevskaya.model;

import by.rublevskaya.logic.TruthTableGenerator;
import by.rublevskaya.logic.TruthTableRow;
import by.rublevskaya.minimizer.KarnaughMapMinimizer;
import by.rublevskaya.minimizer.SDNFMinimizer;
import by.rublevskaya.minimizer.TabularMinimization;
import by.rublevskaya.parser.LogicFunctionEvaluator;
import by.rublevskaya.parser.VariableExtractor;
import by.rublevskaya.util.NumericRepresentation;
import by.rublevskaya.util.SDNFGenerator;
import by.rublevskaya.util.SKNFGenerator;
import by.rublevskaya.minimizer.SKNFMinimizer;
import by.rublevskaya.convert.IndexToDecimalConverter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите логическую функцию (например: a & b | !c; (a | b) & !c):");
        String inputFunction = new java.util.Scanner(System.in).nextLine();
        List<Character> variables = VariableExtractor.extractVariables(inputFunction);
        if (variables.size() > 5) {
            System.out.println("Функция не может содержать более 5 переменных!!!!");
            return;
        }

        if (!LogicFunctionEvaluator.isValidFunction(inputFunction)) {
            System.out.println("Введенное логическое выражение содержит некорректные символы!!!!");
            return;
        }

        TruthTableGenerator truthTableGenerator = new TruthTableGenerator(inputFunction, variables);
        List<TruthTableRow> truthTable = truthTableGenerator.generateTruthTable();

        System.out.println("\nТаблица истинности:");
        truthTableGenerator.printTruthTable(truthTable);

        String sdnf = SDNFGenerator.getSDNF(truthTable);
        System.out.println("\nСДНФ: " + sdnf);

        String numericSDNF = NumericRepresentation.getNumericFormSDNF(truthTable);
        System.out.println("Числовая форма СДНФ: " + numericSDNF);

        String sknf = SKNFGenerator.getSKNF(truthTable);
        System.out.println("\nСКНФ: " + sknf);

        String numericSKNF = NumericRepresentation.getNumericFormSKNF(truthTable);
        System.out.println("Числовая форма СКНФ: " + numericSKNF);

        String indexForm = NumericRepresentation.getIndexForm(truthTable);
        System.out.println("\nИндексная форма функции: " + indexForm);

        int decimalRepresentation = IndexToDecimalConverter.convertToDecimal(indexForm);
        System.out.println("Десятичная форма функции: " + decimalRepresentation);

        System.out.println("\nМинимизация СКНФ:");
        SKNFMinimizer.igniteMinimizationProcess(truthTable);

        System.out.println("\nМинимизация СДНФ:");
        SDNFMinimizer.igniteMinimizationProcess(truthTable);

        TabularMinimization.minimizeSKNF(truthTable);
        TabularMinimization.minimizeSDNF(truthTable);

        System.out.println("\nМинимизация СКНФ методом Карно:");
        KarnaughMapMinimizer.minimizeSKNF(truthTable);

        System.out.println("\nМинимизация СДНФ методом Карно:");
        KarnaughMapMinimizer.minimizeSDNF(truthTable);
    }
}