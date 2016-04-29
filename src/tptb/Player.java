package tptb;

import java.awt.Color;

public class Player extends Entity{
	
	private String name;
	
	public Player(Loc loc, String name){
		super(loc);
		this.name = name;
	}
	
	
	public String getName(){return name;}
	
}
