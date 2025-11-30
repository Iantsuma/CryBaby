package main;

import entity.Entity;
import entity.Projectile;

public class CollisionDetector {
	Panel gp;
	
	public CollisionDetector(Panel gp) {
		this.gp = gp;
	}
	
	public void checkTile (Entity entity) {
		
		// Calcunado as bordas de uma entidade
		int entityLeftEdge = entity.x + entity.collisionBox.x;
		int entityRightEdge = entity.x + entity.collisionBox.x + entity.collisionBox.width;
		int entityTopEdge = entity.y + entity.collisionBox.y;
		int entityBottomEdge = entity.y + entity.collisionBox.y + entity.collisionBox.height; 
		
		int entityLeftCol = entityLeftEdge/gp.realTileSize;
		int entityRightCol = entityRightEdge/gp.realTileSize;
		int entityTopRow = entityTopEdge/gp.realTileSize;
		int entityBottomRow = entityBottomEdge/gp.realTileSize;
		
		int tileNum1, tileNum2;
		
		boolean checkProjectile = false;
	    if (entity instanceof Projectile) {
	        checkProjectile = true;
	    }

	    switch(entity.direction) {
	    case "up":
	        entityTopRow = (entityTopEdge - entity.speed)/gp.realTileSize;
	        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
	        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
	        
	        // Verifica colisão baseado no tipo
	        if (checkProjectile) {
	            if(gp.tileM.tile[tileNum1].collisionProjectile || gp.tileM.tile[tileNum2].collisionProjectile) {
	                entity.collisionOn = true;
	            }
	        } else {
	            // Checagem normal para Player
	            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }
	        }
	        break;

	    case "down":
	        entityBottomRow = (entityBottomEdge + entity.speed)/gp.realTileSize;
	        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
	        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
	        
	        if (checkProjectile) {
	            if(gp.tileM.tile[tileNum1].collisionProjectile || gp.tileM.tile[tileNum2].collisionProjectile) {
	                entity.collisionOn = true;
	            }
	        } else {
	            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }
	        }
	        break;

	    case "left":
	        entityLeftCol = (entityLeftEdge - entity.speed)/gp.realTileSize;
	        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
	        tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
	        
	        if (checkProjectile) {
	            if(gp.tileM.tile[tileNum1].collisionProjectile || gp.tileM.tile[tileNum2].collisionProjectile) {
	                entity.collisionOn = true;
	            }
	        } else {
	            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }
	        }
	        break;

	    case "right":
	        entityRightCol = (entityRightEdge + entity.speed)/gp.realTileSize;
	        tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
	        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
	        
	        if (checkProjectile) {
	            if(gp.tileM.tile[tileNum1].collisionProjectile || gp.tileM.tile[tileNum2].collisionProjectile) {
	                entity.collisionOn = true;
	            }
	        } else {
	            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }
	        }
	        break;
	    }
		
	}
}
