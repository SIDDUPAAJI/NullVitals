package obj;

import Main.GamePanel;
import entity.Entity;
import entity.Projectile;

public class OBJ_Fireball extends Projectile {
GamePanel gp;
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Firball";
		speed=10;
		maxLife=30;
		life=maxLife;
		attack=2;
		useCost=1;
		alive=false;
		getImage();
	}
public void getImage() {
	up1=setup("/projectile/up",gp.tileSize,gp.tileSize);
	up2=setup("/projectile/up",gp.tileSize,gp.tileSize);
	down1=setup("/projectile/down",gp.tileSize,gp.tileSize);
	down2=setup("/projectile/down",gp.tileSize,gp.tileSize);
	left1=setup("/projectile/left",gp.tileSize,gp.tileSize);
	left2=setup("/projectile/left",gp.tileSize,gp.tileSize);
	right1=setup("/projectile/right",gp.tileSize,gp.tileSize);
	right2=setup("/projectile/right",gp.tileSize,gp.tileSize);
}
public boolean haveResource(Entity user) {
	boolean haveResource=false;
	if(user.ammo>=useCost) {
		haveResource=true;
	}
	
	return haveResource;
	}
public void subtractResource(Entity user) {
	user.ammo -=useCost;
}
}
