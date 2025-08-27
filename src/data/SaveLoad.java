package data;

import java.io.*;
import java.util.ArrayList;
import Main.GamePanel;
import entity.Entity;
import entity.Projectile;

public class SaveLoad {
    GamePanel gp;
    private final File saveFile;
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
        // Save inside the res/ folder
        String path = System.getProperty("user.home") + "/save.dat";
        saveFile = new File(path);
    }


    // Save player stats, inventory, equipped items, and position
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            DataStorage ds = new DataStorage();

            // Save player stats
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxAmmo = gp.player.maxAmmo;
            ds.ammo = gp.player.ammo;
            ds.strength = gp.player.strength;
            ds.sanity = gp.player.sanity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.cigs = gp.player.cigs;

            // Save equipped item names (nullable checks)
            ds.equippedWeapon = gp.player.Weapon != null ? gp.player.Weapon.name : null;
            ds.equippedArmour = gp.player.Armour != null ? gp.player.Armour.name : null;
            ds.equippedLight = gp.player.Light != null ? gp.player.Light.name : null;
            ds.equippedProjectile = gp.player.projectile != null ? gp.player.projectile.name : null;

            // Save player position and direction
            ds.worldX = gp.player.worldX;
            ds.worldY = gp.player.worldY;
            ds.direction = gp.player.direction;

            // Save inventory as parallel lists
            ds.itemNames = new ArrayList<>();
            ds.itemAmounts = new ArrayList<>();
            for (Entity item : gp.player.inventory) {
                ds.itemNames.add(item.name);
                ds.itemAmounts.add(item.amount);
            }

            oos.writeObject(ds);
            oos.flush();

            System.out.println("Game saved successfully! File: " + saveFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Save Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load player stats, inventory, equipped items, and position
    public void load() {
        if (!saveFile.exists()) {
            System.out.println("No save file found. Loading default player data...");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            DataStorage ds = (DataStorage) ois.readObject();

            // Detect if old save: inventory lists are null or missing
            if (ds.itemNames == null || ds.itemAmounts == null) {
                System.out.println("Old/incompatible save detected, deleting save file.");
                deleteSaveFile();
                return;
            }

            // Load player stats
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxAmmo = ds.maxAmmo;
            gp.player.ammo = ds.ammo;
            gp.player.strength = ds.strength;
            gp.player.sanity = ds.sanity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.cigs = ds.cigs;

            // Clear and reload inventory
            gp.player.inventory.clear();

            for (int i = 0; i < ds.itemNames.size(); i++) {
                String itemName = ds.itemNames.get(i);
                int amount = ds.itemAmounts.get(i);

                Entity newItem = gp.aSetter.createItemByName(itemName);
                if (newItem != null) {
                    newItem.amount = amount;
                    gp.player.inventory.add(newItem);
                }
            }

            // Restore equipped items by recreating from saved names
            if (ds.equippedWeapon != null) {
                gp.player.Weapon = gp.aSetter.createItemByName(ds.equippedWeapon);
            }
            if (ds.equippedArmour != null) {
                gp.player.Armour = gp.aSetter.createItemByName(ds.equippedArmour);
            }
            if (ds.equippedLight != null) {
                gp.player.Light = gp.aSetter.createItemByName(ds.equippedLight);
            }
            if (ds.equippedProjectile != null) {
                gp.player.projectile = (Projectile) gp.aSetter.createItemByName(ds.equippedProjectile);
            }

            // Restore player position and direction
            gp.player.worldX = ds.worldX;
            gp.player.worldY = ds.worldY;
            gp.player.direction = ds.direction != null ? ds.direction : "down";

            System.out.println("Game loaded successfully! File: " + saveFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Load Exception: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Loading default player data instead...");
        }
    }

    public void deleteSaveFile() {
        if (saveFile.exists()) {
            boolean deleted = saveFile.delete();
            if (deleted) {
                System.out.println("Old save file deleted: " + saveFile.getAbsolutePath());
            } else {
                System.out.println("Failed to delete old save file: " + saveFile.getAbsolutePath());
            }
        } else {
            System.out.println("Save file does not exist, nothing to delete.");
        }
    }

    // Serializable container that holds saved data
    public static class DataStorage implements Serializable {
        private static final long serialVersionUID = 1L;

        // Player position and facing direction
        public int worldX;
        public int worldY;
        public String direction;

        // Equipped item names
        public String equippedWeapon;
        public String equippedArmour;
        public String equippedLight;
        public String equippedProjectile;

        // Player stats
        public int level;
        public int maxLife;
        public int life;
        public int maxAmmo;
        public int ammo;
        public int strength;
        public int sanity;
        public int exp;
        public int nextLevelExp;
        public int cigs;

        // Inventory
        public ArrayList<String> itemNames = new ArrayList<>();
        public ArrayList<Integer> itemAmounts = new ArrayList<>();
    }
}
