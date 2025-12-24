package Main;

import entity.Entity;
import entity.NPC_DrCure;
import entity.NPC_Janitor;
import monster.MON_BloodSlug;
import monster.MON_ParasiteBeast;
import monster.MON_Spitter;
import monster.MON_Sprinter;
import monster.MON_Tank;
import obj.OBJ_Armour;
import obj.OBJ_Axe;
import obj.OBJ_Boost;
import obj.OBJ_Chest;
import obj.OBJ_Cigs;
import obj.OBJ_Corpse;
import obj.OBJ_Door;
import obj.OBJ_Gun;
import obj.OBJ_Key;
import obj.OBJ_Pills;
import obj.OBJ_Teddy;
import obj.OBJ_Torch;
import obj.OBJ_Wrench;
import obj.OBJ_ivbag;
import tile_interactive.IT_Window;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
    	int mapNum=0;
    	int i=0;
    	gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 7;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 11;
        i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 10;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 38;
	    i++; 
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 6;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 45;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 19;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 40;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 44;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 44;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 19;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 21;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Key(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 40;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 23;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Key(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 7;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 21;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Key(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 38;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 6;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Key(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 32;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 5;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Key(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 27;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 27;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Axe(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 8;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 39;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 15;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 16;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 4;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 5;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 7;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 32;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 45;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 21;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 21;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Teddy(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 39;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 10;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Teddy(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 29;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 23;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 36;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 20;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 40;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 35;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 35;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 38;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 21;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 22;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Cigs(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 23;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 25;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Cigs(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 25;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 6;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Cigs(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 27;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 20;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Cigs(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 19;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 27;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Door(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 10;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Door(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 44;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 21;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Door(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 15;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 18;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Torch(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 20;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 15;
	    
	    mapNum=2;
	    
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 6;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 14;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 13;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 14;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 20;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 4;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 15;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 19;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 11;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 33;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 6;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 48;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 1;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 44;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 7;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 38;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 2;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 35;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 8;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 33;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 2;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 37;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 4;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 18;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 1;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 27;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 1;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 44;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 15;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 6;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 4;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 9;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 1;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 15;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 7;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 13;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 43;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 13;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 46;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 17;
	    
	    mapNum=3;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 19;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 35;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 28;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 31;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 10;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 41;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 37;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 5;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 42;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_ivbag(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 43;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 15;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 24;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 35;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 13;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 31;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 20;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 41;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 33;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 5;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 42;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 17;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 21;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 41;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 34;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 5;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 46;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 34;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 38;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 17;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 20;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 41;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 33;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 5;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Pills(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 42;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 17;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 20;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 41;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 33;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 5;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 41;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 30;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 12;
	    i++;
	    gp.obj[mapNum][i] = new OBJ_Gun(gp);
	    gp.obj[mapNum][i].worldX = gp.tileSize * 42;
	    gp.obj[mapNum][i].worldY = gp.tileSize * 17;
	    
	    
    }
    
    public void setNPC() {
    	int mapNum=0;
    	int i=0;
    	gp.npc[mapNum][i]=new NPC_DrCure(gp);
    	gp.npc[mapNum][i].worldX=gp.tileSize*23;
    	gp.npc[mapNum][i].worldY=gp.tileSize*23;
    	i++;
    	mapNum=1;
    	gp.npc[mapNum][i]=new NPC_Janitor(gp);
    	gp.npc[mapNum][i].worldX=gp.tileSize*20;
    	gp.npc[mapNum][i].worldY=gp.tileSize*22;
    	i++;
    	
    }
    public void setMonster() {
    	int mapNum=0;
    	int i=0;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *25;
    	gp.monster[mapNum][i].worldY=gp.tileSize *5;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *23;
    	gp.monster[mapNum][i].worldY=gp.tileSize *5;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *7;
    	gp.monster[mapNum][i].worldY=gp.tileSize *36;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *7;
    	gp.monster[mapNum][i].worldY=gp.tileSize *33;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *7;
    	gp.monster[mapNum][i].worldY=gp.tileSize *34;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *26;
    	gp.monster[mapNum][i].worldY=gp.tileSize *5;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *41;
    	gp.monster[mapNum][i].worldY=gp.tileSize *5;
    	i++;
    	gp.monster[mapNum][i]=new MON_BloodSlug(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *33;
    	gp.monster[mapNum][i].worldY=gp.tileSize *5;
    	i++;
    	gp.monster[mapNum][i]=new MON_Spitter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *41;
    	gp.monster[mapNum][i].worldY=gp.tileSize *34;
    	i++;
    	gp.monster[mapNum][i]=new MON_Spitter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *38;
    	gp.monster[mapNum][i].worldY=gp.tileSize *34;
    	i++;
    	gp.monster[mapNum][i]=new MON_Spitter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *35;
    	gp.monster[mapNum][i].worldY=gp.tileSize *32;
    	i++;
    	gp.monster[mapNum][i]=new MON_Spitter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *31;
    	gp.monster[mapNum][i].worldY=gp.tileSize *32;
    	i++;
    	gp.monster[mapNum][i]=new MON_Spitter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *36;
    	gp.monster[mapNum][i].worldY=gp.tileSize *34;
    	i++;
    	gp.monster[mapNum][i]=new MON_Sprinter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *11;
    	gp.monster[mapNum][i].worldY=gp.tileSize *32;
    	i++;
    	gp.monster[mapNum][i]=new MON_Sprinter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *10;
    	gp.monster[mapNum][i].worldY=gp.tileSize *43;
    	i++;
    	gp.monster[mapNum][i]=new MON_Sprinter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *8;
    	gp.monster[mapNum][i].worldY=gp.tileSize *42;
    	
    	 mapNum=2;
    	 i++;
     	gp.monster[mapNum][i]=new MON_Sprinter(gp);
     	gp.monster[mapNum][i].worldX=gp.tileSize *40;
     	gp.monster[mapNum][i].worldY=gp.tileSize *13;
     	i++;
    	gp.monster[mapNum][i]=new MON_Sprinter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *44;
    	gp.monster[mapNum][i].worldY=gp.tileSize *16;
    	i++;
    	gp.monster[mapNum][i]=new MON_Sprinter(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *46;
    	gp.monster[mapNum][i].worldY=gp.tileSize *17;
    	i++;
    	gp.monster[mapNum][i]=new MON_Tank(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *10;
    	gp.monster[mapNum][i].worldY=gp.tileSize *13;
    	i++;
    	gp.monster[mapNum][i]=new MON_Tank(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *14;
    	gp.monster[mapNum][i].worldY=gp.tileSize *13;
    	
    	mapNum=3;
    	
    	i++;
    	gp.monster[mapNum][i]=new MON_ParasiteBeast(gp);
    	gp.monster[mapNum][i].worldX=gp.tileSize *15;
    	gp.monster[mapNum][i].worldY=gp.tileSize *32;
    	
    	
    }
    public void setInteractiveTile() {
    	int mapNum=0;
    	int i=0;
    	gp.iTile[mapNum][i]=new IT_Window(gp,31,24);i++;
    	gp.iTile[mapNum][i]=new IT_Window(gp,33,38);i++;
    	gp.iTile[mapNum][i]=new IT_Window(gp,41,38);i++;
    	gp.iTile[mapNum][i]=new IT_Window(gp,5,43);i++;
    	gp.iTile[mapNum][i]=new IT_Window(gp,5,42);i++;
    }
    public Entity createItemByName(String itemName) {
        switch (itemName) {
            case "Wrench":
                return new OBJ_Wrench(gp);
            case "Armour":
                return new OBJ_Armour(gp);
            case "Key":
                return new OBJ_Key(gp);
            case "pills":
                return new OBJ_Pills(gp);
            case "Fireaxe":
                return new OBJ_Axe(gp);
            case "Teddy":
                return new OBJ_Teddy(gp);
            case "torch":
                return new OBJ_Torch(gp);
            // Add more cases for every unique inventory item by name
           
            default:
                return null; // Or throw if unknown item
        }
    }
    public Entity createMonsterByName(String name) {
        switch(name) {
            case "BloodSlug":
                return new MON_BloodSlug(gp);
            case "Spitter":
                return new MON_Spitter(gp);
            case "Tank":
                return new MON_Tank(gp);
            case "Sprinter":
                return new MON_Sprinter(gp);
            case "ParasiteBeast":
                return new MON_ParasiteBeast(gp);
            // Add more monsters here as needed
            default:
                System.out.println("Unknown monster requested: " + name);
                return null;
        }
    }

}
