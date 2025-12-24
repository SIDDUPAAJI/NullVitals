package obj;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Teddy extends Entity {

    private int useCount = 0; // Track how many times it’s been used

    public OBJ_Teddy(GamePanel gp) {
        super(gp);
        name = "Teddy";
        type = type_hug;
        stackable = true;
        instantUse = false;
        description = "["+name+"]/nRestores 3 Sanity./nDisappears after 3 uses.";
        price=8; 
        amount = 3;
        down1 = setup("/objects/teddy", gp.tileSize-10, gp.tileSize-10);
    }

    @Override
    public boolean use(Entity entity) {
        if (entity.sanity < entity.maxSanity) {
            entity.sanity += 3;
            if (entity.sanity > entity.maxSanity) {
                entity.sanity = entity.maxSanity;
            }
            gp.playSE(3);
        } else {
        	gp.ui.showMessage("Sanity is full!");
            
        }

        amount--;
        if (amount <= 0) {
            gp.player.inventory.remove(this);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "The teddy is worn out \nand disappears.";
            
        }
   return true; }
}