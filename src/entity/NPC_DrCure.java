package entity;

import java.util.Random;


import Main.GamePanel;


public class NPC_DrCure extends Entity{
	public NPC_DrCure(GamePanel gp) {
		super(gp);
		direction ="down";
		speed = 1;
		getImage();
		setDialogue();
	}
	   public void getImage() {
	       
		    up1=setup("/npc/dr_cure_up_1",gp.tileSize,gp.tileSize);
		    up2=setup("/npc/dr_cure_up_2",gp.tileSize,gp.tileSize);
		    down1=setup("/npc/dr_cure_down_1",gp.tileSize,gp.tileSize);
		    down2=setup("/npc/dr_cure_down_2",gp.tileSize,gp.tileSize);
		    left1=setup("/npc/dr_cure_left_1",gp.tileSize,gp.tileSize);
		    left2=setup("/npc/dr_cure_left_2",gp.tileSize,gp.tileSize);
		    right1=setup("/npc/dr_cure_right_1",gp.tileSize,gp.tileSize);
		    right2=setup("/npc/dr_cure_right_2",gp.tileSize,gp.tileSize);
		  
		    }
	   public void setDialogue() {
		   dialogues[0]="Not dead? Shame.I was \ntaking bets.";
		   dialogues[1]="You’re infected...Not \nfully yet.";	  
		   dialogues[2]="Inject that. Or don’t.\nI’m not here \nto babysit the dead.";
		   dialogues[3]="Vitals are stable. . .\nfor now.Don’t screw \nit up";
		   dialogues[4]="Keep moving. \nIf you slow down. . ., \nyou rot.";
	   }
		public void setAction() {
			
			actionLockCounter++;
			
			if(actionLockCounter == 120) {
				Random random=new Random();
				int i=random.nextInt(100)+1;//picks up a random number from 1 to 100
				if(i <= 25) {
					direction="up";
				}
				if(i > 25 && i <=50) {
					direction="down";
				}
				if(i > 50 && i <=75) {
					direction ="left";
				}
				if(i > 75 && i <= 100) {
					direction="right";
				}
				actionLockCounter=0;
			}
			
			
		}

public void speak() {
	//used for specific character stuff
	super.speak();
}
}
