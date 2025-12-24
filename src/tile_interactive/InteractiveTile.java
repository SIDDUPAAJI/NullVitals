package tile_interactive;

import Main.GamePanel;
import entity.Entity;

public class InteractiveTile extends Entity {

    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        // Default solid area
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void playSE() {
        // Play sound effect
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile= null;
        return tile;
    }

    public void update() {
        // Logic for interactive tile update
    	if(invincible==true) {
    		invincibleCounter++;
    		if(invincibleCounter>30) {
    			invincible=false;
    			invincibleCounter=0;
    		}
    	}
    }
}
