package com.silviucanton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.*;

public class MiniMusicPlayer implements ActionListener {
	
	static JFrame f = new JFrame("My First Music Video");
	static MyDrawPanel m1;
	static JButton b;

	public static void main(String[] args) {
		MiniMusicPlayer mini = new MiniMusicPlayer();
		mini.go();
	}
	
	public void setUpGUI() {
		/*
		 * Initializeaza GUI-ul
		 */
		b = new JButton("Start");
		b.addActionListener(this);
		f.getContentPane().add(BorderLayout.SOUTH, b);
		
		m1 = new MyDrawPanel();
		f.getContentPane().add(BorderLayout.CENTER, m1);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(30, 30, 300, 300);
		f.setVisible(true);
	}
	
	public void go() {
		/*
		 * Seteaza GUI- ul
		 */
		setUpGUI();
	}
	
	public MidiEvent makeEvent(int cmd, int chan, int one, int two, int tick) {
		/*
		 * Creeaza un MidiEvent mai rapid, nu mai trebuie declarat manual mesajul de instructiuni MIDI
		 */
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage(cmd, chan, one, two);
			event = new MidiEvent(a, tick);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}
	
	
	class MyDrawPanel extends JPanel implements ControllerEventListener {
		/*
		 * Inner Class pentru a putea implementa grafica in GUI astfel incat sa poata implementa interefata
		 * de Listener
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

	public void actionPerformed(ActionEvent e) {
		try {
			
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addControllerEventListener(m1, new int[] {127});
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			
			int r = 0;
			for (int i = 0; i < 60; i+= 4) {
				
				r = (int) ((Math.random() * 70) + 1);
				track.add(makeEvent(144, 1, r, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, r, 100, i + 2));
			}
			
			sequencer.setSequence(seq);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch (Exception ex ) {
			ex.printStackTrace();
		}
	}

}
 