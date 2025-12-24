package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.GamePanel;
import Main.KeyHandler;
import obj.OBJ_Armour;
import obj.OBJ_Fireball;
import obj.OBJ_Wrench;

/**
 * Player entity class (cleaned & fixed).
 * - Ensures projectile is initialized safely.
 * - Creates a fresh projectile instance on shoot (multiple shots allowed).
 * - Removed accidental reassignment of projectile in setDefaultValues.
 */
public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean touchedPoisonTile = false;
    public boolean onPoisonTile;
    public boolean lightUpdated = false;

    // Invincibility after getting hit
    public boolean invincible = false;
    public int invincibleCounter = 0;

    // shot cooldown counter (already used in your update logic)
    // int shotAvailableCounter; // assumed declared in Entity

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Set defaults (do NOT overwrite projectile inside setDefaultValues)
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();

        // Initialize projectile template (use a concrete projectile type as default)
        // This ensures projectile is never null when update() runs.
        this.projectile = new OBJ_Fireball(gp);
        this.projectile.alive = false;
    }

    public void setDefaultValues() {
        // Position
        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 15;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // Player Status
        level = 1;
        maxLife = 6;
        life = maxLife; // Ensures life starts at full
        strength = 1; // More strength = more damage dealt
        sanity = 1;
        maxAmmo = 3;
        ammo = maxAmmo;
        energy = 1; // More sanity = less damage taken
        exp = 0;
        nextLevelExp = 10;
        cigs = 0;

        // Equipment
        Weapon = new OBJ_Wrench(gp);
        Armour = new OBJ_Armour(gp);

        // NOTE: do NOT set projectile here. We set it in the constructor,
        // so we don't accidentally overwrite it or introduce nulls.

        // Derived Stats
        attack = getAttack(); // Based on strength + weapon
        defence = getDefence(); // Based on sanity + armour
    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 15;
        direction = "down";
    }

    public void restoreLifeAndAmmo() {
        life = maxLife;
        ammo = maxAmmo;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(Weapon);
        inventory.add(Armour);
    }

    public int getAttack() {
        attackArea = Weapon.attackArea;
        return attack = strength * Weapon.attackValue;
    }

    public int getDefence() {
        return defence = sanity * Armour.defenceValue;
    }

    public void getPlayerImage() {
        up1 = setup("/player/boy_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/boy_attack_up1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/boy_attack_up2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/boy_attack_down1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/boy_attack_down2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/boy_attack_left1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/boy_attack_left2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/player/boy_attack_right1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/boy_attack_right2", gp.tileSize * 2, gp.tileSize);
    }

    public void update() {

        if (attacking) {
            attacking();
            return; // Stop further update logic while attacking
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        // SHOOTING: guard against null projectile template and create a fresh instance for the shot
     // SHOOTING: always spawn a fresh projectile instance if key is pressed and cooldown ready
        if (gp.keyH.shootKeyPressed && shotAvailableCounter >= 30 && projectile != null) {

            Projectile newP;

            // Use the template type to decide what to spawn
            if (projectile instanceof OBJ_Fireball) {
                newP = new OBJ_Fireball(gp);
            } else {
                newP = new Projectile(gp); // fallback or default type
            }

            // Check if player has enough resources to fire
            if (newP.haveResource(this)) {
                // Initialize projectile’s position, direction, owner
                newP.set(worldX, worldY, direction, true, this);

                // Reset cooldown
                shotAvailableCounter = 0;

                // Subtract mana/arrows/whatever resource
                newP.subtractResource(this);

                // Add new projectile to the world
                if (gp.projectileList == null) {
                    gp.projectileList = new ArrayList<>();
                }
                gp.projectileList.add(newP);

                gp.playSE(10); // play sound
            }
        }
    
        

        // Invincibility handling
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic(0);
            gp.playSE(13);
            return; // Stop further updates after game over
        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // Save the current worldX,worldY,SolidArea;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // check monster collision with the updated worldX,worldY and solidarea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);
            // AFTER CHECKING COLLISION RESTORE ORIGINAL DATA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {

        if (i != 999 && i >= 0 && gp.obj != null && gp.obj[gp.currentmap] != null && i < gp.obj[gp.currentmap].length && gp.obj[gp.currentmap][i] != null) {
            Entity obj = gp.obj[gp.currentmap][i];

            // Check if the object is the Vaccine and trigger ending scene
            if (obj instanceof obj.OBJ_Vaccine) {
                obj.use(this);
                gp.obj[gp.currentmap][i] = null; // Remove vaccine from the world
                gp.playSE(6); // Play pickup sound

                // Trigger ending scene with dialogues
                gp.gameState = gp.endingState;
                gp.ui.startEndingDialogue();
                return; // Skip other pickup processing
            }

            if (obj.type == type_pickupOnly) {
                obj.use(this);
                gp.obj[gp.currentmap][i] = null;
                gp.playSE(6);
            } else if (obj.type == type_gun) {
                if (ammo < maxAmmo) {
                    ammo = maxAmmo;
                    gp.playSE(6);
                    gp.ui.showMessage("Got a " + obj.name + "!");
                    gp.obj[gp.currentmap][i] = null;
                } else {
                    gp.gameState = gp.dialogueState;
                    gp.ui.currentDialogue = "AMMO FULL!";
                }
                return;
            } else if (gp.obj[gp.currentmap][i].type == type_obstacle) {
                if (keyH.enterPressed) {
                    gp.obj[gp.currentmap][i].interact();
                }
            } else if (obj.instantUse) {
                if (life < maxLife) {
                    gp.playSE(3);
                    life += 5;
                    if (life > maxLife) life = maxLife;
                    gp.gameState = gp.dialogueState;
                    gp.ui.currentDialogue = "HP received";
                    gp.obj[gp.currentmap][i] = null;
                }
            } else {
                String text;
                if (canObtainItem(gp.obj[gp.currentmap][i])) {
                    gp.playSE(6);
                    text = "Got a " + obj.name + "!";
                    gp.obj[gp.currentmap][i] = null;
                } else {
                    text = "You cannot carry anymore!";
                }
                gp.ui.showMessage(text);
            }
        }
    }

    public void interactNPC(int i) {

        if (i != 999 && gp.npc != null && gp.npc[gp.currentmap] != null) {
            if (keyH.enterPressed) {
                gp.gameState = gp.dialogueState;

                //  Check if it's Dr. Cure specifically
                if (gp.npc[gp.currentmap][i] instanceof NPC_DrCure) {
                    gp.se.setFile(5);
                    gp.se.play();
                }

                gp.npc[gp.currentmap][i].speak();
            }
        } else {
            if (gp.keyH.enterPressed) {
                attacking = true;
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void contactMonster(int i) {
        if (i != 999 && gp.monster != null && gp.monster[gp.currentmap] != null) {
            if (!invincible && !gp.monster[gp.currentmap][i].dying) {
                gp.playSE(8);
                int damage = attack - gp.monster[gp.currentmap][i].attack - defence;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }

    }

    public void damageMonster(int monsterIndex, int attack) {
        if (monsterIndex != 999) {
            Entity monster = gp.monster[gp.currentmap][monsterIndex];
            if (!monster.invincible && monster.damageCooldown == 0) { // Only damage if not invincible AND cooldown is 0
                gp.playSE(9);
                knockback(monster); // knockback effect
                int damage = attack - monster.defence;
                if (damage < 0) damage = 0;
                monster.life -= damage;
                monster.invincible = true; // Set invincible flag
                monster.damageCooldown = 40; // Set cooldown (same as invincibility)
                monster.damageReaction();

                if (monster.life <= 0) {
                    monster.dying = true;
                    gp.playSE(11);
                    gp.ui.showMessage("Exp+" + monster.exp);
                    exp += monster.exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void knockback(Entity entity) {
        entity.direction = direction;
        entity.speed += 3;
        entity.knockback = true;
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile != null && gp.iTile[gp.currentmap] != null && gp.iTile[gp.currentmap][i].destructible && gp.iTile[gp.currentmap][i].isCorrectItem(this) && !gp.iTile[gp.currentmap][i].invincible) {
            gp.iTile[gp.currentmap][i].playSE();
            gp.iTile[gp.currentmap][i].life--;
            gp.iTile[gp.currentmap][i].invincible = true;
            if (gp.iTile[gp.currentmap][i].life == 0) {
                gp.iTile[gp.currentmap][i] = gp.iTile[gp.currentmap][i].getDestroyedForm();
            }
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            maxAmmo += 1;
            strength++;
            sanity++;
            attack = getAttack();
            defence = getDefence();
            gp.playSE(3);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "LEVEL UP!!";
        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerslotCol, gp.ui.playerslotRow);

        // safety checks
        if (itemIndex < 0 || itemIndex >= inventory.size()) {
            return;
        }

        Entity selectedItem = inventory.get(itemIndex);

        // Equip weapon (wrench/axe)
        if (selectedItem.type == type_wrench || selectedItem.type == type_axe) {
            Weapon = selectedItem;
            attack = getAttack();
            gp.ui.showMessage("Equipped " + selectedItem.name + "!");
            return;
        }

        // Equip armour
        if (selectedItem.type == type_armour) {
            Armour = selectedItem;
            defence = getDefence();
            gp.ui.showMessage("Equipped " + selectedItem.name + "!");
            return;
        }
        if (selectedItem.type == type_light) {
            if (Light == selectedItem) {
                Light = null;
            } else {
                Light = selectedItem;
            }
            lightUpdated = true;
        }

        // Use consumable / hug-type items
        if (selectedItem.type == type_consume || selectedItem.type == type_hug) {
            // call the item's use method — item should update its own amount (if stackable)
            if (selectedItem.use(this)) {
                if (selectedItem.amount > 1) {
                    selectedItem.amount--;
                } else {
                    inventory.remove(selectedItem);
                }

            }

            // If item is stackable, remove it only when amount <= 0
            if (selectedItem.stackable) {
                if (selectedItem.amount <= 0) {
                    // remove by reference (safer than by index)
                    inventory.remove(selectedItem);
                }
            } else {
                // Non-stackable consumables should be removed after use,
                // but check existence to avoid double-removal
                if (inventory.contains(selectedItem)) {
                    inventory.remove(selectedItem);
                }
            }

            // Clamp UI cursor so it doesn't point out of range
            if (inventory.size() == 0) {
                gp.ui.playerslotCol = 0;
                gp.ui.playerslotRow = 0;
            } else {
                int maxIndex = inventory.size() - 1;
                int newIndex = Math.min(itemIndex, maxIndex);
                gp.ui.playerslotCol = newIndex % 5;
                gp.ui.playerslotRow = newIndex / 5;
            }
        }
    }

    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item) {
        // If item is stackable
        if (item.stackable) {
            for (Entity invItem : inventory) {
                if (invItem.name.equals(item.name)) {
                    invItem.amount++;
                    return true; // just increase stack, no need for free slot
                }
            }
        }

        // Otherwise, need a free slot
        if (inventory.size() < maxInventorysize) {
            inventory.add(item);
            return true;
        }

        // Inventory full
        gp.ui.showMessage("Inventory Full!");
        return false;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        // ResetAlpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
