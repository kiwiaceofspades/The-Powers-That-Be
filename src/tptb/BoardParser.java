package tptb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import expression.ExpressionHandler;

public class BoardParser {
	
	public BoardParser(String board_file, Game g){
		try {
			Scanner sc = new Scanner(new File(board_file));
			
			Tile[][] tiles = parseBoard(sc);
			
			Player[] players = new Player[2];
			int playIndex = 0;
			ArrayList<VarBlock> vars = new ArrayList<VarBlock>();
			String[] expr = new String[2];
			
			while(sc.hasNext()){
				String s = sc.next();
				if(s.equalsIgnoreCase("player")){
					players[playIndex++] = parsePlayer(sc);
				} else if(s.equalsIgnoreCase("vb")){
					vars.add(parseVarBlock(sc));
				} else if(s.equalsIgnoreCase("eq")){
					expr = parseEquation(sc);
				}
			}
			
			g.setBoard(tiles);
			g.setPlayers(players);
			g.setVarBlocks(vars);
			g.setExpression(expr);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String[] parseEquation(Scanner sc) {
		String[] equation = new String[2];
		String next = sc.nextLine();
		StringTokenizer st = new StringTokenizer(next, "=");
		equation[0] = st.nextToken();
		equation[1] = st.nextToken();
		return equation;
		
	}

	private static VarBlock parseVarBlock(Scanner sc) {
		int x = sc.nextInt();
		int y = sc.nextInt();
		String value = sc.next().toUpperCase();
		return new VarBlock(new Loc(x, y), value.charAt(0));
		
	}

	public static Tile[][] parseBoard(Scanner sc){
		int width = sc.nextInt();
		int height = sc.nextInt();
		Tile[][] tiles = new Tile[width][height];
		sc.nextLine(); 
		for(int y = 0; y < height; y++){
			if(!sc.hasNext()){
				System.out.println("Parse Failure");
				break;
			}
			String next = sc.nextLine();
			char []row = next.toCharArray();
			for(int x = 0; x < width; x++){
				if(row[x] == '.'){
					tiles[x][y] = new Tile(x, y, false);
				}
				else if(row[x] == ' '){
					tiles[x][y] = new Tile(x, y);
				}
				else if(Character.isDigit(row[x])){
					tiles[x][y] = new Tile(x, y, Integer.parseInt(String.valueOf(row[x])));
				}
			}
		}
		return tiles;
	}
	
	public static Player parsePlayer(Scanner sc){
		Scanner lnsc = new Scanner(sc.nextLine());
		
		int x = lnsc.nextInt();
		int y = lnsc.nextInt();
		String name = "";
		if(lnsc.hasNext())
			name = lnsc.next();
		
		
		return new Player(new Loc(x, y), name);
		
	}
}
