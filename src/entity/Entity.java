package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.UtilityTool;
import obj.OBJ_Wrench;

public class Entity {
    protected GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage down1, down2, left1, left2, right1, right2, up1, up2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 44, 44);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[2000];
    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;

    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean hpBarOn = false;
    public boolean dying = false;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    public boolean onPath = false;
    public boolean knockback = false;
    protected int knockbackCounter = 0;
    public int damageCooldown = 0;

    // character status
    public int maxLife;
    public int defaultSpeed;
    public int life;
    public int maxAmmo;
    public int ammo;
    public boolean alive = true;
    public int strength;
    public int energy;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int level;
    public int cigs;
    public Entity Weapon;
    public Entity Armour;
    public Entity Light;
    public Projectile projectile;
    public boolean instantUse = false;
    public boolean boss; // Default false for normal inventory items

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorysize = 50;
    public int value;
    public int attackValue;
    public int defenceValue;
    public String description = "";
    public int useCost;
    public int price;
    public int lightRadius;
    // TYPE
    public int type; // denote 0=player,1=npc,2=monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_wrench = 3;
    public final int type_axe = 4;
    public final int type_armour = 5;
    public final int type_consume = 6;
    public final int type_hug = 7;
    public final int type_gun = 8;
    public final int type_pickupOnly = 9;
    public final int type_obstacle = 10;
    public static final int type_key = 11; // or next free ID
    public final int type_light = 12;
    public boolean stackable = false;

    public int amount = 1;
    public int maxSanity = 30; // or whatever limit you want
    public int sanity = 3; // starting sanity

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public int getScreenX() {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }

    public int getScreenY() {
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }

    public int getLeftX() {
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY() {
        return worldY + solidArea.y;
    }

    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow() {
        return (worldY - solidArea.y) / gp.tileSize;
    }

    public void setAction() {

    }

    public void damageReaction() {

    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction) {
            case "up":
                direction = "down";
            case "down":
                direction = "up";
            case "left":
                direction = "right";
            case "right":
                direction = "left";
                break;
        }
    }

    public void interact() {

    }

    public boolean use(Entity entity) {
        return false;

    }

    public void checkDrop() {

    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentmap][i] == null) {
                gp.obj[gp.currentmap][i] = droppedItem;
                gp.obj[gp.currentmap][i].worldX = worldX;// DEFEATED ENEMY"S WORLDX
                gp.obj[gp.currentmap][i].worldY = worldY;// DEFEATED ENEMY"S WORLDY
                break;
            }
        }
    }

    public void checkCollison() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
    }

    // New method to update counters/timers even when full update is skipped
    public void updateTimers() {
        // Handle invincibility timer
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 10) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        // Handle knockback timer (for completeness, add if needed)
        if (knockback) {
            knockbackCounter++;
            if (knockbackCounter >= 10) {
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }
        }
        // Update shot availability timer
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void update() {
        if (knockback) {
            checkCollison();

            if (collisionOn) {
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            } else {
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

            knockbackCounter++;
            if (knockbackCounter >= 10) {
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }
        } else {
            setAction();
            checkCollison();

            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);

            if (!collisionOn) {
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
        }

        // Handle damage cooldown timer
        if (damageCooldown > 0) {
            damageCooldown--;
        }

        // Handle invincibility timer 
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {  // Adjust this duration as needed
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Handle sprite animation
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        spriteCounter++;
        if (spriteCounter > 24) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        // Other counters (e.g., shot availability)
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }


    public void damagePlayer(int damage) {
        if (!gp.player.invincible) {
            gp.player.life -= damage; // subtract actual monster attack power
            if (gp.player.life < 0) {
                gp.player.life = 0;
            }
            gp.player.invincible = true;
            gp.playSE(6); // optional hurt sound
        }
    }

    public boolean inCamera() {
        boolean inCamera = false;
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (inCamera() == true) {
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            if (dying == true) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i) {
            alive = false;
        }

    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pfinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (gp.pfinder.search()) {
            // Get the next step in the path (use 1 if available, else 0)
            int nextIndex = 0;
            if (gp.pfinder.pathList.size() > 1) {
                nextIndex = 1;
            }

            int nextX = gp.pfinder.pathList.get(nextIndex).col * gp.tileSize;
            int nextY = gp.pfinder.pathList.get(nextIndex).row * gp.tileSize;

            // Current entity solid area
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            // Movement decisions
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY <= nextY + gp.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                } else if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // Up or Left
                direction = "up";
                checkCollison();
                if (collision) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // Up or Right
                direction = "up";
                checkCollison();
                if (collision) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // Down or Left
                direction = "down";
                checkCollison();
                if (collision) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // Down or Right
                direction = "down";
                checkCollison();
                if (collision) {
                    direction = "right";
                }
            }

            // Stop path if goal reached
            int nextCol = gp.pfinder.pathList.get(nextIndex).col;
            int nextRow = gp.pfinder.pathList.get(nextIndex).row;
            if (nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }
        }
    }


    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = 999;
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY() - 1;
                break;
            case "down":
                nextWorldY = user.getBottomY() + 1;
                break;
            case "left":
                nextWorldX = user.getLeftX() - 1;
                break;
            case "right":
                nextWorldX = user.getRightX() + 1;
                break;
        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for (int i = 0; i < target[i].length; i++) {
            if (target[gp.currentmap][i] != null) {
                // Fix: removed accidental semicolon from if-condition
                if (target[gp.currentmap][i].getCol() == col && target[gp.currentmap][i].getRow() == row
                        && target[gp.currentmap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}