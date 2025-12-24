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
	import obj.OBJ_Teddy;

	public class MON_Spitter extends Entity {

	    public MON_Spitter(GamePanel gp) {
	        super(gp);
	     this.gp=gp; 
	        type = type_monster; // monster
	        name = "Spitter";
	        defaultSpeed = 2;
	        speed=defaultSpeed;
	        maxLife = 15;
	        life = maxLife;
	        attack=3;
	        defence=5;
	        exp=15; 
	        projectile=new OBJ_Puke(gp);
	        
	        solidArea.x = 3;
	        solidArea.y = 18;
	        solidArea.width = 42;
	        solidArea.height = 30;
	        solidAreaDefaultX = solidArea.x;
	        solidAreaDefaultY = solidArea.y;
            
	        getImage();
	    }

	    public void getImage() {
	    	up1 = setup("/monster/spitter_up1",gp.tileSize,gp.tileSize);
	        up2 = setup("/monster/spitter_up2",gp.tileSize,gp.tileSize);
	        down1 = setup("/monster/spitter_down1",gp.tileSize,gp.tileSize);
	        down2 = setup("/monster/spitter_down2",gp.tileSize,gp.tileSize);
	        left1 = setup("/monster/spitter_left1",gp.tileSize,gp.tileSize);
	        left2 = setup("/monster/spitter_left2",gp.tileSize,gp.tileSize);
	        right1 = setup("/monster/spitter_right1",gp.tileSize,gp.tileSize);
	        right2 = setup("/monster/spitter_right2",gp.tileSize,gp.tileSize);
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

	    public void damageReaction() {
	    	actionLockCounter=0;
	    	
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
