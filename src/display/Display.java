package display;

import java.awt.Color;
import java.util.ArrayList;

import ecs100.*;
import tptb.*;

public class Display {
	
	
	
	
	
	public Display(Tile[][] map, ArrayList<VarBlock> ent, Player[] play){
		//TODO Handle ent changes and players
		UI.getFrame().setResizable(false);
		UI.setWindowSize(1024, 576 );
		UI.setDivider(0.15);
		updateDisplay(map, ent, play);
		while (true){
			UI.sleep(100);
			updateDisplay(map, ent, play);
		}
	}
	
	private void updateDisplay(Tile[][] map, ArrayList<VarBlock> ent, Player[] play){
		UI.clearGraphics();
		// get window size
		double w = UI.getCanvasWidth();
		double h = UI.getCanvasHeight();
		//get board aspect ratio
		double aspectRatio = (double)map.length/(double)map[0].length;
		double tilesize;
		if((w/h)<aspectRatio){//if the draw area is wider than he board
			tilesize = (double)w/map.length;
		}
		else {//if the draw area is taller than the board
			tilesize = (double)h/map[0].length;
		}
		if(map == null || ent == null){
			return;
		}
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				drawTile(map[x][y], x, y, tilesize, tilesize);
				
				drawVarBlock(ent, x, y, tilesize, tilesize);
				
				drawPlayer(play, x, y, tilesize, tilesize);
			}
		}
	}
	
	private void drawVarBlock(ArrayList<VarBlock> ent, int x, int y, double tileWidth, double tileHeight){	
		
		if(ent == null)
			return;
		
		for(VarBlock e : ent){
			if(e.getLocation().equals(new Loc(x, y))){
				//TODO draw entity
					UI.setColor(Color.RED);
				
				
				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				return;
			}
		}
		
	}
	
	private void drawPlayer(Player[] play, int x, int y, double tileWidth, double tileHeight){
		if(play == null)
			return;
		
		for(int i = 0; i < 2; i++){
			if(play[i].getLocation().equals(new Loc(x, y))){
				UI.setColor(play[i].getCol());
				
				UI.fillOval(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
			}
		}
	}
	
	private void drawTile(Tile tile, int x, int y, double tileWidth, double tileHeight){
		
		UI.setColor(Color.BLACK);
			if(!tile.isMoveable()){
				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
			}
			else{
				//TODO draw tile
				UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
			}
		
	}
}
