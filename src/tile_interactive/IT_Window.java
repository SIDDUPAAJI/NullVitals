package tile_interactive;

import Main.GamePanel;
import entity.Entity;

public class IT_Window extends InteractiveTile {

    public IT_Window(GamePanel gp, int col, int row) {
        super(gp, col, row);

        down1 = setup("/tiles_interactive/window", gp.tileSize, gp.tileSize);
        destructible = true;
        life=3;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        // Assuming entity.currentWeapon exists and type_axe is a constant
        return entity.Weapon != null && entity.Weapon.type == type_axe;
    }

    @Override
    public void playSE() {
        gp.playSE(12);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
    	InteractiveTile tile=new IT_WindowBroken(gp,worldX/gp.tileSize,worldY/gp.tileSize);
    	
        return tile;
    }
}
