package expression;

import java.util.HashMap;
import java.util.Map;

import tptb.Game;
import tptb.VarBlock;

public class ExpressionHandler {

	private Game game;
	
	public ExpressionHandler(Game game, String expression){
		this.game = game;
	}
	
	public boolean evaluateExpression(){
		Map<VarBlock, Integer> map = getValues();
		//TODO
		return false;
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
