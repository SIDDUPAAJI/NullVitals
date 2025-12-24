package monster;

import java.util.Random;


import Main.GamePanel;
import entity.Entity;
import obj.OBJ_Cigs;
import obj.OBJ_Pills;
import obj.OBJ_Puke;
import obj.OBJ_Teddy;

public class MON_BloodSlug extends Entity {

    public MON_BloodSlug(GamePanel gp) {
        super(gp);
     this.gp=gp; 
        type = type_monster; // monster
        name = "Blood Slug";
        defaultSpeed = 1;
        speed=defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack=1;
        defence=0;
        exp=5; 
        
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    @Override
    public void update() {
	super.update();
}
    public void getImage() {
    	up1 = setup("/monster/bloodslug_up",gp.tileSize,gp.tileSize);
        up2 = setup("/monster/bloodslug_up",gp.tileSize,gp.tileSize);
        down1 = setup("/monster/bloodslug_down",gp.tileSize,gp.tileSize);
        down2 = setup("/monster/bloodslug_down",gp.tileSize,gp.tileSize);
        left1 = setup("/monster/bloodslug_left",gp.tileSize,gp.tileSize);
        left2 = setup("/monster/bloodslug_left",gp.tileSize,gp.tileSize);
        right1 = setup("/monster/bloodslug_right",gp.tileSize,gp.tileSize);
        right2 = setup("/monster/bloodslug_right",gp.tileSize,gp.tileSize);
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= 90) {
            Random random = new Random();
            int i = random.nextInt(100);

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
        /*int i=new Random().nextInt(100)+1;
        if(i>99 && projectile.alive==false && shotAvailableCounter==30) {
        	projectile.set(worldX, worldY, direction, true, this);
        	gp.projectileList.add(projectile);
        	shotAvailableCounter=0;
        }*/
    }
    public void damageReaction() {
    	actionLockCounter=0;
    	direction=gp.player.direction;
    }
    public void checkDrop() {
    	//RANDOM(DICE) TYPE AI
    	int i=new Random().nextInt(100)+1;
    	//SET THE MONSTER DROP
    	if(i<70) {
    		dropItem(new OBJ_Cigs(gp));
    	}
    	if(i>=70 && i<100) {
    		dropItem(new OBJ_Pills(gp));
    	}
    	
    }
}