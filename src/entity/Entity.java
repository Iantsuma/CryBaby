package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int x,y;
	public int speed;
	
	public BufferedImage down0,down1,down2,up0,up1,up2,l0,l1,l2,r0,r1,r2;
	public String direction;
	public String state;
	
	public Rectangle collisionBox;
	public Boolean collisionOn = false;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
