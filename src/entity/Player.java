package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.KeyHandler;
import main.Panel;

public class Player extends Entity {
	Panel gp;
	KeyHandler keyH;
	public int cooldown = 0;

	public int playerId;
	
	
	public Player(Panel gp, KeyHandler keyH, int playerId) {
		this.gp = gp;
		this.keyH = keyH;
		this.playerId = playerId;
		
		collisionBox = new Rectangle(8, 16, 32, 32); //Um pouco menor que o bonequinho,
													 //era difícil de entrar em buracos
		
		
		setDefaultValues(playerId);
		getPlayerImage(playerId);
	}
	
	
	public void setDefaultValues(int playerId) {
	    if (playerId == 1) {

	        x = 1 * gp.realTileSize;
	        y = 1 * gp.realTileSize;
	        
	        speed = 5;
	        direction = "down";
	        state = "still";
	    }
	    else if(playerId == 2) {
	        x = 14 * gp.realTileSize;
	        y = 10 * gp.realTileSize;
	        
	        speed = 5;
	        direction = "down";
	        state = "still";
	    }
	}
	
	public void getPlayerImage(int playerId) {
		if (playerId == 1) {
			try {
				up0 = ImageIO.read(getClass().getResourceAsStream("/player/up0.png"));
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
				down0 = ImageIO.read(getClass().getResourceAsStream("/player/down0.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
				l0 = ImageIO.read(getClass().getResourceAsStream("/player/l0.png"));
				l1 = ImageIO.read(getClass().getResourceAsStream("/player/l1.png"));
				l2 = ImageIO.read(getClass().getResourceAsStream("/player/l2.png"));
				r0 = ImageIO.read(getClass().getResourceAsStream("/player/r0.png"));
				r1 = ImageIO.read(getClass().getResourceAsStream("/player/r1.png"));
				r2 = ImageIO.read(getClass().getResourceAsStream("/player/r2.png"));					
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
			else if (playerId == 2) {
				try {
					up0 = ImageIO.read(getClass().getResourceAsStream("/player2/up0.png"));
					up1 = ImageIO.read(getClass().getResourceAsStream("/player2/up1.png"));
					up2 = ImageIO.read(getClass().getResourceAsStream("/player2/up2.png"));
					down0 = ImageIO.read(getClass().getResourceAsStream("/player2/down0.png"));
					down1 = ImageIO.read(getClass().getResourceAsStream("/player2/down1.png"));
					down2 = ImageIO.read(getClass().getResourceAsStream("/player2/down2.png"));
					l0 = ImageIO.read(getClass().getResourceAsStream("/player2/l0.png"));
					l1 = ImageIO.read(getClass().getResourceAsStream("/player2/l1.png"));
					l2 = ImageIO.read(getClass().getResourceAsStream("/player2/l2.png"));
					r0 = ImageIO.read(getClass().getResourceAsStream("/player2/r0.png"));
					r1 = ImageIO.read(getClass().getResourceAsStream("/player2/r1.png"));
					r2 = ImageIO.read(getClass().getResourceAsStream("/player2/r2.png"));					
				}catch(IOException e) {
					e.printStackTrace();
				}
		}
	}
	public void update() {
		boolean shoot 			= false;
		boolean upPressed 		= false;
	    boolean downPressed 	= false;
	    boolean leftPressed 	= false;
	    boolean rightPressed 	= false;
	    
	    // 1. DETERMINAR QUAL O INPUT A LER
	    if (playerId == 1) { // Jogador 1 (WASD e espaços)
	        upPressed    = keyH.upK;
	        downPressed  = keyH.downK;
	        leftPressed  = keyH.leftK;
	        rightPressed = keyH.rightK;
	        shoot	     = keyH.spaceK;
	    } if (playerId == 2) { // Jogador 2 (Setas e ctrl)
	        upPressed    = keyH.upArrow;
	        downPressed  = keyH.downArrow;
	        leftPressed  = keyH.leftArrow;
	        rightPressed = keyH.rightArrow;
	        shoot		 = keyH.controlK;
	    }
	    if(shoot && cooldown == 0) {
	    	System.out.println("TIRO DE "+ playerId);
	    	int headX = 4 * gp.scale;
	    	int headY = 3 * gp.scale;
	    	
	    	int shotX = this.x + headX - (gp.realTileSize / 2 ); 
	        int shotY = this.y + headY - (gp.realTileSize / 2 ); 

	        Projectile newShot = new Projectile(gp, shotX, shotY, this.direction, this.playerId);
	        
	        gp.projectiles.add(newShot);
	        
	        cooldown = 120; // Define o cooldown (Exemplo: 30 frames = 0.5 segundos em 60 FPS) tive que diminuir pq tava impossível
	    }
	    if (cooldown > 0) {
	        cooldown--;
	    }
		if (upPressed == true || downPressed == true || leftPressed == true || rightPressed == true) {
			
			state = "moving";
			

			if (leftPressed) {
				direction = "left";
				
			}
			if (rightPressed) {
				direction = "right";
				
			}
			if (upPressed) {
				direction = "up";
				
			}
			if (downPressed) {
				direction = "down";
				
			}
			
			// Verificando a colisão
			collisionOn = false;
			gp.cDet.checkTile(this);
			
			// Se colisão falso, bonequinho movimenta.
			if (!collisionOn) {
					switch(direction) {
					case "left":
						x -= speed;
						break;
					case "right":
						x += speed;
						break;
					case "up":
						y -= speed;
						break;
					case "down":
						y += speed;
						break;
						
				}
			}
			
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1){
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
		} 
		else {
			state = "still"; 
		}
	}
	public void draw(Graphics2D g2) {
		BufferedImage image =null;
		
		switch(direction) {
		case "up":
			if(spriteNum ==1) {
				image = up1;
			}
			else if(spriteNum ==2) {
				image = up2;
			}
			if (state == "still") {
					image = up0;
			}
			
			break;
		case "down":
			if(spriteNum ==1) {
				image = down1;
			}
			else if(spriteNum ==2) {
				image = down2;
			}
			if (state == "still") {
					image = down0;
			}
			break;
		case "left":
			if(spriteNum ==1) {
				image = l1;
			}
			else if(spriteNum ==2) {
				image = l2;
			}
			if (state == "still") {
				image = l0;
			}
			break;
		case "right":
			if(spriteNum ==1) {
				image = r1;
			}
			else if(spriteNum ==2) {
				image = r2;
			}
			if (state == "still") {
				image = r0;
			}
			break;
		
		}
		g2.drawImage(image, x, y, gp.realTileSize, gp.realTileSize, null);
		
	}
}
