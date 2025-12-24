package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Cigs extends Entity {

	public OBJ_Cigs(GamePanel gp) {
		super(gp);
		this.gp=gp;
		type=type_pickupOnly;
		name="Cigs";
		value=1;
		down1=setup("/objects/cigs",gp.tileSize,gp.tileSize);
	}
	public boolean use(Entity entity) {
	
	gp.ui.showMessage("Cig+"+value);
	gp.player.cigs += value;
	return true;
	}
}
