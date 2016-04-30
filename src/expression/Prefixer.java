package expression;

import java.util.Stack;
import java.util.StringTokenizer;


//Credit: https://github.com/jetpackman/Infix-to-Prefix-Java/

public class Prefixer {

	public static boolean isOperand(String s) {
		return !(s.equals("^") || s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*") || s.equals("(") || s.equals(")"));
	}
	
	public static boolean isNumber(String s){
		try {
			Integer.parseInt(s.trim());
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	public static String operationCombine(Stack<String> operatorStack, Stack<String> operandStack, boolean reduce){
		String operator = operatorStack.pop();
		String rightOperand = operandStack.pop();
		String leftOperand = operandStack.pop();
		if (reduce && isNumber(rightOperand) && isNumber(leftOperand)){
			int left = Integer.parseInt(leftOperand);
			int right = Integer.parseInt(rightOperand);
			int result = 0;
			if (operator.equals("+")){
				result = left + right;
			}else if (operator.equals("-")){
				result = left - right;
			}else if (operator.equals("*")){
				result = left * right;
			}else if (operator.equals("/")){
				result = left / right;
			}else if (operator.equals("^")){
				result = (int) Math.pow(left, right);
			}
			return "" + result;
			
		}
		String operand = "(" + operator + " " + leftOperand + " "+ rightOperand + ")";
		return operand;
	}

	public static int rank(String s) {
		if (s.equals("+") || s.equals("-"))
			return 1;
		else if (s.equals("/") || s.equals("*"))
			return 2;
		else if (s.equals("^"))
			return 3;
		else
			return 0;
	}

	public static String infixToPrefixConvert(String infix, boolean reduce) {
		Stack<String> operandStack = new Stack<String>();
		Stack<String> operatorStack = new Stack<String>();

		StringTokenizer tokenizer = new StringTokenizer(infix);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (isOperand(token)) {
				operandStack.push(token);
			}

			else if (token.equals("(") || operatorStack.isEmpty()
					|| rank(token) > rank(operatorStack.peek())) {
				operatorStack.push(token);
			}

			else if (token.equals(")")) {
				while (!operatorStack.peek().equals("(")) {
					operandStack.push(operationCombine(operatorStack, operandStack,reduce));
				}
				operatorStack.pop();
			}
			
			else if( rank(token) <= rank(operatorStack.peek())){
				while(!operatorStack.isEmpty() && rank(token) <= rank(operatorStack.peek())){
					operandStack.push(operationCombine(operatorStack, operandStack,reduce));
				}
				operatorStack.push(token);
			}
		}
		while( !operatorStack.isEmpty() ) {
			operandStack.push(operationCombine(operatorStack, operandStack,reduce));
		}
		return (operandStack.peek());
	}

}