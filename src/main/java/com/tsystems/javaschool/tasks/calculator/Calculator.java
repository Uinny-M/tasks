package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        //валидация
        if (statement == null || statement.length() == 0
                || !(statement.startsWith("(")) && !Character.isDigit(statement.charAt(0)))
            return null;

        //скобки, упощение
        if (statement.contains("(") && statement.contains(")")) {
            String sub = statement.substring(statement.indexOf('('), statement.indexOf(')') + 1);
            statement = statement.replace(sub, "" + simpleTask(sub.substring(1, sub.length() - 1)));
        }

        //решение упрощенного примера
        Double res = simpleTask(statement);

        //приведение ответа к заданному виду
        if (res == null || Double.isInfinite(res)) return null;
        if (res % 1 == 0) {
            return "" + Math.round(res);
        } else return "" + res;
    }

    //решение простого примера без скобок
    private Double simpleTask(String simpleStr) {
        double result;
        if (simpleStr.contains("/-"))
            simpleStr = simpleStr.replaceAll("/-", "/&");
//        if (simpleStr.contains("*-"))
//        simpleStr = simpleStr.replaceAll("*-", "*&");

        //получение массива математических операторов
        String math = simpleStr.replaceAll("\\d", ""); //операторы
        math = math.replace(".", "");
        math = math.replace("&", "");
        char[] operators = math.toCharArray(); //массив операторов

        //получение листа чисел
        StringTokenizer st = new StringTokenizer(simpleStr, "+-*/");
        ArrayList<Double> num = new ArrayList<>(); //числа
        while (st.hasMoreTokens()) {
            try {
                String s = st.nextToken();
                if (s.contains("&")) s = s.replaceAll("&", "-");
                num.add(Double.parseDouble(s)); //парсим числа
            } catch (NumberFormatException e) {
                return null;
            }
        }

        //валидация
        if (num.size() - 1 != math.length()) return null;

        //приоритетные операции
        int shift = 0; //сдвиг по массиву чисел
        for (int i = 0; i < operators.length; i++) {
            if (operators[i] == '*') {
                num.set(i + shift, num.get(i + shift) * num.get(i + 1 + shift));
                num.remove(i + 1 + shift);
                shift--;
            }
            if (operators[i] == '/') {
                try {
                    num.set(i + shift, num.get(i + shift) / num.get(i + 1 + shift));
                } catch (ArithmeticException e) {
                    return null;
                }
                num.remove(i + 1 + shift);
                shift--;
            }
        }

        //операции сложения и вычетания
        result = num.get(0);
        for (int i = 1, j = 0; i < num.size(); i++, j++) {
            if (operators[j] == '/' || operators[j] == '*') j++;
            if (operators[j] == '+') result = result + num.get(i);
            if (operators[j] == '-') result = result - num.get(i);
        }
        return result;
    }
}
