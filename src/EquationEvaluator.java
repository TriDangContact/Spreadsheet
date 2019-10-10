/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Expression.*;


public class EquationEvaluator {

    public EquationEvaluator() {}

    public boolean isOperator(String str) {
        return (str.equals("+") || str.equals("-")
                || str.equals("*") || str.equals("/"));
    }

    public boolean isNumber(String str) {
        try {
            Double test = Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCellReference(String str) { return str.contains("$"); }
    public boolean isLog(String str) { return str.equals("lg"); }
    public boolean isSin(String str) { return str.equals("sin"); }

    //TODO: need to implement
    public boolean isValidEquation(String str) {
        Pattern pattern = Pattern.compile("((\\$[A-Z]+)*|(-?\\d\\.?\\d+)|(\\d+)|(log)*|(sin)*|([\\+\\-\\*\\/])|( )*)*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private Expression getOperatorExpression(String str, Expression left, Expression right) {
        switch (str) {
            case "*":
                return new MultiplyExpression(left, right);
            case "/":
                return new DivideExpression(left, right);
            case "+":
                return new AdditionExpression(left, right);
            case "-":
                return new SubtractExpression(left, right);
        }
        return null;
    }

    private Expression getLogExpression(String str, Expression expr) {
        switch (str) {
            case "lg":
                return new LogBase2Expression(expr);
        }
        return null;
    }

    private Expression getSinExpression(String str, Expression expr) {
        switch (str) {
            case "sin":
                return new SinExpression(expr);
        }
        return null;
    }

    public String processInput(String str) {
        Stack<Expression> stack = new Stack<Expression>();
        String answer;
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        if (tokenizer.countTokens()==0) {
            answer = "";
        } else {
            try {
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (isOperator(token)) {
                        Expression left = stack.pop();
                        Expression right = stack.pop();
                        Expression operator = getOperatorExpression(token, left, right);
                        Double result = operator.interpret();
                        stack.push(new NumberExpression(result));
                    } else if (isNumber(token)) {
                        Expression number = new NumberExpression(token);
                        stack.push(number);
                    } else if (isLog(token)) {
                        Expression number = stack.pop();
                        Expression operator = getLogExpression(token, number);
                        Double result = operator.interpret();
                        stack.push(new NumberExpression(result));
                    } else if (isSin(token)) {
                        Expression number = stack.pop();
                        Expression operator = getSinExpression(token, number);
                        Double result = operator.interpret();
                        stack.push(new NumberExpression(result));
                    } else if (isCellReference(token)) {
                        try {
                            //unwrapping it and process tokens of that token if we need to
                            String unwrapped = CellReferenceUnwrapper.parse(token);
                            // we should now have a string that needs to be processed and evaluated into a number
                            Expression number = new NumberExpression(processInput(unwrapped));
                            stack.push(number);
                        } catch (StackOverflowError error) {
                            throw error;
                        }
                    }
                    else {
                        throw new Exception("Unable to parse Expression");
                    }
                }
                answer = String.valueOf(stack.pop().interpret());
            } catch (Exception e) {
//                System.out.println("Error, invalid expression, " + e.getMessage());
                answer = "CANNOT COMPUTE: " + e.getMessage();
            }
        }
        return answer;
    }

}
