package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import expression.*;
import tptb.*;

public class ExpressionTests {
	
	public Map<VarBlock, Integer> getMap(){
		Map<VarBlock, Integer> map = new HashMap<VarBlock, Integer>();
		
		map.put(new VarBlock(new Loc(0, 0), 'X'), 2);
		map.put(new VarBlock(new Loc(0, 0), 'Y'), 3);
		map.put(new VarBlock(new Loc(0, 0), 'Z'), 4);
		
		
		return map;
	}
	
	
		
	@Test
	public void test() {
		String arg = Prefixer.infixToPrefixConvert("Y * Y + X", true);
		System.out.println(arg);
		arg = ExpressionHandler.removeBrackets(arg);
		System.out.println(arg);
		arg = ExpressionHandler.subValues(arg, getMap());
		System.out.println(arg);
		double ans = PrefixEvaluator.evaluate(new Scanner(arg));
		
		System.out.println(ans);
		
		assert(ans == 0);
		
		
		
	}

}
