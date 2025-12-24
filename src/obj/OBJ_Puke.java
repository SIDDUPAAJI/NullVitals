package obj;

import Main.GamePanel;
import entity.Projectile;

public class OBJ_Puke extends Projectile {

	public OBJ_Puke(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="puke";
		speed=7;
		maxLife=70;
		life=maxLife;
		attack=1;
		defence=2;
		useCost=1;
		alive=false;
		getImage();
	}
public void getImage() {
	up1=setup("/projectile/puke_up",gp.tileSize,gp.tileSize);
	up2=setup("/projectile/puke_up",gp.tileSize,gp.tileSize);
	down1=setup("/projectile/puke_down",gp.tileSize,gp.tileSize);
	down2=setup("/projectile/puke_down",gp.tileSize,gp.tileSize);
	left1=setup("/projectile/puke_left",gp.tileSize,gp.tileSize);
	left2=setup("/projectile/puke_left",gp.tileSize,gp.tileSize);
	right1=setup("/projectile/puke_right",gp.tileSize,gp.tileSize);
	right2=setup("/projectile/puke_right",gp.tileSize,gp.tileSize);
}

}