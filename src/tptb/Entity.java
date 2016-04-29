package tptb;

public abstract class Entity {
	
	private Location loc;
	
	public Entity(Location loc){
		this.loc = loc;
	}
	
	public Location getLocation(){
		
		return loc;
	}
}
