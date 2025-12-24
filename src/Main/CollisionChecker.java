package Main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftX = entity.worldX + entity.solidArea.x;
        int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.worldY + entity.solidArea.y;
        int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX / gp.tileSize;
        int entityRightCol = entityRightX / gp.tileSize;
        int entityTopRow = entityTopY / gp.tileSize;
        int entityBottomRow = entityBottomY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityTopRow];
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityBottomRow];
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityBottomRow];
                break;
            case "right":
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityBottomRow];
                break;
            default:
                return;
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999; // no object hit

        for (int i = 0; i < gp.obj[gp.currentmap].length; i++) {

            // Skip if object is null
            if (gp.obj[gp.currentmap][i] == null) continue;

            // Store original positions
            int entityAreaX = entity.solidArea.x;
            int entityAreaY = entity.solidArea.y;
            int objectAreaX = gp.obj[gp.currentmap][i].solidArea.x;
            int objectAreaY = gp.obj[gp.currentmap][i].solidArea.y;

            // Update positions for collision check
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY + entity.solidArea.y;

            gp.obj[gp.currentmap][i].solidArea.x = gp.obj[gp.currentmap][i].worldX + gp.obj[gp.currentmap][i].solidArea.x;
            gp.obj[gp.currentmap][i].solidArea.y = gp.obj[gp.currentmap][i].worldY + gp.obj[gp.currentmap][i].solidArea.y;

            // Move based on direction
            switch (entity.direction) {
                case "up":    entity.solidArea.y -= entity.speed; break;
                case "down":  entity.solidArea.y += entity.speed; break;
                case "left":  entity.solidArea.x -= entity.speed; break;
                case "right": entity.solidArea.x += entity.speed; break;
            }

            // Collision check
            if (entity.solidArea.intersects(gp.obj[gp.currentmap][i].solidArea)) {
                if (gp.obj[gp.currentmap][i].collision) {
                    entity.collisionOn = true;
                }
                if (player) {
                    index = i;
                }
            }

            // Reset positions
            entity.solidArea.x = entityAreaX;
            entity.solidArea.y = entityAreaY;
            gp.obj[gp.currentmap][i].solidArea.x = objectAreaX;
            gp.obj[gp.currentmap][i].solidArea.y = objectAreaY;
        }

        return index;
    }


    public int checkEntity(Entity entity, Entity[] [] target) {
        int index = 999;

        for (int i = 0; i < target[1].length; i++) {
            
            if (target[gp.currentmap][i] != null ) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                target[gp.currentmap][i].solidArea.x = target[gp.currentmap][i].worldX + target[gp.currentmap][i].solidArea.x;
                target[gp.currentmap][i].solidArea.y = target[gp.currentmap][i].worldY + target[gp.currentmap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target[gp.currentmap][i].solidArea)) {
                	if(target[gp.currentmap][i]!=entity) {
                    entity.collisionOn = true;
                    index = i;
                }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentmap][i].solidArea.x = target[gp.currentmap][i].solidAreaDefaultX;
                target[gp.currentmap][i].solidArea.y = target[gp.currentmap][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contact = false;

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }

        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contact = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contact;
    }
}
