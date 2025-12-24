package monster;

import java.util.Random;
import entity.Entity;
import obj.OBJ_Cigs;
import obj.OBJ_Key;
import obj.OBJ_Pills;
import Main.GamePanel;

public class MON_Sprinter extends Entity {

    GamePanel gp;

    public MON_Sprinter(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Sprinter";
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 2;
        defence = 0;
        exp = 10;
        knockbackCounter = 2;
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
        up1 = setup("/monster/sprinter_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/monster/sprinter_up2",gp.tileSize,gp.tileSize);
        down1 = setup("/monster/sprinter_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/monster/sprinter_down2",gp.tileSize,gp.tileSize);
        left1 = setup("/monster/sprinter_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/monster/sprinter_left2",gp.tileSize,gp.tileSize);
        right1 = setup("/monster/sprinter_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/monster/sprinter_right2",gp.tileSize,gp.tileSize);
    }

    @Override
    public void setAction() {
        // Always chase the player
        int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
        searchPath(goalCol, goalRow);
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

    @Override
    public void checkDrop() {
    	//RANDOM(DICE) TYPE AI
    	int i=new Random().nextInt(100)+1;
    	//SET THE MONSTER DROP
    	if(i<70) {
    		dropItem(new OBJ_Cigs(gp));
    	}
    	if(i>=70 && i<90) {
    		dropItem(new OBJ_Pills(gp));
    	}
    	if(i>=90 && i<100) {
    		dropItem(new OBJ_Key(gp));
    	}
    }
  
}
