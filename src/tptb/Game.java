package tptb;

import java.util.ArrayList;

public class Game {
	
	public enum Direction { Up, Down, Left, Right}
	
	private Tile board[][];
	
	private ArrayList<Entity> onBoard;
	
	public Game(){
		
	}
	
	public boolean moveable(Player pl, Direction dir){
		return false;
	}
	
	
}
