package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Door extends Entity {

    GamePanel gp;
    private boolean isOpen = false;

    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Door";
        down1 = setup("/objects/door_1", gp.tileSize, gp.tileSize); // closed sprite
        collision = true;

        solidArea.x = 0;
        solidArea.y = 5;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact() {
        if (!isOpen) {
            // Check player inventory for a key
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                Entity item = gp.player.inventory.get(i);

                if (item instanceof OBJ_Key) {
                    if (item.amount > 1) {
                        item.amount--; // decrement stack
                    } else {
                        gp.player.inventory.remove(i); // remove last key
                    }

                    // Open door
                    open();
                    gp.ui.currentDialogue = "Door Opened!";
                    gp.playSE(1);
                    return; // stop after using one key
                }
            }

            // If no key found
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "A key might help me...";
        }
    }





    public void open() {
        isOpen = true;
        gp.playSE(3);

        // Remove collision
        collision = false;

        // Make invisible
        down1 = null; // or load a transparent/empty sprite
    }

}
