package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler2 implements KeyListener{

	public boolean upK, downK, leftK, rightK;
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
			case KeyEvent.VK_UP:
				upK = true;
				break;
			case KeyEvent.VK_DOWN:
				downK = true;
				break;
			case KeyEvent.VK_LEFT:
				leftK = true;
				break;
			case KeyEvent.VK_RIGHT:
				rightK = true;
				break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
			case KeyEvent.VK_UP:
				upK = false;
				break;
			case KeyEvent.VK_DOWN:
				downK = false;
				break;
			case KeyEvent.VK_LEFT:
				leftK = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightK = false;
				break;
		}
		
	}

}
