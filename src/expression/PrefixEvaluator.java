package expression;

import java.io.*;
import java.util.*;
public class PrefixEvaluator{
	
	static String opString = "+-*/^";
	
	public static double evaluate(String expr){
		Stack<Character> operators = new Stack<Character>();
		Stack<Double> numbers = new Stack<Double>();
		Scanner scan = new Scanner(expr);
		
		int i = 0;
		while(scan.hasNext()){
			if(scan.hasNextDouble()){
				numbers.push(scan.nextDouble());
			} else {
				String s = scan.next();
				while(s.indexOf('(') > -1){
					operators.push('(');
					s = s.substring(1);
				}
				if(opString.indexOf(s) > -1){
					operators.push(s.charAt(0));
					
					if(operators.size() > 0 && numbers.size() > 1)
						numbers.push(Operation(operators.pop(), numbers.pop(), numbers.pop()));
					
				} else if(s.indexOf(')') > -1)  {
					String num = s.substring(0, s.indexOf(')'));
					s = s.substring(s.indexOf(')'));
					numbers.push(Double.parseDouble(num));

					for(int i1 = 0; i1 < s.length(); i1++){
						numbers.push(Operation(nextOperator(operators), numbers.pop(), numbers.pop()));
					}					
				}
			}
		}
		while(operators.size() > 0){
			char c = nextOperator(operators);
			if(c == ' ')
				break;
			numbers.push(Operation(c, 
					numbers.pop(), 
					numbers.pop()));
		}
		return numbers.pop();
	}
	
	private static char nextOperator(Stack<Character> operators){
		
		
		
		while(!operators.isEmpty() && operators.peek() == '(')
			operators.pop();
		
		if(operators.isEmpty())
			return ' ';
		
		return operators.pop();
	}

	private static Double Operation(char pop, Double pop2, Double pop3) {
		switch(pop){
			case '+': return pop2 + pop3;
			case '-': return pop3 - pop2;
			case '*': return pop2 * pop3;
			case '/': return pop3 / pop2;
			case '^': return Math.pow(pop3, pop2);
		}
		return null;
	}
	
}
