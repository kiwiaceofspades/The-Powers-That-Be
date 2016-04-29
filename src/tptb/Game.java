package tptb;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import display.Display;
import ecs100.UI;
import ecs100.UIKeyListener;
import expression.ExpressionHandler;

public class Game implements UIKeyListener{
	
	public enum Direction { Up, Down, Left, Right}
	
	private Tile board[][];
	
	private ArrayList<VarBlock> onBoard;
	private Player[] players;
	private String board1 = "Board1";
	private String[] exprs;
	ExpressionHandler verify;
	
	//----------------------------------------------
	//Constructor
	//----------------------------------------------
	
	public Game(){
		
		UI.setKeyListener(this);
		new BoardParser(board1, this);
		ExpressionHandler verify = new ExpressionHandler(this);
		UI.print(joinExpressions());
		new Display(board, onBoard, players);
	}
	
	//----------------------------------------------
	//Main
	//----------------------------------------------
	
	public static void main(String[] args){
		new Game();
	}
	
	public String joinExpressions(){
		return exprs[0] + " = " + exprs[1];
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
	
	public String[] getExpressions(){
		return exprs;
	}
	
	public void setBoard(Tile[][] tiles){
		this.board = tiles;
	}
	
	public void setVarBlocks(ArrayList<VarBlock> vars){
		this.onBoard = vars;
	}
	
	public void setPlayers(Player[] players){
		if(players.length != 2)
			throw new IllegalArgumentException("Can't have anything but two players");
		this.players = players;
	}
	
	public void setExpression(String[] exprs){
		if(exprs.length != 2)
			throw new IllegalArgumentException("Can't have anything but two expressions");
		this.exprs = exprs;
	}
	
	//----------------------------------------------
	//Movement
	//----------------------------------------------
	

	@Override
	public void keyPerformed(String key){
		
		if(players.length != 2)
			System.out.println("There is not two players");
		
		Direction d = null;
		Player p = null;
		
		switch(key){
			case "w": 		d = Direction.Up; p = players[0]; break;
			case "s": 		d = Direction.Down; p = players[0]; break;
			case "a": 		d = Direction.Left; p = players[0]; break;
			case "d": 		d = Direction.Right; p = players[0]; break;
			
			case "Up": 		d = Direction.Up; p = players[1]; break;
			case "Down": 	d = Direction.Down; p = players[1]; break;
			case "Left": 	d = Direction.Left; p = players[1]; break;
			case "Right": 	d = Direction.Right; p = players[1]; break;
			default:
		}
		if (p!=null){
			Entity e = moveable(p, d);
			if ((e = moveable(p, d))!=null){
				if (e instanceof VarBlock){
					e.move(d);
				}
				p.move(d);
			}
		}	
		
		UI.print(verify.evaluateExpression());
	}
	
	public Entity moveable(Entity en, Direction dir){
		Loc enLoc = en.getLocation();
		int enx = enLoc.getX(), eny = enLoc.getY();
		int targetx = enx, targety = eny;
		switch (dir){
			case Down: targety++; break;
			case Up: targety--; break;
			case Left: targetx--; break;
			case Right: targetx++; break;
		}
		Loc targetLoc = new Loc(targetx, targety);
		if (!getTileAt(targetLoc).isMoveable()){
			return null;
		}
		VarBlock target = null;
		for (VarBlock v: onBoard){
			if (v.getLocation().equals(targetLoc)){
				target = v;
				break;
			}
		}
		if (target == null){
			return en;
		} else if (target.equals(moveable(target, dir))){
			return target;
		} else {
			return null;
		}
	}
	
	
	
}
