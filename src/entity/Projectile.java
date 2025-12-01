package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Panel;

public class Projectile extends Entity{
	
	Panel gp;
	public int ownerId;
	public boolean spectral = false;
	public int type;
	BufferedImage imgNormal, imgSpectral, imgBig;
	
	
	
	
	
	
	public Projectile(Panel gp, int x, int y, String direction, int ownerId, int type) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this. ownerId = ownerId;
		this.type = type;
		if (this.type == 1) {
			this.spectral = true;
		}
		
		this.speed = 10;
		int scale = gp.realTileSize / gp.tileSize;
		int hitWidth, hitHeight;
		int hitX, hitY;
		if(direction.equals("left") || direction.equals("right")) {
	        hitWidth = 6 * scale;  // 36 px
	        hitHeight = 2 * scale; // 12 px
		}
		else {
	        hitWidth = 2 * scale;  // 12 px
	        hitHeight = 6 * scale; // 36 px
	    }
		
		hitX = (gp.realTileSize - hitWidth) / 2;
	    hitY = (gp.realTileSize - hitHeight) / 2;
		
		this.collisionBox = new Rectangle(hitX, hitY, hitWidth, hitHeight);
		getProjectileImage();
	}
	
	public void getProjectileImage() {
		try {
			imgNormal = ImageIO.read(getClass().getResourceAsStream("/projectiles/tear.png"));
			imgSpectral = ImageIO.read(getClass().getResourceAsStream("/projectiles/tear_red.png"));
			imgBig = ImageIO.read(getClass().getResourceAsStream("/projectiles/tear.png"));
		} catch(IOException e) {
            e.printStackTrace();
		}
	}
	
	public void update() {
		// verifica colisão
		collisionOn = false;
		gp.cDet.checkTile(this); 
		
		if (collisionOn) {
			// O projétil bateu em um tijolinho. Ele deve desaparecer.
			return; 
		}
		
		// 2. Mover o projétil
		switch (direction) {
			case "up":
				y -= speed;
				break;
			case "down":
				y += speed;
				break;
			case "left":
				x -= speed;
				break;
			case "right":
				x += speed;
				break;
		}
		

		}
		
	public void draw(Graphics2D g2) {
		int drawSize = gp.realTileSize;
		BufferedImage image = imgNormal;
		if (type == 1) {
			image = imgSpectral;
		}
		g2.drawImage(image, x + (gp.realTileSize / 2 - drawSize / 2), y + (gp.realTileSize / 2 - drawSize / 2), drawSize, drawSize, null);
	}
	
	
	
	}
	
