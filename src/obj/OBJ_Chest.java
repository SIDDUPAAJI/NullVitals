package obj;
import Main.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity {
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		name="Chest";
		
			image=setup("/objects/Chest_1",gp.tileSize,gp.tileSize);
			

}
}