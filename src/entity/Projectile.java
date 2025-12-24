package entity;

import Main.GamePanel;

public class Projectile extends Entity{
Entity user;
	public Projectile(GamePanel gp) {
		super(gp);
		
	}
	public void set(int worldX,int worldY,String direction,boolean alive,Entity user) {
		this.worldX=worldX;
		this.worldY=worldY;
		this.direction=direction;
		this.alive=alive;
		this.user=user;
		this.life=this.maxLife;
	}
	public void update() {
	    if(user==gp.player) {
	    	int monsterIndex=gp.cChecker.checkEntity(this, gp.monster);
	    	if(monsterIndex !=999) {
	    		gp.player.damageMonster(monsterIndex,attack);
	    		alive=false;
	    	}
	    }
	    if(user != gp.player) {
	    	Boolean contactPlayer=gp.cChecker.checkPlayer(this);
	    	if(gp.player.invincible==false && contactPlayer==true) {
	    		damagePlayer(attack);
	    		alive=false;
	    	}
	    }
		
		if(direction.equals("up")) {
	        worldY -= speed;
	    } else if(direction.equals("down")) {
	        worldY += speed;
	    } else if(direction.equals("left")) {
	        worldX -= speed;
	    } else if(direction.equals("right")) {
	        worldX += speed;
	    }

		
		life--;
		if(life <= 0) {
			alive=false;
		}
		
		spriteCounter++;
		if(spriteCounter>12) {
			if(spriteNum==1) {
				spriteNum=2;
			}
			else if(spriteNum==2) {
				spriteNum=1;
			}
			spriteCounter=0;
		}
	}
		public boolean haveResource(Entity user) {
			boolean haveResource=false;
			return haveResource;		
		}
		public void subtractResource(Entity user) {
		}
}