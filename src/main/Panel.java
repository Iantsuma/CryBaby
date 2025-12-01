package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import entity.Player;
import entity.PowerUp;
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
    public int powerUpSpawnTimer = 0;
    
    
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionDetector cDet = new CollisionDetector(this);
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    public ArrayList<PowerUp> powerUps = new ArrayList<>();
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

	    if (powerUps.isEmpty()) {
	        powerUpSpawnTimer++;
	        
	        if (powerUpSpawnTimer >= 1200) { // 1200 frames = 10 segundos
	            spawnPowerUp();
	            powerUpSpawnTimer = 0; // Reseta para contar de novo na próxima vez
	        }
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
	    for (int i = 0; i < powerUps.size(); i++) {
	        PowerUp pu = powerUps.get(i);
	        
	        if(pu != null) {
	            // Cria retângulo do PowerUp
	            Rectangle puRect = new Rectangle(pu.x, pu.y, realTileSize, realTileSize);
	            
	            // Cria retângulos dos Players
	            Rectangle p1Rect = new Rectangle(player.x + player.collisionBox.x, player.y + player.collisionBox.y, player.collisionBox.width, player.collisionBox.height);
	            Rectangle p2Rect = new Rectangle(player2.x + player2.collisionBox.x, player2.y + player2.collisionBox.y, player2.collisionBox.width, player2.collisionBox.height);
	            
	            // SE PLAYER 1 PEGAR
	            if(puRect.intersects(p1Rect)) {
	                System.out.println("Player 1 pegou PowerUp!");
	                player.activatePowerUp(pu.type);
	                powerUps.remove(i); // Remove o item da tela
	                // Opcional: spawnar outro logo em seguida
	                // spawnPowerUp(); 
	            }
	            // SE PLAYER 2 PEGAR
	            else if(puRect.intersects(p2Rect)) {
	                System.out.println("Player 2 pegou PowerUp!");
	                player2.activatePowerUp(pu.type);
	                powerUps.remove(i);
	            }
	        }
	    }
	    
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D)g;
		tileM.draw(g2);
		for(PowerUp pu : powerUps) {
	        pu.draw(g2);
	    }
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
	    powerUps.clear();
	    player.fireRate = 120;
	    player.powerUpTimer = 0;
	    player2.fireRate = 120;
	    player2.powerUpTimer = 0;
	    powerUpSpawnTimer= 0;
	    //spawnPowerUp();
	    gameState = playState;
	}
	
	public void spawnPowerUp() {
	    int col;
	    int row;
	    boolean validSpot = false;
	    
	    // Tenta achar um lugar válido (Grama = 0)
	    while(!validSpot) {
	        // Sorteia uma coluna e linha aleatória dentro do mapa
	        col = (int)(Math.random() * maxScreenCol);
	        row = (int)(Math.random() * maxScreenRow);
	        
	        if(tileM.mapTileNum[col][row] == 0) {
	            int type = (int)(Math.random()*2);
	            int worldX = col * realTileSize;
	            int worldY = row * realTileSize;
	            
	            powerUps.add(new PowerUp(this, worldX, worldY, type));
	            validSpot = true;
	            System.out.println("PowerUp Spawnado em: " + col + "," + row);
	        }
	    }
	}
	
	
}

