package org.example;

import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {

    public static double evaluateExpression(String expression) {
        Deque<Double> operandStack = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        expression = expression.replaceAll("\\s", "");

        int i = 0;
        while (i < expression.length()) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ch);

                while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    sb.append(expression.charAt(i + 1));
                    i++;
                }

                double operand = Double.parseDouble(sb.toString());
                operandStack.push(operand);
            } else if (isOperator(ch)) {
                while (!operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), ch)) {
                    performOperation(operandStack, operatorStack);
                }

                operatorStack.push(ch);
            }

            i++;
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack);
        }

        if (operandStack.size() != 1 || !operatorStack.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return operandStack.pop();
    }

    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return true;
        } else if ((op1 == '*' || op1 == '/') && (op2 == '*' || op2 == '/')) {
            return true;
        } else if ((op1 == '+' || op1 == '-') && (op2 == '+' || op2 == '-')) {
            return true;
        }
        return false;
    }


    public static void performOperation(Deque<Double> operandStack, Deque<Character> operatorStack) {
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        char operator = operatorStack.pop();
        double result = 0;

        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
        }

        operandStack.push(result);
    }
}

