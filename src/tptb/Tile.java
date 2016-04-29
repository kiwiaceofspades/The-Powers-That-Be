package tptb;

public class Tile {
	private Loc location;
	private final boolean moveable;
	
	public Tile(Loc location){
		this(location, true);
	}
	
	public Tile(Loc location, boolean moveable){
		this.location = location;
		this.moveable = moveable;
	}
	
	public Loc getLoc(){
		return location.clone();
	}
	
	public boolean moveable(){
		return moveable;
	}
	
}
