package Environment;

import java.awt.Graphics2D;

import Main.GamePanel;

public class EnvironmentManager {
GamePanel gp;
Lighting lighting;
public EnvironmentManager(GamePanel gp) {
	this.gp=gp;
}
public void setup() {
	lighting=new Lighting(gp);
}
public void update() {
	lighting.update();
}
public void draw(Graphics2D g2) {
	lighting.draw(g2);
}
}
