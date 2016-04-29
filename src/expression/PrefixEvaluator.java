package expression;

import java.util.*;

//Credit: http://www.buildingjavaprograms.com/code-files/4ed/ch12/PrefixEvaluator.java

public class PrefixEvaluator {

	private static String[] operands = {"+", "-", "*", "/", "^", "%"};
	
	// pre : input contains a legal prefix expression
    // post: expression is consumed and the result is returned
    public static double evaluate(Scanner input) throws Exception {
    	String next = input.next();
        if (isOperand(next)) {
        	String operator = next;
            double operand1 = evaluate(input);
            double operand2 = evaluate(input);
            return evaluate(operator, operand1, operand2);
        } //If it is a double 
        else if (next.matches("[\\x00-\\x20]*")){
        	return input.nextDouble();
        } else {
        	throw new Exception();
        }
    }
    
    private static boolean isOperand(String s){
    	for(String op : operands){
    		if(op.equals(s)) return true;
    	}
    	return false;
    }

    // pre : operator is one of +, -, *, / or %
    // post: returns the result of applying the given operator to
    //       the given operands
    public static double evaluate(String operator, double operand1,
                                  double operand2) {
        if (operator.equals("+")) {
            return operand1 + operand2;
        } else if (operator.equals("-")) {
            return operand1 - operand2;
        } else if (operator.equals("*")) {
            return operand1 * operand2;
        } else if (operator.equals("/")) {
            return operand1 / operand2;
        } else if (operator.equals("%")) {
            return operand1 % operand2;
        } else if (operator.equals("^")){
        	return Math.pow(operand1, operand2);
        } else {
            throw new RuntimeException("illegal operator " + operator);
        }
    }
}