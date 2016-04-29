package display;

import java.awt.Color;
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
				
				double tileWidth = GAME_SPACE_WIDTH/map.length;
				double tileHeight = GAME_SPACE_HEIGHT/map[x].length;
				
				drawTile(map[x][y], x, y, tileWidth, tileHeight);
				
				drawEntity(ent, x, y, tileWidth, tileHeight);
			}
		}
	}
	
	private void drawEntity(ArrayList<Entity> ent, int x, int y, double tileWidth, double tileHeight){		
		for(Entity e : ent){
			if(e.getLocation().equals(new Loc(x, y))){
				//TODO draw entity
				
				
				if(e instanceof Player){
					UI.setColor(((Player) e).getCol());
				}
				else if(e instanceof VarBlock){
					UI.setColor(Color.RED);
				}
				
				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				return;
			}
		}
		
	}
	
	private void drawTile(Tile tile, int x, int y, double tileWidth, double tileHeight){
		
		UI.setColor(Color.BLACK);
			
		//TODO draw tile
		UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
		
		
	}
}
