package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Vaccine extends Entity {
GamePanel gp;
public static final String objName="Vaccine";
	public OBJ_Vaccine(GamePanel gp) {
		super(gp);
		this.gp=gp;
		type=type_pickupOnly;
		name=objName;
		down1=setup("/objects/vaccine",gp.tileSize,gp.tileSize);
	}

}
