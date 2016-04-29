package tptb;

public class VarBlock extends Entity{
	private char name;
	
	public VarBlock(Location lco, char name){
		super(loc);
		this.name = name;
	}
	
	public char getName(){return name;}
}
