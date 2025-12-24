package entity;
import java.awt.Rectangle;

import Main.GamePanel;
import entity.Entity;
import obj.OBJ_Boost;
import obj.OBJ_Key;
import obj.OBJ_Pills;
import obj.OBJ_Teddy;
import obj.OBJ_Torch;


	public class NPC_Janitor extends Entity{
		public NPC_Janitor(GamePanel gp) {
			super(gp);
			direction ="down";
			speed = 0;
			solidArea=new Rectangle();
			solidArea.x=8;
			solidArea.y=16;
			solidAreaDefaultX=solidArea.x;
			solidAreaDefaultY=solidArea.y;
			solidArea.width=32;
			solidArea.height=32;
			getImage();
			setDialogue();
			setItems();
		}
		   public void getImage() {
		       
			    up1=setup("/npc/janitor_1",gp.tileSize,gp.tileSize);
			    up2=setup("/npc/janitor_2",gp.tileSize,gp.tileSize);
			    down1=setup("/npc/janitor_1",gp.tileSize,gp.tileSize);
			    down2=setup("/npc/janitor_2",gp.tileSize,gp.tileSize);
			    left1=setup("/npc/janitor_1",gp.tileSize,gp.tileSize);
			    left2=setup("/npc/janitor_2",gp.tileSize,gp.tileSize);
			    right1=setup("/npc/janitor_1",gp.tileSize,gp.tileSize);
			    right2=setup("/npc/janitor_2",gp.tileSize,gp.tileSize);
			  
			    }
		   public void setDialogue() {
			   dialogues[0]="Atlast someone found me...\nWanna trade those cigs\nfor some of my stuff?";
			   
			  
		   }
		   public void setItems() {
			   inventory.add(new OBJ_Key(gp));
			   inventory.add(new OBJ_Pills(gp));
			   inventory.add(new OBJ_Teddy(gp));
			   inventory.add(new OBJ_Torch(gp));
		   }
		   public void speak() {
			   super.speak();
			   gp.gameState=gp.tradeState;
			   gp.ui.npc=this;
		   }
}
