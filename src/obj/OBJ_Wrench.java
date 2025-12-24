package obj;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Wrench extends Entity {
public OBJ_Wrench(GamePanel gp) {
super(gp);	
type=type_wrench;
name="Wrench";
down1=setup("/objects/wrench",gp.tileSize,gp.tileSize);
attackValue=1;
attackArea.width=36;
attackArea.height=36;
description="["+name+"]/nA rusty wrench.";

}
}
