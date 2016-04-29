package tptb;

public abstract class Entity {
	
	private Loc loc;
	
	public Entity(Loc loc){
		this.loc = loc;
	}
	
	public Loc getLocation(){
		
		return loc;
	}
}
