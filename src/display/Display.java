package display;

import java.awt.Color;
import java.awt.Graphics2D;
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
//	private final Graphics2D g;
	
	
	public Display(Tile[][] map, ArrayList<VarBlock> ent, Player[] play){
		//TODO Handle ent changes and players
		UI.getFrame().setResizable(false);
		UI.setWindowSize(1024, 576 );
		tiles = loadSpriteSheet("assets/hyptosis_tile-art-batch-1.png", 32, 15, 8);
		numbers = loadSpriteSheet("assets/tiles.png", 64, 10, 2);
		box = loadSpriteSheet("assets/RTS_Crate.png", 512, 1,1)[0];
		UI.setDivider(0.15);
		BufferedImage b = new BufferedImage(1024, 576, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = b.createGraphics();
		System.err.println("adsfsdf");
//		b.
		updateDisplay(map, ent, play, g);
//		UI.setImmediateRepaint(false);
		while (true){
			UI.sleep(100);
			g.fillRect(0, 0, b.getWidth(), b.getHeight());
			updateDisplay(map, ent, play, g);
			UI.drawImage(b, 0, 0);
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
	
	private void updateDisplay(Tile[][] map, ArrayList<VarBlock> ent, Player[] play, Graphics2D g){
//		UI.clearGraphics();
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
				
				drawTile(map[x][y], x, y, tilesize, tilesize, g);
				
				drawVarBlock(ent, x, y, tilesize, tilesize, g);
				
				drawPlayer(play, x, y, tilesize, tilesize, g);
			}
		}
	}
	
	private void drawVarBlock(ArrayList<VarBlock> ent, int x, int y, double tileWidth, double tileHeight, Graphics2D g){	
		
		if(ent == null)
			return;
		
		for(VarBlock e : ent){
			if(e.getLocation().equals(new Loc(x, y))){
				//TODO draw entity
					UI.setColor(Color.RED);
				
				
//				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
//				UI.drawImage(box, x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9);
				g.drawImage(box, (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas);
//				UI.println(g);
//				UI.println(UI.theUI.canvas);
				switch(e.getName()){
				case 'X': g.drawImage(numbers[1], (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas); break;
				case 'Y': g.drawImage(numbers[3], (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas); break;
				case 'Z': g.drawImage(numbers[5], (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas); break;
				
				case 'A': g.drawImage(numbers[7], (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas); break;
				case 'B': g.drawImage(numbers[9], (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas); break;
				case 'C': g.drawImage(numbers[11], (int) (x*tileWidth+tileWidth*0.05), (int) (y*tileHeight+tileHeight*0.05), (int) (tileWidth*0.9), (int)(tileHeight*0.9), UI.theUI.canvas); break;
				
				
//				case 'X': UI.drawImage(numbers[1], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
//				case 'Y': UI.drawImage(numbers[3], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
//				case 'Z': UI.drawImage(numbers[5], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
//				
//				case 'A': UI.drawImage(numbers[7], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
//				case 'B': UI.drawImage(numbers[9], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
//				case 'C': UI.drawImage(numbers[11], x*tileWidth+tileWidth*0.05, y*tileHeight+tileHeight*0.05, tileWidth*0.9, tileHeight*0.9); break;
				
				}
				return;
			}
		}
		
	}
	
	private void drawPlayer(Player[] play, int x, int y, double tileWidth, double tileHeight, Graphics2D g){
		if(play == null)
			return;
		
		for(int i = 0; i < 2; i++){
			if(play[i].getLocation().equals(new Loc(x, y))){
//				UI.setColor(play[i].getCol());
//				
//				UI.fillOval(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
//				UI.drawImage(numbers[19], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
				g.drawImage(numbers[19], (int) (x*tileWidth), (int) (y*tileHeight), (int) tileWidth, (int) tileHeight, UI.theUI.canvas);
			}
		}
	}
	
	private void drawTile(Tile tile, int x, int y, double tileWidth, double tileHeight, Graphics2D g){
		
		UI.setColor(Color.BLACK);
			if(!tile.isMoveable()){
//				UI.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
//				UI.drawImage(tiles[8*(6+tile.hashCode()%2) +2+ tile.hashCode()%5], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
				g.drawImage(tiles[8*(6+tile.hashCode()%2) +2+ tile.hashCode()%5], (int) (x*tileWidth), (int) (y*tileWidth), (int)tileWidth, (int)tileHeight, null);
			}
			else{
				//TODO draw tile
//				UI.drawRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
//				UI.drawImage(tiles[0], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
				g.drawImage(tiles[0], (int) (x*tileWidth), (int) (y*tileWidth), (int)tileWidth, (int)tileHeight, null);
				Integer val = tile.getValue();
				if (val != null){
//					UI.drawImage(numbers[val*2], x*tileWidth, y*tileWidth, tileWidth, tileHeight);
					g.drawImage(numbers[val*2], (int) (x*tileWidth), (int) (y*tileWidth), (int)tileWidth, (int)tileHeight, null);
				}
			}
		
	}
}
