package Main;

import java.awt.BasicStroke;
import entity.Entity;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import obj.OBJ_Ammo;
import obj.OBJ_Cigs;
import obj.OBJ_Health;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font PressStart2P_40, PressStart2P_80B;
    BufferedImage heart_full, half_heart, heart_empty, ammo, ammo_empty,cigs;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int playerslotCol = 0;
    public int playerslotRow = 0;
    public int npcslotCol = 0;
    public int npcslotRow = 0;
    int subState = 0;
    public Entity npc;

    // ---- Constants for options UI ----
    private static final int VOL_BAR_WIDTH = 120;
    private static final int VOL_BAR_HEIGHT = 24;
    private static final int VOL_BAR_MAX_SCALE = 5;
    // matches Sound.volumeScale range 0..5

    public UI(GamePanel gp) {
        this.gp = gp;
        PressStart2P_40 = new Font("Press Start 2P", Font.PLAIN, 40);
        PressStart2P_80B = new Font("Press Start 2P", Font.BOLD, 80);

        // Create Object (health/ammo HUD sprites)
        Entity health = new OBJ_Health(gp);
        heart_full = health.image;
        half_heart = health.image2;
        heart_empty = health.image3;

        Entity Ammo = new OBJ_Ammo(gp);
        ammo = Ammo.image;
        ammo_empty = Ammo.image2;
        
        Entity Cigs=new OBJ_Cigs(gp);
        cigs=Cigs.down1;
    }
    private String[] endingDialogues = {
    	    "Is this the cure?",
    	    "Is it really the \nend or just the \nbeginning of upcoming \nmadness?"
    	};
    	private boolean showingEndingDialogue = false;
    	private int currentEndingDialogueIndex = 0;

    public void showMessage(String text) {

        message = text;
        messageOn = true;
        messageCounter = 0;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(PressStart2P_40);
        g2.setColor(Color.white);

        // Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMonsterLife();
        }

        // Pause state
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        // Timed on-screen message (e.g., heal message)
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(15F));
            g2.setColor(Color.white);
            g2.drawString(message, 25, 150);
            messageCounter++;
            if (messageCounter > 120) { // ~2s @ 60 FPS
                messageCounter = 0;
                messageOn = false;
            }
        }

        // Character/Inventory state
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player,true);
        }

        // Options state
        if (gp.gameState == gp.optionState) {
            drawOptionsScreen();
        }
        // Game Over State
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        // Trade State
        if (gp.gameState == gp.tradeState) {
            drawtradeScreen();
        }
    }
    
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // Empty hearts
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // Reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // Current life
        while (i < gp.player.life) {
            g2.drawImage(half_heart, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        // Max ammo (empty)
        x = gp.tileSize / 2;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while (i < gp.player.maxAmmo) {
            g2.drawImage(ammo_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // Current ammo (filled)
        x = gp.tileSize / 2;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while (i < gp.player.ammo) {
            g2.drawImage(ammo, x, y, null);
            i++;
            x += gp.tileSize;
        }
    }
public void drawMonsterLife() {
	//ENEMY HP BAR
	
	for(int i=0;i<gp.monster[1].length;i++) {
		Entity monster=gp.monster[gp.currentmap][i];
		if(monster !=null && monster.inCamera()==true) {
			if(monster.hpBarOn==true && monster.boss==false  ) {
				double oneScale=(double)gp.tileSize/monster.maxLife;
				double hpBarValue=oneScale*monster.life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gp.tileSize+2, 6);
				
				g2.setColor(new Color(255,0,30));
				g2.fillRect(monster.getScreenX(), monster.getScreenY()-15, (int)hpBarValue, 5);
			
		monster.hpBarCounter++;
		if(monster.hpBarCounter>600) {
			monster.hpBarCounter=0;
			monster.hpBarOn=false;
		}
		}
			else if(monster.boss==true) {
				double oneScale=(double)gp.tileSize*8/monster.maxLife;
				double hpBarValue=oneScale*monster.life;
				int x=gp.screenWidth/2-gp.tileSize*4;
				int y=gp.tileSize*10;
				g2.setColor(new Color(35,35,35));
				g2.fillRect(x-1,y-1 , gp.tileSize*8+2, 15);
				
				g2.setColor(new Color(255,0,30));
				g2.fillRect(x,y , (int)hpBarValue, 20);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD,12f));
				g2.setColor(Color.white);
				g2.drawString(monster.name, x+4, y-10);
			}
	}
			
			}
			
}
    public void drawTitleScreen() {
        g2.setColor(new Color(18, 1, 13));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String text = "NULL VITALS";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Shadow
        g2.setColor(new Color(79, 121, 66));
        g2.drawString(text, x + 4, y + 3);

        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Logo
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize / 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) g2.drawString(">", x - gp.tileSize, y);

        text = "CONTINUE";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) g2.drawString(">", x - gp.tileSize, y);

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) g2.drawString(">", x - gp.tileSize, y);
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // Dialogue window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {

        // Frame
        final int frameX = gp.tileSize / 5;
        final int frameY = gp.tileSize / 2;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(15f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        // Names
        g2.drawString("Level", textX, textY);                 textY += lineHeight;
        g2.drawString("Life", textX, textY);                  textY += lineHeight;
        g2.drawString("Ammo", textX, textY);                  textY += lineHeight;
        g2.drawString("Strength", textX, textY);              textY += lineHeight;
        g2.drawString("Sanity", textX, textY);                textY += lineHeight;
        g2.drawString("Energy", textX, textY);                textY += lineHeight;
        g2.drawString("Attack", textX, textY);                textY += lineHeight;
        g2.drawString("Defence", textX, textY);               textY += lineHeight;
        g2.drawString("Exp", textX, textY);                   textY += lineHeight;
        g2.drawString("Next Level", textX, textY);            textY += lineHeight;
        g2.drawString("cigs", textX, textY);                  textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);                textY += lineHeight + 15;
        g2.drawString("Armour", textX, textY);                textY += lineHeight;

        // Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;

        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = gp.player.life + "/" + gp.player.maxLife;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = gp.player.ammo + "/" + gp.player.maxAmmo;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.sanity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.energy);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.defence);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        value = String.valueOf(gp.player.cigs);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);                   textY += lineHeight;

        g2.drawImage(gp.player.Weapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.Armour.down1, tailX - gp.tileSize, textY - 24, null);
    }

    public void drawInventory(Entity entity,boolean cursor) {

    	int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol=0;
        int slotRow=0;
        if(entity==gp.player) {
        	 frameX = gp.tileSize * 8;
             frameY = gp.tileSize / 2;
             frameWidth = gp.tileSize * 6;
             frameHeight = gp.tileSize * 5;
             slotCol=playerslotCol;
             slotRow=playerslotRow;
        }
        else {
        	 frameX = gp.tileSize/3;
             frameY = gp.tileSize / 2;
             frameWidth = gp.tileSize * 6;
             frameHeight = gp.tileSize * 5;
             slotCol=npcslotCol;
             slotRow=npcslotRow;
        }
        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;

        // Draw player's items
        for (int i = 0; i < entity.inventory.size(); i++) {
            // Equip cursor
            if (entity.inventory.get(i) == entity.Weapon ||
            		entity.inventory.get(i) == entity.Armour || entity.inventory.get(i) == entity.Light) {
                g2.setColor(new Color(36, 36, 36, 180));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            //DISPLAY THE AMOUNT
            
            if(entity==gp.player && entity.inventory.get(i).amount>1) {
            	g2.setFont(g2.getFont().deriveFont(10f));
            	int amountX;
            	int amountY;
            	String s=""+entity.inventory.get(i).amount;
            	amountX=getXforAlignToRightText(s,slotX+40);
            	amountY=slotY+gp.tileSize;
            	//SHADOW
            	g2.setColor(new Color(60,60,60));
            	g2.drawString(s, amountX, amountY);
            	//NUMBER
            	g2.setColor(Color.white);
            	g2.drawString(s, amountX-3, amountY-3);
            }
            slotX += gp.tileSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }

        // Cursor
        if(cursor==true) {
        	int cursorX = slotXstart + (gp.tileSize * slotCol);
            int cursorY = slotYstart + (gp.tileSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            g2.setColor(Color.white);
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 20, 20);

            // Description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            // Description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 11F));
            int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                // FIX: split by newline, not "/n"
                for (String line : entity.inventory.get(itemIndex).description.split("/n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
        
    }
  public void drawGameOverScreen() {
    	g2.setColor(new Color(0,0,0,170));
    	g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
    	
    	int x;
    	int y;
    	String text;
    	g2.setFont(g2.getFont().deriveFont(Font.BOLD,60f));
    	
    	text="GAME OVER";
    	//SHADOW
    	g2.setColor(new Color(79, 121, 66));
    	x=getXforCenteredText(text);
    	y=gp.tileSize*4;
    	g2.drawString(text, x, y);
    	//MAIN
    	g2.setColor(Color.white);
    	g2.drawString(text,x-4,y-4);
    	
    	//RETRY
    	g2.setFont(g2.getFont().deriveFont(40f));
    	text="Retry";
    	x=getXforCenteredText(text);
    	y=gp.tileSize*7;
    	g2.drawString(text, x, y);
    	if(commandNum==0){
    		g2.drawString(">", x-40, y);
    	}
    	//MENU
    	text="Quit";
    	x=getXforCenteredText(text);
    	y+=65;
    	g2.drawString(text, x, y);
    	if(commandNum==1){
    		g2.drawString(">", x-40, y);
    	}
    }
    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(15F));

        // Sub window
        int frameX = gp.tileSize * 3;
        int frameY = gp.tileSize+25;
        int frameWidth = (gp.tileSize * 8)+25;
        int frameHeight = (gp.tileSize * 8)+25;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Clamp commandNum according to which submenu we're in
        if (subState == 0) {
            // 5 items: Music(0), SE(1), Controls(2), End Game(3), Back(4)
            commandNum = clamp(commandNum, 0, 4);
        } else if (subState == 2) {
            // Only "Back" is selectable here -> index 0
            commandNum = 0;
        }

        switch (subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: break;
            case 2: options_control(frameX,frameY); break;
        }
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "SETTINGS";
        textX = getXforCenteredText(text)+25;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Music label
        textX = frameX + gp.tileSize;
        textY += gp.tileSize + 10;
        g2.drawString("Music", textX, textY);
        if (commandNum == 0) g2.drawString(">", textX - 25, textY);

        // SE label
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 1) g2.drawString(">", textX - 25, textY);

        // Controls
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            // Only enter Controls if it's actually selected
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;               // controls submenu has only "Back"
                gp.keyH.enterPressed = false; // DEBOUNCE
            }
        }

        // End game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                // Optional: send back to title
                gp.gameState = gp.titleState;
                gp.keyH.enterPressed = false; // DEBOUNCE
            }
        }

        // Back
        textY += gp.tileSize + 30;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                // Return to play
                gp.gameState = gp.playState;
                gp.keyH.enterPressed = false; // DEBOUNCE
            }
           
        }

        // Volume bars (shared logic)
        int barX = frameX + (int) (gp.tileSize * 4.5);
        int musicBarY = frameY + gp.tileSize + 40;
        int seBarY = musicBarY + gp.tileSize;

        g2.setStroke(new BasicStroke(3));

        // Draw both bars via the same helper (prevents overflow/misalignment)
        drawVolumeBar(barX, musicBarY, VOL_BAR_WIDTH, VOL_BAR_HEIGHT, gp.music.volumeScale);
        drawVolumeBar(barX, seBarY,     VOL_BAR_WIDTH, VOL_BAR_HEIGHT, gp.se.volumeScale);
    
        gp.config.saveConfig();
    }

    // ---- Shared volume bar drawing (prevents overflow) ----
    private void drawVolumeBar(int x, int y, int width, int height, int volumeScale) {
        int clamped = clamp(volumeScale, 0, VOL_BAR_MAX_SCALE);
        int stepWidth = width / VOL_BAR_MAX_SCALE; // 120/5 = 24 (matches your original)
        int fillWidth = stepWidth * clamped;

        // Border
        g2.setColor(Color.white);
        g2.drawRect(x, y, width, height);

        // Fill
        g2.fillRect(x, y, fillWidth, height);
    }

    public void options_control(int frameX,int frameY) {
        int textX;
        int textY;

        // TITLE
        String text="Controls";
        textX=getXforCenteredText(text);
        textY=frameY+gp.tileSize;
        g2.drawString(text,textX,textY);

        // LEFT column
        textX=frameX+gp.tileSize;
        textY +=gp.tileSize;
        g2.drawString("MOVE", textX, textY);                textY +=gp.tileSize;
        g2.drawString("CONFIRM/ATTACK", textX, textY);      textY +=gp.tileSize;
        g2.drawString("SHOOT", textX, textY);               textY +=gp.tileSize;
        g2.drawString("CHAR SCREEN", textX, textY);         textY +=gp.tileSize;
        g2.drawString("PAUSE", textX, textY);               textY +=gp.tileSize;
        g2.drawString("SETTINGS", textX, textY);            textY +=gp.tileSize;

        // RIGHT column
        textX=frameX+gp.tileSize*6;
        textY=frameY+gp.tileSize*2;
        g2.drawString("WASD", textX, textY);                textY +=gp.tileSize;
        g2.drawString("ENTER", textX, textY);               textY +=gp.tileSize;
        g2.drawString("F", textX, textY);                   textY +=gp.tileSize;
        g2.drawString("Q", textX, textY);                   textY +=gp.tileSize;
        g2.drawString("P", textX, textY);                   textY +=gp.tileSize;
        g2.drawString("ESCAPE", textX, textY);              textY +=gp.tileSize;

        // BACK
        textX=frameX+gp.tileSize;
        textY=frameY+gp.tileSize*8;
        g2.drawString("Back",textX,textY);

        // Only index 0 is valid here; ensure arrow shows properly
        if (commandNum != 0) commandNum = 0;
        g2.drawString(">", textX-25, textY);

        if (gp.keyH.enterPressed) {
            subState = 0;                 // return to top-level options
            commandNum = 2;               // focus back on "Controls" entry
            gp.keyH.enterPressed = false; // DEBOUNCE
        }
    }
    public void drawtradeScreen() {

    	switch(subState) {
    	case 0:trade_select();break;
    	case 1:trade_buy();break;
    	case 2:trade_sell();break;
    	}
    	gp.keyH.enterPressed=false;
    }
    public void trade_select() {

    	drawDialogueScreen();
    	//draw window
    	int x=gp.tileSize*9;
    	int y=gp.tileSize*4;
    	int width=gp.tileSize*3;
    	int height=(int)(gp.tileSize*3.5);
    	drawSubWindow(x,y,width,height);
    	
    	//DRAW TEXTS
    	x += gp.tileSize-19;
    	y += gp.tileSize;
    	g2.drawString("Buy", x, y);
    	if(commandNum==0) {
    		g2.drawString(">",x-22,y);
    		if(gp.keyH.enterPressed==true) {
    			subState=1;
    		}
    	}
    	y+=gp.tileSize;
    	g2.drawString("Sell", x, y);
    	if(commandNum==1) {
    		g2.drawString(">",x-22,y);
    		if(gp.keyH.enterPressed==true) {
    			subState=2;
    		}
    	}
    	y+=gp.tileSize;
    	g2.drawString("Leave", x, y);
    	if(commandNum==2) {
    		g2.drawString(">",x-22,y);
    		if(gp.keyH.enterPressed==true) {
    			commandNum=0;
    			gp.gameState=gp.dialogueState;
    			currentDialogue="Bring more cigs \nnext time!";
    		}
    	}
    	y+=gp.tileSize;
    }
    public void trade_buy() {
        // draw player inventory
        drawInventory(gp.player, false);
        // draw npc inventory
        drawInventory(npc, true);

        // Draw hint window
        int x = gp.tileSize / 3;
        int y = (int) (gp.tileSize * 8) + 23;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // Draw cigs window
        x = gp.tileSize * 8;
        y = (int) (gp.tileSize * 8) + 23;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Cigs in inventory: " + gp.player.cigs, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(npcslotCol, npcslotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize * 4);
            y = (int) (gp.tileSize * 5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(cigs, x + 10, y + 8, 48, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 8);
            g2.drawString(text, x - 90, y + 32);

            // BUY AN ITEM 
            if (gp.keyH.enterPressed) {
                gp.keyH.enterPressed = false; // reset so it only triggers once per press

                if (npc.inventory.get(itemIndex).price > gp.player.cigs) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "INSUFFICIENT CIGS!";
                    drawDialogueScreen();
                
                }
                else {
                	if(gp.player.canObtainItem(npc.inventory.get(itemIndex))==true) {
                		 gp.player.cigs -= npc.inventory.get(itemIndex).price;
                	}
                	else {
                		subState = 0;
                        gp.gameState = gp.dialogueState;
                        currentDialogue = "Inventory full!\nClear some items.";
                	}
                }
                
                /*else if (gp.player.inventory.size() == gp.player.maxInventorysize) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Inventory full!\nClear some items.";
                    drawDialogueScreen();
                } else {
                    gp.player.cigs -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }*/
            }
        }
    }

    public void trade_sell() {
    	//DRAW PLAYERS INVENTORY
    	drawInventory(gp.player,true);
    	int x;
    	int y;
    	int width;
    	int height;
    	 // Draw hint window
         x = gp.tileSize / 3;
         y = (int) (gp.tileSize * 8) + 23;
         width = gp.tileSize * 6;
         height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // Draw cigs window
        x = gp.tileSize * 8;
        y = (int) (gp.tileSize * 8) + 23;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Cigs in inventory: " + gp.player.cigs, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(playerslotCol, playerslotRow);
        if (itemIndex <gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 8);
            y = (int) (gp.tileSize * 5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(cigs, x + 10, y + 8, 48, 32, null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 16);
            g2.drawString(text, x-280, y + 32);

            // SELL AN ITEM (fixed condition)
            if (gp.keyH.enterPressed==true) {
               if(gp.player.inventory.get(itemIndex)==gp.player.Weapon || gp.player.inventory.get(itemIndex)==gp.player.Armour) {
            	   commandNum=0;
            	   subState=0;
            	   gp.gameState=gp.dialogueState;
            	   currentDialogue="You cannot trade an \nequipped item!";
               }
               else {
            	   if(gp.player.inventory.get(itemIndex).amount>1) {
            		   gp.player.inventory.get(itemIndex).amount--;
            	   }
            	   else {
            		   gp.player.inventory.remove(itemIndex);
            	   }
            	   
            	   gp.player.cigs +=price;
               }
               
            }
        }
    }
    public void startEndingDialogue() {
        showingEndingDialogue = true;
        currentEndingDialogueIndex = 0;
    }

    public void advanceEndingDialogue() {
        currentEndingDialogueIndex++;
        if (currentEndingDialogueIndex >= endingDialogues.length) {
            showingEndingDialogue = false;
            gp.gameState = gp.titleState;  // Return to title screen after last dialogue
        }
    }

    public void drawEndingDialogue(Graphics2D g2) {
        if (!showingEndingDialogue) return;

        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(new Font("Press Start 2P", Font.BOLD, 12));
        g2.setColor(Color.white);
        String text = endingDialogues[currentEndingDialogueIndex];
        int textWidth = g2.getFontMetrics().stringWidth(text);
        int x = gp.screenWidth / 2 - textWidth / 2;
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        g2.setFont(new Font("Press Start 2P", Font.PLAIN, 10));
        String prompt = "(Press Enter)";
        int promptWidth = g2.getFontMetrics().stringWidth(prompt);
        g2.drawString(prompt, gp.screenWidth / 2 - promptWidth / 2, y + 50);
    }

    public int getItemIndexOnSlot(int slotCol,int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 190);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
