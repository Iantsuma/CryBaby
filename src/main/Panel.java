package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

import java.util.ArrayList;
import entity.Projectile;

public class Panel extends JPanel implements Runnable{
    public final int tileSize = 8;
    public final int scale = 6;
    public final int realTileSize = tileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    final int screenW = realTileSize * maxScreenCol; //768
    final int screenH = realTileSize * maxScreenRow; //576
    
    int FPS = 60;
    
    public int gameState;
    public final int playState = 1;
    public final int gameOverState = 2;
    public String winnerText = "";
    
    
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionDetector cDet = new CollisionDetector(this);
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    Player player = new Player(this, keyH, 1);
    Player player2 = new Player(this, keyH, 2);
    TileManager tileM = new TileManager(this);
    
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;
    
    public Panel(){
        this.setPreferredSize(new Dimension(screenW, screenH));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        gameState = playState;
    }
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();
    }
    
    //Game Loop ============================================================================================
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			timer += (currentTime - lastTime);
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta = 0;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " +drawCount);
				drawCount=0;
				timer = 0;
			}
			
		}
		
	}
	public void update() {
	    
	    // Se o jogo acabou, só verificamos se alguém apertou Espaço para reiniciar
	    if (gameState == gameOverState) {
	        if (keyH.spaceK == true) {
	            resetGame();
	        }
	        return; // Para o update aqui, não move bonecos nem tiros
	    }

	    // Lógica do jogo
	    
	    player.update();
	    player2.update();

	    for (int i = projectiles.size() - 1; i >= 0; i--) {
	        Projectile p = projectiles.get(i);
	        
	        if (p != null) {
	            p.update();


	            if (p.collisionOn) {
	                projectiles.remove(i); //matando os pjéteis que colidirem
	                continue; // Pula para o próximo tiro
	            }
	      
	            // Retângulo do Tiro
	            java.awt.Rectangle shotRect = new java.awt.Rectangle(
	                p.x + p.collisionBox.x, 
	                p.y + p.collisionBox.y, 
	                p.collisionBox.width, 
	                p.collisionBox.height
	            );

	            // Retângulo do Player 1
	            java.awt.Rectangle p1Rect = new java.awt.Rectangle(
	                player.x + player.collisionBox.x,
	                player.y + player.collisionBox.y,
	                player.collisionBox.width,
	                player.collisionBox.height
	            );

	            // Retângulo do Player 2
	            java.awt.Rectangle p2Rect = new java.awt.Rectangle(
	                player2.x + player2.collisionBox.x,
	                player2.y + player2.collisionBox.y,
	                player2.collisionBox.width,
	                player2.collisionBox.height
	            );

	            if (p.ownerId == 2 && shotRect.intersects(p1Rect)) {
	            	System.out.println("Tiro de "  +  p.ownerId);
	                gameState = gameOverState;
	                winnerText = "Player 2 Venceu!";
	                projectiles.remove(i);
	                break; 
	            }
	            
		        if (p.ownerId == 1 && shotRect.intersects(p2Rect)) {
		        	System.out.println("Tiro de "  +  p.ownerId);
		            gameState = gameOverState;
		            winnerText = "Player 1 Venceu!";
		            projectiles.remove(i);
		            break; 
	        }
	        }
	    }
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D)g;
		tileM.draw(g2);
		for (Projectile p : projectiles) {
	        p.draw(g2);
	    }
		player.draw(g2);
		player2.draw(g2);
		if (gameState == gameOverState) {
	        //Minha telinha de game over

	        g2.setColor(new Color(0, 0, 0, 150));
	        g2.fillRect(0, 0, screenW, screenH);
	        

	        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
	        g2.setColor(Color.white);
	        

	        int textLength = (int)g2.getFontMetrics().getStringBounds(winnerText, g2).getWidth();
	        int x = screenW / 2 - textLength / 2;
	        int y = screenH / 2 - (realTileSize * 2);
	        g2.drawString(winnerText, x, y);
	        
	        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
	        String restartText = "Pressione ESPAÇO para reiniciar";
	        textLength = (int)g2.getFontMetrics().getStringBounds(restartText, g2).getWidth();
	        x = screenW / 2 - textLength / 2;
	        y = screenH / 2 + (realTileSize * 2);
	        g2.drawString(restartText, x, y);
	    }
		g2.dispose();
	}
	
	public void resetGame() {
	    player.setDefaultValues(1);
	    player2.setDefaultValues(2);
	    projectiles.clear();
	    gameState = playState;
	}
	
}

