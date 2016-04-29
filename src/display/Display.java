package display;

import ecs100.*;

public class Display {
	
	private static int GRID_WIDTH = 11;
	private static int GRID_HEIGHT = 10;
	
	private static int GAME_SPACE_WIDTH = UI.getCanvasWidth();
	private static int GAME_SPACE_HEIGHT = UI.getCanvasHeight();
	
	public Display(){
		drawMap();
	}
	
	private void drawMap(){		
		
		for(int x = 0; x < GRID_WIDTH; x++){
			for(int y = 0; y < GRID_HEIGHT; y++){
				
				double tileWidth = GAME_SPACE_WIDTH/GRID_WIDTH;
				double tileHeight = GAME_SPACE_HEIGHT/GRID_HEIGHT;
				
				if(x != GRID_WIDTH/2){
					UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				}
				else{
					UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				}
					
					
				
				
			}
		}
	}
}
