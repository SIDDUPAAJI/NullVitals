package obj;
import Main.GamePanel;
import entity.Entity;


	public class OBJ_ivbag extends Entity {
		
		public OBJ_ivbag(GamePanel gp) {
			super(gp);
			name="ivbag";
			down1 = setup("/objects/ivbag", gp.tileSize, gp.tileSize);

		instantUse = true;
		
		}

	}