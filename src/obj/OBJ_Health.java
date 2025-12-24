package obj;
import Main.GamePanel;
import entity.Entity;

public class OBJ_Health extends Entity {
	public OBJ_Health(GamePanel gp) {
		super(gp);
		name="Health";
		
			image=setup("/objects/heart_full",gp.tileSize,gp.tileSize);
			image2=setup("/objects/half_heart",gp.tileSize,gp.tileSize);
			image3=setup("/objects/heart_empty",gp.tileSize,gp.tileSize);
		
	}
}
