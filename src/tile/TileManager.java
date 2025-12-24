package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
 
import Main.GamePanel;
import Main.UtilityTool;

public class TileManager {
GamePanel gp;
public Tile[] tile;
public int mapTileNum[][][];

ArrayList<String>fileNames=new ArrayList<>();
ArrayList<String>collisionStatus=new ArrayList<>();
ArrayList<String> tileTypes = new ArrayList<>();


public TileManager(GamePanel gp) {
	this.gp=gp;
	
	InputStream is=getClass().getResourceAsStream("/maps/tiledata.txt");
	BufferedReader br=new BufferedReader(new InputStreamReader(is));
	
	String line;
	try {
		while ((line = br.readLine()) != null) {
		    fileNames.add(line);
		    collisionStatus.add(br.readLine());
		    tileTypes.add(br.readLine());
		}

		br.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	tile=new Tile[fileNames.size()];
	getTileImage();
	
	is=getClass().getResourceAsStream("/maps/hospitalmap.txt");
	br=new BufferedReader(new InputStreamReader(is));

	try {
		String line2=br.readLine();
		String maxTile[]=line2.split(" ");
		
		gp.maxWorldCol=maxTile.length;
		gp.maxWorldRow=maxTile.length;
		mapTileNum=new int[gp.maxMap] [gp.maxWorldCol][gp.maxWorldRow];
		
		br.close();
	}catch(IOException e) {
		System.out.println("Exception!");
	}
	loadMap("/maps/hospitalmap.txt",0);
	loadMap("/maps/secretroom.txt",1);
	loadMap("/maps/miniboss_map.txt",2);
	loadMap("/maps/bossroom.txt",3);
}
public void getTileImage() {
    for (int i = 0; i < fileNames.size(); i++) {
        String fileName = fileNames.get(i);
        boolean collision = collisionStatus.get(i).equalsIgnoreCase("true");
        String type = tileTypes.get(i);

        setup(i, fileName, collision, type);
    }
}

public void setup(int index, String imageName, boolean collision, String type) {

	UtilityTool uTool = new UtilityTool();
	try {
	    tile[index] = new Tile();
	    tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
	    tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
	    tile[index].collision = collision;
	    tile[index].isBloodTile = type.equalsIgnoreCase("blood"); // 🔥 HERE!
	} catch (IOException e) {
	    e.printStackTrace();
	}

	
}
public void loadMap(String filePath, int map) {
	try {
		InputStream is=getClass().getResourceAsStream(filePath);	
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		
		int col=0;
		int row=0;
		
		while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
			String line=br.readLine();	
			while(col<gp.maxWorldCol) {
				String numbers[]=line.split(" ");
				int num=Integer.parseInt(numbers[col]);
				
				
				mapTileNum[map][col][row]=num;
				col++;
			}
		if(col==gp.maxWorldCol) {
			col=0;
			row++;
		}
		}
		br.close();
	}catch(Exception e) {
		
	}
		
	
	
}
public void draw(Graphics2D g2) {
	
		int worldCol=0;
		int worldRow=0;
		
		
	
		while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			int tileNum=mapTileNum[gp.currentmap][worldCol][worldRow];
			int worldX=worldCol*gp.tileSize;
			int worldY=worldRow*gp.tileSize;
			int screenX=worldX-gp.player.worldX+gp.player.screenX;
			int screenY=worldY-gp.player.worldY+gp.player.screenY;
			if(worldX+gp.tileSize>gp.player.worldX-gp.player.screenX && 
			   worldX-gp.tileSize<gp.player.worldX+gp.player.screenX &&
			   worldY+gp.tileSize>gp.player.worldY-gp.player.screenY &&
		   	   worldY-gp.tileSize<gp.player.worldY+gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image,screenX,screenY, null);
				
			}
						worldCol++;
			
			if(worldCol==gp.maxWorldCol) {
				worldCol=0;
				
				worldRow++;
				
				
			}
		}
		}

	
}


