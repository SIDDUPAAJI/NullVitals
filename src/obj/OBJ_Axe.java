package obj;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Axe extends Entity {
public OBJ_Axe(GamePanel gp) {
	super(gp);
	type=type_axe;
	name="Fireaxe";
	down1=setup("/objects/fireaxe",gp.tileSize,gp.tileSize);
	attackValue=4;
	attackArea.width=48;
	attackArea.height=48;
	description="["+name+"]/nHeavy damage but very /nslow.";
	
}
}
