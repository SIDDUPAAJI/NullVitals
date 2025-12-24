package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Armour extends Entity {

	public OBJ_Armour(GamePanel gp) {
		super(gp);
		type=type_armour;
		name="Armour";
		down1=setup("/objects/armour",gp.tileSize,gp.tileSize);
		defenceValue=1;
		description="["+name+"]/nThis might protect me.";
	}

}
