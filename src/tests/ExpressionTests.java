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
	
	
	public Double evaluate(String s){
		String arg = Prefixer.infixToPrefixConvert(s, true);
		arg = ExpressionHandler.subValues(arg, getMap());
		return PrefixEvaluator.evaluate(arg);
	}
	
		
	@Test
	public void testMultiply() {	
		assertTrue(evaluate("X * Y") == 6);
	}
	
	@Test
	public void testDivision() {	
		assertTrue(evaluate("Z / X") == 2);
	}
	
	@Test
	public void testAddition() {	
		assertTrue(evaluate("X + X") == 4);
	}
	
	@Test
	public void testSubtraction() {	
		assertTrue(evaluate("X - X") == 0);
	}
	
	@Test
	public void testBrackets() {	
		System.out.println(evaluate("Z * ( X - Y )"));
		assertTrue(evaluate("Z * ( X - Y )") == -4);
	}
	
	@Test
	public void testExponents() {
		assertTrue(evaluate("Y ^ X") == 9);
	}
	
}
