package noHitMedallionBoard;

import noHitMedallionBoard.*;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoHitMedallionBoard {

	private JFrame frame;
	//private JPanel panel;
	private noHitMedallionBoard.MedallionPanel panel;
	//private MedallionPanel zootPanel;
	//private MedallionPanel mmPanel;
	
	public NoHitMedallionBoard() {
		
		frame = new JFrame();
		//panel = new JPanel();
		panel = new noHitMedallionBoard.MedallionPanel("The Legend of Zelda: Ocarina of Time",
				"/Volumes/Seagate 2 TB Storage/300px-Light-Medallion.png");
		
		/*zootPanel = new MedallionPanel("The Legend of Zelda: Ocarina of Time",
				"/Volumes/Seagate 2 TB Storage/300px-Light-Medallion.png");
		mmPanel = new MedallionPanel("The Legend of Zelda: Majora's Mask",
				"/Volumes/Seagate 2 TB Storage/Moon.png");
		*/
		/*
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0, 4));
		panel.setPreferredSize(new Dimension(500, 120));
		panel.add(zootPanel);
		panel.add(mmPanel);
		*/
		frame.add(panel, BorderLayout.CENTER);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				
				panel.frameResizeEvent();
				//zootPanel.frameResizeEvent();
				//mmPanel.frameResizeEvent();
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Completed Zelda No Hit Runs");
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		System.out.println("Working dir:  " + System.getProperty("user.dir"));
		
		new NoHitMedallionBoard();

	}

}
