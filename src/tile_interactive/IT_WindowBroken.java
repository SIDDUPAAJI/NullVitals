package tile_interactive;

import Main.GamePanel;

public class IT_WindowBroken extends InteractiveTile {

    public IT_WindowBroken(GamePanel gp, int col, int row) {
        super(gp, col, row);

        down1 = setup("/tiles_interactive/window_broken", gp.tileSize, gp.tileSize);

        // Remove collision
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
