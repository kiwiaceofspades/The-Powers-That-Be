package tptb;

public abstract class Entity {
	
	private Loc loc;
	
	public Entity(Loc loc){
		this.loc = loc;
	}
	
	public Loc getLocation(){
		
		return loc;
	}
	
	public boolean move(Game.Direction dir){
		switch (dir){
			case Down: loc.down(); break;
			case Up: loc.up(); break;
			case Left: loc.left(); break;
			case Right: loc.right(); break;
			
		}
		return false;
	}
}
