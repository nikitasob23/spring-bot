package com.bot.springbot.server.calculator;

import javax.naming.OperationNotSupportedException;

public class Calculator {

    private Double result;
    private CalcOperation calcOperation;

    public Calculator(String expression) throws OperationNotSupportedException {
        StringBuilder sb = new StringBuilder();
        for (char ch : expression.toCharArray()) {
            if (isPartOfDigit(ch)) {
                sb.append(ch);
            } else {
                apply(Double.parseDouble(sb.toString()), CalcOperation.getBySymbol(ch));
                sb = new StringBuilder();
            }
        }
        apply(Double.parseDouble(sb.toString()));
    }

    public static boolean isPartOfDigit(char ch) {
        return Character.isDigit(ch) || ch == '.';
    }

    public static boolean haveDigit(String str) {
        return str.chars()
                .mapToObj(n -> (char) n)
                .anyMatch(Character::isDigit);
    }

    public void apply(double digit) {
        if (result == null) {
            result = digit;
            return;
        }
        result = calcOperation.applyOperation(result, digit);
    }

    public void apply(CalcOperation calcOperation) {
        this.calcOperation = calcOperation;
    }

    public void apply(double digit, CalcOperation calcOperation) {
        apply(digit);
        apply(calcOperation);
    }

    public double getResult() {
        return result;
    }
}

