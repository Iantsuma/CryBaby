package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Panel;

public class PowerUp extends Entity {
    
    Panel gp;

    public PowerUp(Panel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        
        // Define o tamanho da hitbox (um pouco menor que o tile para ficar bonitinho)
        this.collisionBox = new Rectangle(0, 0, gp.realTileSize, gp.realTileSize);
        
        getPowerUpImage();
    }

    public void getPowerUpImage() {
        try {
            down0 = ImageIO.read(getClass().getResourceAsStream("/powerups/powerup_speed.png"));
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(down0, x, y, gp.realTileSize, gp.realTileSize, null);
    }
}