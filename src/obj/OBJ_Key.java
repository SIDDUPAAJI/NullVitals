package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity {

    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_key;
        name = "Key";
        down1 = setup("/objects/key_1", gp.tileSize, gp.tileSize);
        description = "[Key]\nOpens a door.";
        price=10;
        stackable = true;
    }

    @Override
    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != 999) {
            Entity door = gp.obj[gp.currentmap][objIndex];
            if (door instanceof OBJ_Door) {
                ((OBJ_Door) door).open(); // 👈 call door’s open method
                gp.ui.currentDialogue = "The door creaks open...";
                gp.playSE(1);
                return true; // key used successfully
            }
        }

        gp.ui.currentDialogue = "No door nearby...";
        return false;
    }
}
