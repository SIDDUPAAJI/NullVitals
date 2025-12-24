package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Torch extends Entity {

	public OBJ_Torch(GamePanel gp) {
		super(gp);
		type=type_light;
		name="torch";
		down1=setup("/objects/torch",gp.tileSize,gp.tileSize);
		description="[Torch]\nThis will help me see.";
		price=13;
		lightRadius=225;
	}

}
