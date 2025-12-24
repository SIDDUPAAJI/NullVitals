package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // Player stats
    int level;
    int maxLife;
    int life;
    int maxAmmo;
    int ammo;
    int strength;
    int sanity;
    int exp;
    int nextLevelExp;
    int cigs;

    // Inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();

    // Equipped items
    public String equippedWeapon;
    public String equippedArmour;
    public String equippedLight;
    public String equippedProjectile;

    // Position and direction
    public int worldX;
    public int worldY;
    public String direction;
}
