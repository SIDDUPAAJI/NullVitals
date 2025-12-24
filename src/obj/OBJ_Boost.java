package obj;
import Main.GamePanel;
import entity.Entity;

public class OBJ_Boost extends Entity{
	public OBJ_Boost(GamePanel gp) {
		super(gp);
		name="Boost";
	
			image=setup("/objects/boost_1",gp.tileSize,gp.tileSize);
			
	}
}
