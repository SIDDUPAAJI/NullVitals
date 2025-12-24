package monster;

import java.util.ArrayList;
import java.util.Random;

import Main.GamePanel;
import entity.Entity;
import obj.OBJ_Axe;
import obj.OBJ_Cigs;
import obj.OBJ_Gun;
import obj.OBJ_Pills;
import obj.OBJ_Puke;
import obj.OBJ_Vaccine;

public class MON_ParasiteBeast extends Entity {
    GamePanel gp;
    Random random = new Random();
    private int phase = 1;  // Track current phase
    private int spawnCounter = 0;
    private final int spawnInterval = 300; // frames delay between spawns
    private boolean tankSpawned = false;  // Ensure Tank spawns exactly once
    private ArrayList<Entity> spawnedMinions = new ArrayList<>(); // Track spawned minions

    public MON_ParasiteBeast(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        boss = true;
        name = "ParasiteBeast";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 1000;
        life = maxLife;
        attack = 7;
        defence = 3;
        exp = 40;
        projectile = new OBJ_Puke(gp);

        int spriteSize = gp.tileSize * 4;

        solidArea.x = 12;
        solidArea.y = spriteSize - 120;
        solidArea.width = spriteSize - 72;
        solidArea.height = 36;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = spriteSize;
        attackArea.height = spriteSize;

        getImage();
    }

    public void getImage() {
        int size = gp.tileSize * 2;

        up1 = setup("/monster/parasitebeast_up", size, size);
        up2 = setup("/monster/parasitebeast_up", size, size);
        down1 = setup("/monster/parasitebeast_down", size, size);
        down2 = setup("/monster/parasitebeast_down", size, size);
        left1 = setup("/monster/parasitebeast_left", size, size);
        left2 = setup("/monster/parasitebeast_left", size, size);
        right1 = setup("/monster/parasitebeast_right", size, size);
        right2 = setup("/monster/parasitebeast_right", size, size);
    }

    public void setAction() {
        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);

            int i = random.nextInt(100) + 1;
            if (i > 50 && projectile.alive == false && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);
                gp.projectileList.add(projectile);
                shotAvailableCounter = 0;
            } else {
                actionLockCounter++;
                if (actionLockCounter >= 90) {
                    i = random.nextInt(100);
                    if (i < 25) {
                        direction = "up";
                    } else if (i < 50) {
                        direction = "down";
                    } else if (i < 75) {
                        direction = "left";
                    } else {
                        direction = "right";
                    }
                    actionLockCounter = 0;
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 9) {
            onPath = true;
        }
        if (onPath && tileDistance > 12) {
            onPath = false;
        }

        // Phase change at 50% life
        if (phase == 1 && life <= maxLife / 2) {
            phase = 2;
            gp.ui.showMessage("The Parasite Beast enters Phase 2!");
        }

        // Handle spawning logic
        spawnCounter++;
        if (spawnCounter >= spawnInterval) {
            spawnCounter = 0;
            spawnMinion();
        }

        // Check death and handle
        checkDead();
    }

    private void spawnMinion() {
        int spawnX = worldX + (random.nextInt(gp.tileSize * 4) - gp.tileSize * 2);
        int spawnY = worldY + (random.nextInt(gp.tileSize * 4) - gp.tileSize * 2);

        if (phase == 1) {
            int choice = random.nextInt(2);
            switch (choice) {
                case 0:
                    spawnEntity("BloodSlug", spawnX, spawnY);
                    gp.ui.showMessage("Parasite Beast spawned a BloodSlug!");
                    break;
                case 1:
                    spawnEntity("Spitter", spawnX, spawnY);
                    gp.ui.showMessage("Parasite Beast spawned a Spitter!");
                    break;
            }
        } else if (phase == 2) {
            if (!tankSpawned) {
                spawnEntity("Tank", spawnX, spawnY);
                gp.ui.showMessage("Parasite Beast spawned an Abomination!");
                tankSpawned = true;
            } else {
                spawnEntity("Sprinter", spawnX, spawnY);
                gp.ui.showMessage("Parasite Beast spawned a Sprinter!");
            }
        }
        
    }

    private void spawnEntity(String monsterName, int x, int y) {
        for (int i = 0; i < gp.monster[gp.currentmap].length; i++) {
            if (gp.monster[gp.currentmap][i] == null) {
                Entity newMonster = gp.aSetter.createMonsterByName(monsterName);
                if (newMonster != null) {
                    newMonster.worldX = x;
                    newMonster.worldY = y;
                    gp.monster[gp.currentmap][i] = newMonster;
                    spawnedMinions.add(newMonster); // track minions
                }
                break;
            }
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        if (gp.player.worldX < worldX) direction = "left";
        else if (gp.player.worldX > worldX) direction = "right";
        else if (gp.player.worldY < worldY) direction = "up";
        else if (gp.player.worldY > worldY) direction = "down";
    }

    public void checkDrop() {
        int i = random.nextInt(100) + 1;

        if (i < 10) {
            dropItem(new OBJ_Cigs(gp));
            dropItem(new OBJ_Pills(gp));
        } else if (i < 40) {
            dropItem(new OBJ_Pills(gp));
            dropItem(new OBJ_Gun(gp));
        } else if (i < 90) {
            dropItem(new OBJ_Gun(gp));
            dropItem(new OBJ_Cigs(gp));
        } else if (i < 100) {
            dropItem(new OBJ_Axe(gp));
            dropItem(new OBJ_Pills(gp));
            dropItem(new OBJ_Gun(gp));
        }
        dropItem(new OBJ_Vaccine(gp));
        System.out.println("Boss defeated - setting dialogue");
        gp.ui.currentDialogue = "BOSS DEFEATED!";
        gp.gameState = gp.dialogueState;
    }

    public void checkDead() {
        if (life <= 0) {
            // Kill all spawned minions when this boss dies
            killSpawnedMinions();

            // Remove this boss from monster array to prevent respawn
            for (int i = 0; i < gp.monster[gp.currentmap].length; i++) {
                if (gp.monster[gp.currentmap][i] == this) {
                    gp.monster[gp.currentmap][i] = null;
                    break;
                }
            }
            // Additional death actions can go here
        }
    }

    private void killSpawnedMinions() {
        System.out.println("Killing spawned minions: " + spawnedMinions.size());
        for (Entity minion : spawnedMinions) {
            if (minion != null) {
                System.out.println("Setting minion life to 0: " + minion.name);
                minion.life = 0;
                // Optionally set alive = false or call die() here if needed
            }
        }
        spawnedMinions.clear();
    }

}
