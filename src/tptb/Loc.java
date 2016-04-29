package tptb;

public class Loc implements Cloneable{
	private int x, y;
	
	public Loc(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

	public void left(){
		x--;
	}
	public void right(){
		x++;
	}
	public void up(){
		y--;
	}
	public void down(){
		y++;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Loc)) {
			return false;
		}
		Loc other = (Loc) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}	
	
	public Loc clone() {
		return new Loc(this.x, this.y);
	}
}
