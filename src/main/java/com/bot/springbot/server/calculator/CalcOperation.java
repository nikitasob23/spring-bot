package com.bot.springbot.server.calculator;

import javax.naming.OperationNotSupportedException;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public enum CalcOperation {
    MUL('*', (x, y) -> x * y),
    DIV('/', (x, y) -> x / y),
    SUB('-', (x, y) -> x - y),
    SUM('+', (x, y) -> x + y);

    private final char symbol;
    private final BinaryOperator<Double> operation;

    private CalcOperation(char symbol, BinaryOperator<Double> operation){
        this.symbol = symbol;
        this.operation = operation;
    }

    public char getSymbol() {
        return symbol;
    }

    public BinaryOperator<Double> getOperation() {
        return operation;
    }

    public static boolean contains(String str) {
        return str.chars()
                .mapToObj(num -> (char) num)
                .anyMatch(CalcOperation::contains);
    }

    public static boolean contains(char ch) {
        return ch == CalcOperation.MUL.symbol
                || ch == CalcOperation.DIV.symbol
                || ch == CalcOperation.SUB.symbol
                || ch == CalcOperation.SUM.symbol;
    }

    public double applyOperation(double numb1, double numb2){
        return this.operation.apply(numb1, numb2);
    }

    public static CalcOperation getBySymbol(final char symbol) throws OperationNotSupportedException {
        for (CalcOperation CalcOperation : CalcOperation.values()) {
            if (CalcOperation.symbol == symbol) {
                return CalcOperation;
            }
        }
        throw new OperationNotSupportedException("Operator " + symbol + " not exist");
    }
}
