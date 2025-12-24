package obj;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Gun extends Entity {

    public OBJ_Gun(GamePanel gp) {
        super(gp);

        name = "Gun";
        type = type_gun; // Important so pickUpObject() detects it
        down1 = setup("/objects/gun", gp.tileSize, gp.tileSize); // your sprite path
        description = "["+name+"]\nA basic firearm.\nRefills ammo when picked up.";
        stackable = false; // Guns aren't stackable
    }
}
