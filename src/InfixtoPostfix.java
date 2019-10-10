/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

import java.util.Stack;

public class InfixtoPostfix {

    static String infixToPostFix(String expr){
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int index = 0; index < expr.length(); index++) {
            char c = expr.charAt(index);
            //check if char is operator
            if (precedence(c) > 0) {
                // if there's a higher precedence operator than current one
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    result.append(stack.pop());
                }
                stack.push(c);
            } else {
                if (c != ' ') {
                    result.append(c);
                }
            }
        }

        // after we have all the numbers, we can now get the operators
        for (int i = 0; i <= stack.size() ; i++) {
            if (!stack.isEmpty()) {
                result.append(stack.pop());
            }
        }

        //we now have a string with no spaces in between chars, so we need to add them back
        EquationEvaluator evaluator = new EquationEvaluator();
        if (evaluator.isNumber(result.toString())) {
            return result.toString();
        } else {
            return result.toString().replace("", " ").trim();
        }
    }

    private static int precedence(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }
}
