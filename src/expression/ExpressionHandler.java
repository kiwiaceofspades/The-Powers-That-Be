package expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ecs100.UI;
import tptb.Game;
import tptb.VarBlock;

public class ExpressionHandler {

	private Game game;
	String[] expr;
	String[] prefix;
	
	public ExpressionHandler(Game game){
		
		this.game = game;
		
		expr = game.getExpressions();
		prefix = new String[expr.length];
		for(int i = 0; i < expr.length; i++){
			prefix[i] = Prefixer.infixToPrefixConvert(expr[i], false);
		}		
	}
	
	public boolean evaluateExpression(){
		Map<VarBlock, Integer> map = getValues();
		
		String[] subExpr = new String[prefix.length];
		
		for(int i = 0; i < prefix.length; i++){
			subExpr[i] = subValues(prefix[i], map);
		}
		
		Double[] vals = new Double[subExpr.length];
		
		for(int i = 0; i < subExpr.length; i++){
			try{
				
				vals[i] = PrefixEvaluator.evaluate(subExpr[i]);
			} catch(Exception e){
				//UI.println("Need more variables");
			}
		}
		System.out.println(vals[0] + "; " + vals[1]);
		return vals[0] != null && vals[1] != null && vals[0].doubleValue() == vals[1].doubleValue();
	}
	
	public static String subValues(String prefix, Map<VarBlock, Integer> map ){
				
		for(Map.Entry<VarBlock, Integer> m : map.entrySet()){
			//NOTE: If the value is >9 it will be a value of its first digit.
			prefix = prefix.replace(m.getKey().getName(), 
					m.getValue().toString().toCharArray()[0]);
		}
		return prefix;
	}
	
	public static String removeBrackets(String s){
		if(s == null)
			return "";
		return s.replaceAll("[(|)]", "");
	}
	
	private Map<VarBlock, Integer> getValues(){
		
		HashMap<VarBlock, Integer> map = new HashMap<VarBlock, Integer>();
		
		for(VarBlock vb : game.getVarBlocks()){
			Integer val = game.getTileAt(vb.getLocation()).getValue();
			
			if(val != null && val >= 0)
				map.put(vb, val);
		}
		System.out.println();
		return map;
	}
}
