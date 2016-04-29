package tptb;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
	
	public static Tile[][] parseBoard(String boardName){
		Tile[][] tiles;
		try {
			Scanner sc = new Scanner(new File(boardName));
			int width = sc.nextInt();
			int height = sc.nextInt();
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
						tiles[x][y] = new Tile(x, y, row[x]);
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
		}
		
		return tiles;
		
	}
	
	public static Player[] parsePlayers(String boardName){
		Player[] players = new Player[2];
		int index = 0;
		try {
			Scanner sc = new Scanner(new File(boardName));
			while(sc.hasNext()){
				String next = sc.next();
				if(next.contains("player")){
					int x = sc.nextInt();
					int y = sc.nextInt();
					if(index ==0){
						players[index] =  new Player(new Loc(x, y), "player "+index, Color.BLUE);
						index++;
					}
					else if(index == 1){
						players[index] =  new Player(new Loc(x, y), "player "+index, Color.BLUE);
						index++;
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
		}
		
		return players;
	}
	
	public static ArrayList<VarBlock> parseBlocks(String boardName){
		ArrayList<VarBlock> varBlocks = new ArrayList<VarBlock>();
		try {
			Scanner sc = new Scanner(new File(boardName));
			while(sc.hasNext()){
				String next = sc.next();
				if(next.equals("vb")){
					int x = sc.nextInt();
					int y = sc.nextInt();
					String value = sc.next();
					varBlocks.add(new VarBlock(new Loc(x, y), value.charAt(0)));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
		}
		return varBlocks;
	}
}
