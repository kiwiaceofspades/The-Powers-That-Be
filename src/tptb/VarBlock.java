package tptb;

public class VarBlock extends Entity{
	private char name;
	private boolean isMimic;
	
	public VarBlock(Loc loc, char name){
		super(loc);
		this.name = name;
		isMimic = false;
	}
	
	public VarBlock(Loc loc, char name, boolean mimic){
		super(loc);
		this.name = name;
		isMimic = mimic;
	}
	
	public char getName(){return name;}
	
	public boolean isMimic(){return isMimic;}
}
