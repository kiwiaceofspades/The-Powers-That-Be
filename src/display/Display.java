package display;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ecs100.*;
import tptb.*;

public class Display {
	
	private final BufferedImage[] tiles;
	private final BufferedImage[] numbers;
	private final BufferedImage box;
	
	
	public Display(Tile[][] map, ArrayList<VarBlock> ent, Player[] play){
		//TODO Handle ent changes and players
		UI.getFrame().setResizable(false);
		UI.setWindowSize(1024, 576 );
		tiles = loadSpriteSheet("assets/hyptosis_tile-art-batch-1.png", 32, 15, 8);
		numbers = loadSpriteSheet("assets/tiles.png", 64, 10, 2);
		box = loadSpriteSheet("assets/RTS_Crate.png", 512, 1,1)[0];
		UI.setDivider(0.15);
		updateDisplay(map, ent, play);
		//UI.setImmediateRepaint(false);
		while (true){
			UI.sleep(100);
			updateDisplay(map, ent, play);
		}
	}
	
	private BufferedImage[] loadSpriteSheet(String filename, int imgsize, int rows, int cols){
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		BufferedImage[] sprites = new BufferedImage[rows * cols];
		
		for (int i = 0; i < rows; i++)
		{
		    for (int j = 0; j < cols; j++)
		    {
		        sprites[(i * cols) + j] = sheet.getSubimage(
		            j * imgsize,
		            i * imgsize,
		            imgsize,
		            imgsize
		        );
		    }
		}
		
		return sprites;
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
				
				
//				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				UI.drawImage(box, x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9);
				switch(e.getName()){
				case 'X': UI.drawImage(numbers[1], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				case 'Y': UI.drawImage(numbers[3], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				case 'Z': UI.drawImage(numbers[5], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				
				case 'A': UI.drawImage(numbers[7], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				case 'B': UI.drawImage(numbers[9], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				case 'C': UI.drawImage(numbers[11], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				
				}
				return;
			}
		}
		
	}
	
	private void drawPlayer(Player[] play, int x, int y, double tileWidth, double tileHeight){
		if(play == null)
			return;
		
		for(int i = 0; i < 2; i++){
			if(play[i].getLocation().equals(new Loc(x, y))){
//				UI.setColor(play[i].getCol());
//				
//				UI.fillOval(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				UI.drawImage(numbers[19], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
			}
		}
	}
	
	private void drawTile(Tile tile, int x, int y, double tileWidth, double tileHeight){
		
		UI.setColor(Color.BLACK);
			if(!tile.isMoveable()){
//				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				UI.drawImage(tiles[8*(6+tile.hashCode()%2) +2+ tile.hashCode()%5], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
			}
			else{
				//TODO draw tile
//				UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				UI.drawImage(tiles[0], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
				Integer val = tile.getValue();
				if (val != null){
					UI.drawImage(numbers[val*2], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
				}
			}
		
	}
}
