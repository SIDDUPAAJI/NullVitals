package Main;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    boolean canTouchEvent = true;
    int previousEventX, previousEventY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        for (int map = 0; map < gp.maxMap; map++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    eventRect[map][col][row] = new EventRect();
                    eventRect[map][col][row].x = 23;
                    eventRect[map][col][row].y = 23;
                    eventRect[map][col][row].width = 2;
                    eventRect[map][col][row].height = 2;
                    eventRect[map][col][row].eventRectDefaultX = 23;
                    eventRect[map][col][row].eventRectDefaultY = 23;
                }
            }
        }
    }


    public void checkEvent() {
        int tileX = gp.player.worldX / gp.tileSize;
        int tileY = gp.player.worldY / gp.tileSize;

        // --- Poison Tiles ---
        if (tileX >= 0 && tileX < gp.maxWorldCol && tileY >= 0 && tileY < gp.maxWorldRow) {
            int tileNum = gp.tileM.mapTileNum[gp.currentmap][tileX][tileY];
            if (tileNum >= 13 && tileNum <= 17) {
                if (!gp.player.touchedPoisonTile) {
                    damagePit(gp.dialogueState);
                    gp.player.touchedPoisonTile = true;
                }
            } else {
                gp.player.touchedPoisonTile = false;
            }
        }

        // --- Event Cooldown ---
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            // --- Teleports ---
            if (hit(0, 44, 20, "any")) teleport(1, 20, 26);
            else if (hit(1, 20, 26, "any")) teleport(0, 44, 20);
            else if (hit(0, 47, 27, "any")) teleport(2, 26, 7);
            else if (hit(2, 26, 7, "any")) teleport(0, 47, 27);
            else if (hit(0, 4, 43, "any")) teleport(3, 23, 36);
            else if (hit(3, 23, 36, "any")) teleport(0, 4, 43);        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        int playerAreaX = gp.player.solidArea.x;
        int playerAreaY = gp.player.solidArea.y;
        int rectX = eventRect[map][col][row].x;
        int rectY = eventRect[map][col][row].y;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
        eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

        if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
            if (reqDirection.equals("any") || gp.player.direction.equals(reqDirection)) {
                hit = true;
            }
        }

        gp.player.solidArea.x = playerAreaX;
        gp.player.solidArea.y = playerAreaY;
        eventRect[map][col][row].x = rectX;
        eventRect[map][col][row].y = rectY;

        return hit;
    }

    public void damagePit(int gameState) {
        if (!gp.player.invincible) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "The blood is poisonous";
            gp.player.life -= 1;

            if (gp.player.life < 0) {
                gp.player.life = 0;
            }

            gp.player.invincible = true; // prevent immediate re-damage
        }
    }

    public void teleport(int map, int col, int row) {
        gp.currentmap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
    }
}
