package expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tptb.Game;
import tptb.VarBlock;

public class ExpressionHandler {

	private Game game;
	String[] expr;
	String[] prefix;
	
	public ExpressionHandler(Game game){
		
		this.game = game;
		
		expr = game.getExpressions();
		prefix = new String[expr.length + 1];
		
		for(int i = 0; i <= expr.length; i++){
			prefix[i] = Prefixer.infixToPrefixConvert(expr[i], false);
		}		
	}
	
	public boolean evaluateExpression(){
		Map<VarBlock, Integer> map = getValues();
		
		String[] subExpr = new String[prefix.length+1];
		
		System.out.println(prefix);
		
		for(int i = 0; i < prefix.length+1; i++){
			for(Map.Entry<VarBlock, Integer> m : map.entrySet()){
				//NOTE: If the value is >9 it will be a value of its first digit.
				subExpr[i] = prefix[i].replace(m.getKey().getName(), 
						m.getValue().toString().toCharArray()[0]);
			}
		}
		System.out.println(subExpr);
		
		Double[] vals = new Double[subExpr.length+1];
		
		for(int i = 0; i <= subExpr.length; i++){
			vals[i] = PrefixEvaluator.evaluate(new Scanner(subExpr[i]));
		}
		
		return vals[0] == vals[1];
}
	
	private Map<VarBlock, Integer> getValues(){
		
		HashMap<VarBlock, Integer> map = new HashMap<VarBlock, Integer>();
		
		for(VarBlock vb : game.getVarBlocks()){
			Integer val = game.getTileAt(vb.getLocation()).getValue();
			
			if(val != null && val >= 0)
				map.put(vb, val);
		}
		
		return map;
	}
}
