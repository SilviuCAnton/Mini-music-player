package com.silviucanton;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import javax.sound.midi.*;

public class MiniMusicPlayer {
	
	static JFrame f = new JFrame("My First Music Video");
	static MyDrawPanel m1;

	public static void main(String[] args) {
	}
	
	
	class MyDrawPanel extends JPanel implements ControllerEventListener {
		/*
		 * Inner Class pentru a putea implementa grafica in GUI
		 */
		
		private static final long serialVersionUID = 1L;
		boolean msg = false;
		
		public void controlChange(ShortMessage event) {
			/*
			 * Override pentru functia din Interfata Listener - se executa in momentul in care este aruncat un 
			 * Event
			 */
			msg = true;
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			/*
			 * Override pentru functia de paintComponent din JPanel pentru a putea randa grafica in GUI
			 */
			if (msg) {
				
				Graphics2D g2 = (Graphics2D) g;
				
				int r = (int) (Math.random() * 250);
				int gr = (int) (Math.random() * 250);
				int b = (int) (Math.random() * 250);
				
				g2.setColor(new Color(r, gr, b));
				
				int ht = (int) ((Math.random() * 120) + 10);
				int width = (int) ((Math.random() * 120) + 10);
				int x = (int) ((Math.random() * 40) + 10);
				int y = (int) ((Math.random() * 40) + 10);
				
				g2.fillRect(x, y, width, ht);
				msg = false;
				
			}
		}
	}

}
 