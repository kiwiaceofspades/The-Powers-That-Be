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
	private String board1 = "Board1";
	
	public Game(){
		
		UI.setKeyListener(this);
		board = Parser.parseBoard(board1);
		onBoard = Parser.parseBlocks(board1);
		players = Parser.parsePlayers(board1);
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
			case "w": 		players[0].move(Direction.Up); break;
			case "s": 		players[0].move(Direction.Down); break;
			case "a": 		players[0].move(Direction.Left); break;
			case "d": 		players[0].move(Direction.Right); break;
			
			case "Up": 		players[1].move(Direction.Up); break;
			case "Down": 	players[1].move(Direction.Down); break;
			case "Left": 	players[1].move(Direction.Left); break;
			case "Right": 	players[1].move(Direction.Right); break;
			default:
		}
		
		
		
	}
	
	
	
}
