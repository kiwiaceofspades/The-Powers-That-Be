package tptb;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import display.Display;
import ecs100.UI;
import ecs100.UIButtonListener;
import ecs100.UIKeyListener;
import expression.ExpressionHandler;

public class Game implements UIKeyListener, UIButtonListener{
	
	public enum Direction { Up, Down, Left, Right}
	
	private Tile board[][];
	
	private ArrayList<VarBlock> onBoard;
	private Player[] players;
	private ArrayList<String> boards;
	private String[] exprs;
	ExpressionHandler verify;
	private JTextArea textArea;
	private int level;
	private int maxLevel = 3;
	private Display display;
	//----------------------------------------------
	//Constructor
	//----------------------------------------------
	
	public Game(){
		
		MusicBox.LoadMusic();
		MusicBox.play();
		
		level = 0;
		UI.setKeyListener(this);
		boards = new ArrayList<String>();
		for(int i = 1; i <= maxLevel+1; i++){
			boards.add("Board"+i);
			System.out.println(boards.get(i-1));
		}
		new BoardParser(boards.get(level), this);
		verify = new ExpressionHandler(this);
		setupTextArea();
		//UI.println(joinExpressions());
		UI.println(exprs[0]);
		UI.println("=");
		UI.println(exprs[1]);
		display = new Display(board, onBoard, players);
		UI.addButton("Reset", this);
		display.runDisplay();
	}
	
	//----------------------------------------------
	//Setup of a level
	//----------------------------------------------
	
	public void setupLevel(){
		new BoardParser(boards.get(level), this);
		verify = new ExpressionHandler(this);
		setupTextArea();
		UI.println(exprs[0]);
		UI.println("=");
		UI.println(exprs[1]);
		display.updateLevel(board, onBoard, players);
	}
	
	private void resetLevel(){
		new BoardParser(boards.get(level), this);
		verify = new ExpressionHandler(this);
		setupTextArea();
		display.updateLevel(board, onBoard, players);
		
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
		if(verify.evaluateExpression()){
			UI.println("CORRECT!");
			if(level < maxLevel){
				level++;
				resetLevel();
			}
		}
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
	
	private void setupTextArea(){
		JComponent jcp = (JComponent)UI.theUI.canvas;
		JSplitPane jsp = (JSplitPane) jcp.getParent().getParent().getParent();
		JScrollPane jscp = (JScrollPane) jsp.getLeftComponent();
		textArea = (JTextArea) jscp.getViewport().getView();
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		Font font = new Font("Verdana", Font.BOLD, 14);
		textArea.setFont(font);
		textArea.setEditable(false);
	}

	@Override
	public void buttonPerformed() {
		setupLevel();
		
	}
	
}
