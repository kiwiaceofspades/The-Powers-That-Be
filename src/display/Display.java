package display;

import java.awt.Color;
import java.util.ArrayList;

import ecs100.*;
import tptb.*;

public class Display {
	
	
	
	
	
	public Display(Tile[][] map, ArrayList<VarBlock> ent, Player[] play){
		//TODO Handle ent changes and players
		updateDisplay(map, ent, play);
	}
	
	private void updateDisplay(Tile[][] map, ArrayList<VarBlock> ent, Player[] play){
		int w = UI.getCanvasWidth();
		int h = UI.getCanvasHeight();
		double aspectRation = map.length/map[0].length;
		double tilesize;
		if((w/h)<aspectRation){
			tilesize = (double)w/map[0].length;
		}
		else {
			tilesize = (double)h/map.length;
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
			
		//TODO draw tile
		UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
		
		
	}
}
