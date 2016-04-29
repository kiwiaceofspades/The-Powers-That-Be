package tptb;

public class Tile {
	private Loc location;
	private final boolean moveable;
	private final Integer value;
	
	public Tile(int x, int y){
		this.location = new Loc(x, y);
		this.moveable = true;
		this.value = null;
	}
	
	public Tile(int x, int y, boolean moveable){
		this.location = new Loc(x, y);
		this.moveable = moveable;
		this.value = null;
	}
	public Tile(int x, int y, int value){
		this.location = new Loc(x, y);
		this.moveable = true;
		this.value = value;
	}
	
	public Loc getLoc(){
		return this.location.clone();
	}
	
	public boolean isMoveable(){
		return this.moveable;
	}
	
	public Integer getValue(){
		return this.value;
	}
	
}
