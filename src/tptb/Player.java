package tptb;

import java.awt.Color;

public class Player extends Entity{
	
	private String name;
	private Color col;
	
	public Player(Location loc, String name, Color col){
		super(loc);
		this.name = name;
		this.col = col;
	}
	
	public Color getCol(){ return col;}
	
	public String getName(){return name;}
	
	public boolean move(){
		return false;
	}
}
