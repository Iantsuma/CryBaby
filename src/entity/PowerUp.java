package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Panel;

public class PowerUp extends Entity {
    
    Panel gp;
    public int type;

    public PowerUp(Panel gp, int x, int y, int type) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.type = type;
        
        // Define o tamanho da hitbox (um pouco menor que o tile para ficar bonitinho)
        this.collisionBox = new Rectangle(0, 0, gp.realTileSize, gp.realTileSize);
        
        getPowerUpImage();
    }

    public void getPowerUpImage() {
        try {
        	if (type == 0) {
        		down0 = ImageIO.read(getClass().getResourceAsStream("/powerups/powerup_speed.png"));
        	}
        	else if (type == 1) {
        		down0 = ImageIO.read(getClass().getResourceAsStream("/powerups/powerup_red.png"));
        	}
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(down0, x, y, gp.realTileSize, gp.realTileSize, null);
    }
}