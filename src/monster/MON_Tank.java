package monster;

import java.util.Random;

import Main.GamePanel;
import entity.Entity;
import obj.OBJ_Axe;
import obj.OBJ_Cigs;
import obj.OBJ_Gun;
import obj.OBJ_Key;
import obj.OBJ_Pills;
import obj.OBJ_Puke;

public class MON_Tank extends Entity {
    GamePanel gp;

    public MON_Tank(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Tank";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 400;
        life = maxLife;
        attack = 3;
        defence = 0;
        exp = 40;
        projectile=new OBJ_Puke(gp);
        // Sprite is doubled in size, so solidArea should be adjusted
        int spriteSize = gp.tileSize * 2;

        // 🔹 Feet-aligned solid area (keeps collision natural)
        solidArea.x = 12;                  // small side margin
        solidArea.y = spriteSize - 40;     // near bottom
        solidArea.width = spriteSize - 24; // wide but not full width
        solidArea.height = 36;             // only the lower body
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // 🔹 Attack area scaled to sprite
        attackArea.width = spriteSize;
        attackArea.height = spriteSize;

        getImage();
    }

    public void getImage() {
        // 🔹 Double the size of the Tank sprite
        int size = gp.tileSize * 2;

        up1 = setup("/monster/tank_up", size, size);
        up2 = setup("/monster/tank_up", size, size);
        down1 = setup("/monster/tank_down", size, size);
        down2 = setup("/monster/tank_down", size, size);
        left1 = setup("/monster/tank_left1", size, size);
        left2 = setup("/monster/tank_left2", size, size);
        right1 = setup("/monster/tank_right1", size, size);
        right2 = setup("/monster/tank_right2", size, size);
    }

    public void setAction() {
        if(onPath==true) {
        	int goalCol=(gp.player.worldX+gp.player.solidArea.x)/gp.tileSize;
        	int goalRow=(gp.player.worldY+gp.player.solidArea.y)/gp.tileSize;
        	searchPath(goalCol,goalRow);
        	int i=new Random().nextInt(100)+1;
            if(i>50 && projectile.alive==false && shotAvailableCounter==30) {
            	projectile.set(worldX, worldY, direction, true, this);
            	gp.projectileList.add(projectile);
            	shotAvailableCounter=0;
        }
        else {
        	  actionLockCounter++;

              if (actionLockCounter >= 90) {
                  Random random = new Random();
                   i = random.nextInt(100);

                  if (i < 25) {
                      direction = "up";
                  } else if (i < 50) {
                      direction = "down";
                  } else if (i < 75) {
                      direction = "left";
                  } else {
                      direction = "right";
                  }

                  actionLockCounter = 0;
                 
              }
        }
        }
    }
    @Override
    public void update() {  
   	 super.update();
   	 
   	 int xDistance=Math.abs(worldX-gp.player.worldX);
   	 int yDistance=Math.abs(worldY-gp.player.worldY);
   	 int tileDistance=(xDistance + yDistance)/gp.tileSize;
   	 if(onPath==false && tileDistance<9) {
   		 int i=new Random().nextInt(100)+1;
   			 onPath=true;
   	 }
   	 if(onPath==true && tileDistance>12) {
   		 onPath=false;
   	 }
    }


    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        // turn towards player
        if (gp.player.worldX < worldX) direction = "left";
        if (gp.player.worldX > worldX) direction = "right";
        if (gp.player.worldY < worldY) direction = "up";
        if (gp.player.worldY > worldY) direction = "down";
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 10) {
            dropItem(new OBJ_Cigs(gp));
            dropItem(new OBJ_Pills(gp));   // drop extra with it
        }
        if (i >= 10 && i < 40) {
            dropItem(new OBJ_Pills(gp));
            dropItem(new OBJ_Gun(gp));
        }
        if (i >= 40 && i < 90) {
            dropItem(new OBJ_Gun(gp));
            dropItem(new OBJ_Cigs(gp));
        }
        if (i >= 90 && i < 100) {
            dropItem(new OBJ_Axe(gp));
            dropItem(new OBJ_Pills(gp));
            dropItem(new OBJ_Gun(gp));  // boss-tier loot
        }
        System.out.println("Boss defeated - setting dialogue");
        gp.ui.currentDialogue ="Mini-Boss:\nAbomination dead!";
        gp.gameState = gp.dialogueState;   // Switch to dialogue mode so dialogue box appears and player cannot move

    }
}
