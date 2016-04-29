package tptb;

public class VarBlock extends Entity{
	private char name;
	
	public VarBlock(Loc loc, char name){
		super(loc);
		this.name = name;
	}
	
	public char getName(){return name;}
}
