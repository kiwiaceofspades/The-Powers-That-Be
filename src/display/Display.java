package display;

import java.util.ArrayList;

import ecs100.*;
import tptb.*;

public class Display {
			
	private static int GAME_SPACE_WIDTH = UI.getCanvasWidth();
	private static int GAME_SPACE_HEIGHT = UI.getCanvasHeight();
	
	
	
	public Display(Tile[][] map, ArrayList<Entity> ent){
		updateDisplay(map, ent);
	}
	
	private void updateDisplay(Tile[][] map, ArrayList<Entity> ent){
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				
				
				drawTile(map, x, y);
				
				drawEntity(ent, x, y);
			}
		}
	}
	
	private void drawEntity(ArrayList<Entity> ent, int x, int y){		
		for(Entity e : ent){
			if(e.getLocation().equals(new Loc(x, y))){
				
			}
		}
		
	}
	
	private void drawTile(Tile[][] map, int x, int y){
		double tileWidth = GAME_SPACE_WIDTH/map.length;
		double tileHeight = GAME_SPACE_HEIGHT/map[x].length;
		
		UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
	}
}
