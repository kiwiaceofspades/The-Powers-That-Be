package tptb;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import display.Display;
import ecs100.UI;
import ecs100.UIKeyListener;

public class Game implements UIKeyListener{
	
	public enum Direction { Up, Down, Left, Right}
	
	private Tile board[][];
	
	private ArrayList<Entity> onBoard;
	
	public Game(){
		
		UI.setKeyListener(this);
		Parser.parseBoard("Board1");
		Display d = new Display(board, onBoard);
		
	}
	
	public static void main(String[] args){
		new Game();
	}
	
	public boolean moveable(Player pl, Direction dir){
		return false;
	}
	
	//----------------------------------------------
	//Input Handler
	//----------------------------------------------
	private String[] getPlayerNames(){
		
		String[] players = new String[2];
		int i = 0;
		for(Entity e : onBoard){
			if(e instanceof Player){
				players[i++] = ((Player) e).getName();
			}
			if(i>1)
				break;
		}
		
		return players;
	}
	
	public Player getPlayerByName(String name){
		for(Entity e: onBoard){
			if(e instanceof Player &&
					((Player)e).getName().equals(name)){
				return (Player)e;
			}
		}
		return null;
	}

	@Override
	public void keyPerformed(String key){
		String[] player = getPlayerNames();
		
		if(player.length != 2)
			System.out.println("There is not two players");
		
		switch(key){
			case "w": 		getPlayerByName(player[0]).move(Direction.Up);
			case "s": 		getPlayerByName(player[0]).move(Direction.Down);
			case "a": 		getPlayerByName(player[0]).move(Direction.Left);
			case "d": 		getPlayerByName(player[0]).move(Direction.Right);
			
			case "Up": 		getPlayerByName(player[1]).move(Direction.Up);
			case "Down": 	getPlayerByName(player[1]).move(Direction.Down);
			case "Left": 	getPlayerByName(player[1]).move(Direction.Left);
			case "Right": 	getPlayerByName(player[1]).move(Direction.Right);
			
		}
		
		
		
	}
	
	
	
}
