package obj;
import Main.GamePanel;
import entity.Entity;

public class OBJ_Corpse extends Entity{
	
	public OBJ_Corpse(GamePanel gp) {
		super(gp);
		name="Corpse";
			image=setup("/objects/corpse_1",gp.tileSize,gp.tileSize);
		collision=true;
	}

}