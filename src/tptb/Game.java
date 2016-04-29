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
	
	private ArrayList<VarBlock> onBoard;
	private Player[] players = new Player[2];
	
	public Game(){
		
		UI.setKeyListener(this);
		Parser.parseBoard("Board1");
		Display d = new Display(board, onBoard, players);
		
	}
	
	public static void main(String[] args){
		new Game();
	}
	
	public boolean moveable(Player pl, Direction dir){
		return false;
	}
	
	//----------------------------------------------
	//Getters and Setters
	//----------------------------------------------
	
	public ArrayList<VarBlock> getVarBlocks(){
		return onBoard;
	}
	
	public Player[] getPlayers(){
		return players;
	}
	
	public Tile getTileAt(Loc loc){
		return board[loc.getX()][loc.getY()];
	}
	
	//----------------------------------------------
	//Input Handler
	//----------------------------------------------
	

	@Override
	public void keyPerformed(String key){
		
		if(players.length != 2)
			System.out.println("There is not two players");
		
		switch(key){
			case "w": 		players[0].move(Direction.Up);
			case "s": 		players[0].move(Direction.Down);
			case "a": 		players[0].move(Direction.Left);
			case "d": 		players[0].move(Direction.Right);
			
			case "Up": 		players[1].move(Direction.Up);
			case "Down": 	players[1].move(Direction.Down);
			case "Left": 	players[1].move(Direction.Left);
			case "Right": 	players[1].move(Direction.Right);
			
		}
		
		
		
	}
	
	
	
}
