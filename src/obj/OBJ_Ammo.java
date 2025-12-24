package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Ammo extends Entity{
GamePanel gp;
	public OBJ_Ammo(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Ammo";
		image=setup("/objects/ammo",gp.tileSize,gp.tileSize);
		image2=setup("/objects/ammo_empty",gp.tileSize,gp.tileSize);
	}

}
