package Main;

import entity.Entity;

import entity.Player;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import Environment.EnvironmentManager;
import ai.PathFinder;
import data.SaveLoad;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
    //WORLD SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 14;
    public final int maxScreenRow = 11;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentmap = 0;
    
   
    //FPS
    int FPS = 60;
    
    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    public Sound se = new Sound();
    int currentTrack = -1;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config=new Config(this);
    Thread gameThread;
    public PathFinder pfinder=new PathFinder(this);
    EnvironmentManager eManager=new EnvironmentManager(this);
    SaveLoad saveLoad=new SaveLoad(this);
    
//ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][100];
    public Entity npc[][] = new Entity[maxMap][100];
    public Entity monster[][] = new Entity[maxMap][100];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity>projectileList=new ArrayList<>();
    public InteractiveTile iTile[][]=new InteractiveTile[maxMap][50];


//GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState=4;
    public final int optionState=5;
    public final int gameOverState=6;
	public final int tradeState=7;
	public final int endingState = 8;               // Define new state for ending scene
	public int endingDialogueIndex = 0;             // To track dialogue progress during ending

	
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();
        aSetter.setNPC();
        aSetter.setInteractiveTile();
        eManager.setup();
    }
    public void retry() {
    	
    	player.setDefaultPositions();
    	player.restoreLifeAndAmmo();
    	 aSetter.setMonster();
         aSetter.setNPC();
         playMusic(0);
         System.out.println("retry() called: resetting player state");
    }
    public void restart() {
    	stopMusic(4);
    	player.setDefaultValues();
    	player.setDefaultPositions();
    	player.restoreLifeAndAmmo();
    	player.setItems();
    	 aSetter.setObject();
    	 aSetter.setMonster();
         aSetter.setNPC();
         aSetter.setInteractiveTile();
         System.out.println("restart() called: resetting player state");
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        // Handle music switching as before
        if (gameState == titleState && currentTrack != 4) {
            music.stop();
            playMusic(4);
            currentTrack = 4;
        } else if (gameState == playState && currentTrack != 0) {
            music.stop();
            playMusic(0);
            currentTrack = 0;
        }

        // Always update monsters fully to avoid freeze/infinite invincibility
        for(int i=0;i<monster[currentmap].length;i++){
            if(monster[currentmap][i]!=null){
                if(monster[currentmap][i].alive && !monster[currentmap][i].dying){
                    monster[currentmap][i].update();
                }
                if(!monster[currentmap][i].alive){
                    monster[currentmap][i].checkDrop();
                    monster[currentmap][i]=null;
                }
            }
        }

        // Freeze gameplay except monsters during endingState
        if(gameState==endingState){
            return; // skip player, NPC, projectiles, etc
        }

        if(gameState==playState){
            player.update();

            for(int i=0;i<npc[currentmap].length;i++){
                if(npc[currentmap][i]!=null){
                    npc[currentmap][i].update();
                }
            }

            for(int i=0;i<projectileList.size();i++){
                if(projectileList.get(i)!=null){
                    if(projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }else{
                        projectileList.remove(i);
                    }
                }
            }

            for(int i=0;i<iTile[currentmap].length;i++){
                if(iTile[currentmap][i]!=null){
                    iTile[currentmap][i].update();
                }
            }

            eManager.update();

            for(int i=0;i<obj[currentmap].length;i++){
                if(obj[currentmap][i]!=null && obj[currentmap][i].dying){
                    obj[currentmap][i]=null;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
       
        if (gameState == endingState) {
            ui.drawEndingDialogue((Graphics2D) g);
            return; // Skip all other drawing while showing ending scene
        }

        long drawStart = 0;
        
        if (keyH.showDebugText == true) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);
            //INTERACTIVE TILE
            for(int i=0;i<iTile[1].length;i++) {
            	if(iTile[currentmap][i]!=null) {
            		iTile[currentmap][i].draw(g2);
            	}
            }

            entityList.clear();
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentmap][i] != null) entityList.add(npc[currentmap][i]);
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentmap][i] != null) entityList.add(obj[currentmap][i]);
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentmap][i] != null)
                { entityList.add(monster[currentmap][i]);
            }
                }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null){
                	entityList.add(projectileList.get(i));
                }
                }

            Collections.sort(entityList, new Comparator<Entity>() {
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });
            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
             //EMPTTY ENTITY LIST
            entityList.clear();
            
            //ENVIRONMENT
            eManager.draw(g2);
            //UI
            ui.draw(g2);
        }
        //DEBUG

        if (keyH.showDebugText == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.white);
            int x=10;
            int y=400;
            int lineHeight=20;
            g2.drawString("WorldX: "+player.worldX, x, y);y+=lineHeight;
            g2.drawString("WorldY: "+player.worldY, x, y);y+=lineHeight;
            g2.drawString("Col: "+(player.worldX + player.solidArea.x)/tileSize, x, y);y+=lineHeight;
            g2.drawString("Row: "+(player.worldY + player.solidArea.y)/tileSize, x, y);y+=lineHeight;
            g2.drawString("Draw Time: "+passed, x, y);
        }
        g2.dispose();
    }

    public void playMusic(int i) {
        music.stop();
        music.setFile(i);
        music.loop();
        currentTrack = i;
    }

    public void stopMusic(int i) {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}