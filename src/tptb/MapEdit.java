package tptb;


import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import static tptb.MapEdit.MapTile.*;
import ecs100.UI;

public class MapEdit {
	
	protected enum MapTile { n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, w, f };
	private enum editAction {tile, player, block, mimic}
	
	private MapTile[][] map;
	private Loc[] players;
	private List<VarBlock> entities = new ArrayList<VarBlock>();
	private String equation;
	private int rows;
	private int cols;
	
	private final BufferedImage[] tiles;
	private final BufferedImage[] numbers;
	private final BufferedImage box;
	
	private int tileSize = 32;
	private editAction action = editAction.tile;
	
	public static void main(String[] args){
		new MapEdit();
	}

	public MapEdit(){
		
		tiles = loadSpriteSheet("assets/hyptosis_tile-art-batch-1.png", 15, 8);
		numbers = loadSpriteSheet("assets/tiles.png", 10, 2);
		box = loadSpriteSheet("assets/RTS_Crate.png", 1, 1)[0];
		
		UI.initialise();
		
		// add menu entries
		JMenu fileMenu = new JMenu("File");
		JMenuItem mi = new JMenuItem("New");
		mi.addActionListener((e) -> newMap());
		fileMenu.add(mi);
		mi = new JMenuItem();
		mi.setEnabled(false);
		fileMenu.add(mi);
		mi = new JMenuItem("Open");
		mi.addActionListener((e) -> open(new JFileChooser(".")) );
		fileMenu.add(mi);
		mi = new JMenuItem("Save");
		mi.addActionListener((e) -> save(new JFileChooser(".")) );
		fileMenu.add(mi);
		mi = new JMenuItem("Close");
		mi.addActionListener((e) -> close());
		fileMenu.add(mi);
		UI.getFrame().getJMenuBar().add(fileMenu);
		UI.getFrame().setJMenuBar(UI.getFrame().getJMenuBar());
		
		UI.addButton("Mode", () -> {
			int v = action.ordinal();
			v ++;
			try {
				action = editAction.values()[v];
			} catch (IndexOutOfBoundsException e){
				v = 0;
				action = editAction.values()[v];
			}
			UI.clearText();
			UI.println("Action: " + action);
		});
		
		UI.addTextField("Equation", (e) -> {
			equation = e;
			UI.printMessage(equation);
		});
		
		UI.addButton("addLeft", () -> addColLeft());
		UI.addButton("addRight", () -> addColRight());
		UI.addButton("addTop", () -> addRowTop());
		UI.addButton("addBottom", () -> addRowBottom());
		
		UI.setMouseListener((action, x, y) -> mouse(action, x, y));
		
		newMap();
		UI.println("Action: " + action);
	}
	
	private BufferedImage[] loadSpriteSheet(String filename, int rows, int cols){
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		int width = sheet.getWidth()/cols;
		int height = sheet.getHeight()/rows;
		BufferedImage[] sprites = new BufferedImage[rows * cols];
		
		for (int i = 0; i < rows; i++)
		{
		    for (int j = 0; j < cols; j++)
		    {	
		        sprites[(i * cols) + j] = sheet.getSubimage(
		            j * width,
		            i * height,
		            width,
		            height
		        );
		    }
		}
		
		return sprites;
	}
	
	private void draw(){
		UI.clearGraphics();
		if (map == null) return;
		for (int i=0; i<cols; ++i){
			for (int j=0; j<rows; ++j){
//				map[i][j] = '.';
//				UI.drawString(String.valueOf(map[i][j]), i*10, j*10);
				switch (map[i][j]){
				case w: 
					UI.drawImage(tiles[50], i*tileSize, j*tileSize, tileSize, tileSize); 
					break;
					
				case f: 
					UI.drawImage(tiles[0], i*tileSize, j*tileSize, tileSize, tileSize); 
					  break;
					default:
						UI.drawImage(tiles[0], i*tileSize, j*tileSize, tileSize, tileSize);
						UI.drawImage(numbers[map[i][j].ordinal()*2], i*tileSize, j*tileSize, tileSize, tileSize);
				}
			}
		}
		
		for (Loc l: players){
			if (l!=null){ UI.drawImage(numbers[19], l.getX()*tileSize, l.getY()*tileSize, tileSize, tileSize); }
		}
		for (Entity e: entities){
			int x = e.getLocation().getX()*tileSize;
			int y = e.getLocation().getY()*tileSize;
			UI.drawImage(box, x, y, tileSize, tileSize);
			switch(((VarBlock)e).getName()){
				case 'x': UI.drawImage(numbers[1], x, y, tileSize, tileSize);break;
				case 'y': UI.drawImage(numbers[3], x, y, tileSize, tileSize);break;
				case 'z': UI.drawImage(numbers[5], x, y, tileSize, tileSize);break;
	
				case 'a': UI.drawImage(numbers[7], x, y, tileSize, tileSize);break;
				case 'b': UI.drawImage(numbers[9], x, y, tileSize, tileSize);break;
				case 'c': UI.drawImage(numbers[11], x, y, tileSize, tileSize);break;
			default:
			}
		}
		
	}
	
	private void newMap(){
		UI.println("new");
		rows = 7;
		cols = 7;
		map = new MapTile[cols][rows];
		for (int i=0; i<cols; ++i){
			for (int j=0; j<rows; ++j){
				map[i][j] = w;
			}
		}
		players = new Loc[2];
		map[1][1] = f;
		players[0] = new Loc(1, 1);
		map[5][5] = f;
		players[1] = new Loc(5, 5);
		
		entities.clear();
		
		draw();
	}
	private void open(JFileChooser chooser){
		UI.println("open");
		if (chooser.showSaveDialog(UI.getFrame()) != JFileChooser.APPROVE_OPTION) return;
		File file = chooser.getSelectedFile();
		if (file==null) return;
		try {
			Scanner scan = new Scanner(file);
			close();
			cols = scan.nextInt();
			rows = scan.nextInt();
			scan.nextLine();
			map = new MapTile[cols][rows];
			entities.clear();
			
			for (int i=0;i<rows;++i){
				String line = scan.nextLine();
				for (int j=0;j<cols;++j){
					switch (line.charAt(j)){
					case '.': map[j][i] = w; break;
					case ' ': map[j][i] = f; break;
						default:
							map[j][i] = MapTile.valueOf("n" + String.valueOf(line.charAt(j))); break;
					}
				}
			}
			int player = 0;
			while (scan.hasNext()){
				String type = scan.next();
				switch (type){
				case "player":
					players[player] = new Loc(scan.nextInt(), scan.nextInt());
					player++;
					break;
				case "vb":
					entities.add(new VarBlock(new Loc(scan.nextInt(), scan.nextInt()), scan.next().charAt(0)));
					break;
				case "VB":
					entities.add(new VarBlock(new Loc(scan.nextInt(), scan.nextInt()), scan.next().charAt(0), true));
					break;
				case "eq":
					equation = scan.nextLine();
					break;
					default:
						
				}
			}
				
			
			
			
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UI.printMessage(equation);
		draw();
		
	}
	private void save(JFileChooser chooser){
		UI.println("save");
		if (players[0] ==null || players[1]==null){
			UI.println("Please place both players");
			return;
		}
		
		
		if (equation==null){
			UI.println("Please Enter an equation (this may have happened if you hadn't pressed enter on the sidebar, then press enter");
			return;
		}
		if (chooser.showSaveDialog(UI.getFrame()) != JFileChooser.APPROVE_OPTION) return;
		File file = chooser.getSelectedFile();
		if (file==null) return;
		
		File f = file;
		try {
			BufferedWriter out =  new BufferedWriter(new FileWriter(f));
			
			out.write(String.format("%d %d\n", cols, rows));
			char[] line;
			for (int j=0; j<rows; ++j){
				line = new char[cols+1];
				for (int i=0; i<cols; ++i){
					switch (map[i][j]){
					case f: line[i] = ' '; break;
					case n0: line[i] = '0'; break;
					case n1: line[i] = '1'; break;
					case n2: line[i] = '2'; break;
					case n3: line[i] = '3'; break;
					case n4: line[i] = '4'; break;
					case n5: line[i] = '5'; break;
					case n6: line[i] = '6'; break;
					case n7: line[i] = '7'; break;
					case n8: line[i] = '8'; break;
					case n9: line[i] = '9'; break;
					case w: line[i] = '.'; break;
					default: line[i] = '.';
						break;
					}
				}
				line[cols] = '\n';
				out.write(line);
			}
			
			out.write(String.format("\nplayer %d %d\nplayer %d %d\n", 
					players[0].getX(), players[0].getY(), 
					players[1].getX(), players[1].getY()));
			
			for (Entity e: entities){
				VarBlock v = (VarBlock) e;
				out.write(String.format("%s %d %d %c\n",(v.isMimic()? "VB" : "vb") ,v.getLocation().getX(), v.getLocation().getY(), v.getName()));
			}
			
			out.write("\neq " + equation);
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
	}
	private void close(){
		UI.println("close");
		map = null;
	}
	
	private void addColLeft(){
		MapTile[][] m = new MapTile[cols+1][rows];
		for (int row=0; row<rows; row++){
			for (int col=0; col<cols; col++){
				m[col+1][row] = map[col][row];
			}
			m[0][row] = w;
		}
		map = m;
		cols++;
		for (Entity e: entities){
			e.move(Game.Direction.Right);
		}
		if (players[0] != null) players[0].right();
		if (players[1] != null) players[1].right();
		
		draw();
	}
	private void addColRight(){
		MapTile[][] m = Arrays.copyOf(map, cols+1);
		for (int j=0; j<cols; ++j){
			m[j] = map[j];
		}
		m[cols] = new MapTile[rows];
		for (int i=0; i<rows; ++i){
			m[cols][i]= w;
		}
		map = m;
		cols++;
		draw();
	}
	
	private void addRowTop(){
		MapTile[][] m = new MapTile[cols][rows+1];
		for (int col=0; col<cols; col++){
			for (int row=0; row<rows; row++){
				m[col][row+1] = map[col][row];
			}
			m[col][0] = w;
		}
		map = m;
		rows++;
		for (Entity e: entities){
			e.move(Game.Direction.Down);
		}
		if (players[0] != null) players[0].down();
		if (players[1] != null) players[1].down();
		
		draw();
	}
	private void addRowBottom(){
		MapTile[][] m = new MapTile[cols][rows+1];
		for (int col=0; col<cols; col++){
			m[col] = Arrays.copyOf(map[col], rows+1);
			m[col][rows] = w;
		}
		map = m;
		rows++;
		draw();
	}
	
	private void mouse(String action, double x, double y){
		// get the coords of the tile clicked on
		if (!action.equalsIgnoreCase("released")) return;
		int col = (int) x / tileSize;
		int row = (int) y / tileSize;
		if (col >= cols || row >= rows) return;
//		System.out.println(col + " " + row);
		boolean mimic = false;
		switch (this.action){
		case tile:
			// get the tile clicked on
			int tile = map[col][row].ordinal();
			tile ++;
			try {
				map[col][row] = MapTile.values()[tile];
			} catch (IndexOutOfBoundsException e){
				tile = 0;
				map[col][row] = MapTile.values()[tile];
			}
			break;
		case mimic:
			mimic = true;
		case block:
			// get the block clicked on
			VarBlock ent = null;
			Loc loc = new Loc(col,row);
			for (Entity e: entities){
				if (e.getLocation().equals(loc)) {
					ent = (VarBlock) e;
					entities.remove(ent);
					break;
				}
			}
			if (ent==null){
				ent = new VarBlock(loc, '-', mimic);
			} else {
				switch(ent.getName()){
					case '-': ent = new VarBlock(loc, 'x', mimic);break;
					case 'x': ent = new VarBlock(loc, 'y', mimic);break;
					case 'y': ent = new VarBlock(loc, 'z', mimic);break;
					case 'z': ent = new VarBlock(loc, 'a', mimic);break;
	
					case 'a': ent = new VarBlock(loc, 'b', mimic);break;
					case 'b': ent = new VarBlock(loc, 'c', mimic);break;
					case 'c': ent = new VarBlock(loc, ' ', mimic);break;
				default:
				}
			}
			if (ent.getName() != ' '){
				entities.add(ent);
			}
				
			break;
		case player:
			// get the player clicked on
			if (players[0] != null && players[0].getX()==col && players[0].getY()==row){
				players[0] = null;
			} else if (players[1] != null && players[1].getX()==col && players[1].getY()==row){
				players[1] = null;
			} else if (players[0] == null){
				players[0] = new Loc(col, row);
			} else if (players[1] == null){
				players[1] = new Loc(col, row);
			}
			break;
		default:
			break;
		}
		
		draw();
	}
}

