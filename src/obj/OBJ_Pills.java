package obj;

import entity.Entity;
import Main.GamePanel;
public class OBJ_Pills extends Entity {
	GamePanel gp;
 
public OBJ_Pills(GamePanel gp) {
	super(gp);
	this.gp=gp;
	type=type_consume;
	name="pills";
	value=5;
	down1=setup("/objects/painkillers",gp.tileSize,gp.tileSize);
	description="["+name+"]/nThis might ease the /npain abit.";
    price=2; 
    stackable=true;
}
public boolean use(Entity entity) {
	gp.gameState=gp.dialogueState;
	gp.ui.currentDialogue="HP Increased by "+1+".";	
	entity.life +=2;
	if(gp.player.life>gp.player.maxLife) {
		gp.player.life=gp.player.maxLife;
	}
	gp.playSE(3);
return true;}
}
