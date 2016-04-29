package tptb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	
	public static Tile[][] parseBoard(String boardName){
		try {
			Scanner sc = new Scanner(new File(boardName));
			int width = sc.nextInt();
			int height = sc.nextInt();
			Tile[][] tiles = new Tile[width][height];
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
						tiles[x][y] = new Tile(x, y, true);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
		}
		
		return null;
		
	}
}
