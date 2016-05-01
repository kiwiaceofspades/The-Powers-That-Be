package tptb;

import java.awt.Color;

public class Player extends Entity{
	
	private String name;
	private boolean alive;
	
	public Player(Loc loc, String name){
		super(loc);
		this.name = name;
		alive = true;
	}
	
	
	public String getName(){return name;}

	public boolean isAlive(){return alive;}
	public void die() {
		alive = false;
		
	}
	
}
