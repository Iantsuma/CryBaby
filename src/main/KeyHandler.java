package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	// Jogador 1 (WASD)
	public boolean upK, downK, leftK, rightK;
	public boolean spaceK; 
	
	// Jogador 2 (Setas)
	public boolean upArrow, downArrow, leftArrow, rightArrow; 
	public boolean controlK;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		// --- JOGADOR 1 (WASD E ESPAÇO) ---
		if (code == KeyEvent.VK_W) {
			upK = true;
		}
		if (code == KeyEvent.VK_S) {
			downK = true;
		}
		if (code == KeyEvent.VK_A) {
			leftK = true;
		}
		if (code == KeyEvent.VK_D) {
			rightK = true;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			spaceK = true;
		}
		
		// --- JOGADOR 2 (SETAS E CTRL) ---
		if (code == KeyEvent.VK_UP) {
			upArrow = true;
		}
		if (code == KeyEvent.VK_DOWN) {
			downArrow = true;
		}
		if (code == KeyEvent.VK_LEFT) {
			leftArrow = true;
		}
		if (code == KeyEvent.VK_RIGHT) {
			rightArrow = true;
		}
		if (code == KeyEvent.VK_CONTROL) {
			controlK = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		// --- JOGADOR 1 (WASD E ESPAÇO) ---
		if (code == KeyEvent.VK_W) {
			upK = false;
		}
		if (code == KeyEvent.VK_S) {
			downK = false;
		}
		if (code == KeyEvent.VK_A) {
			leftK = false;
		}
		if (code == KeyEvent.VK_D) {
			rightK = false;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			spaceK = false;
		}
		
		// --- JOGADOR 2 (SETAS E CRRL) ---
		if (code == KeyEvent.VK_UP) {
			upArrow = false;
		}
		if (code == KeyEvent.VK_DOWN) {
			downArrow = false;
		}
		if (code == KeyEvent.VK_LEFT) {
			leftArrow = false;
		}
		if (code == KeyEvent.VK_RIGHT) {
			rightArrow = false;
		}
		if (code == KeyEvent.VK_CONTROL) {
			controlK = false;
		}
		
	}
}