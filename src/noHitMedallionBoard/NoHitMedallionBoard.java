package noHitMedallionBoard;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoHitMedallionBoard {

	private JFrame frame;
	private JPanel panel;
	private noHitMedallionBoard.MedallionCombo zootCombo;
	private noHitMedallionBoard.MedallionCombo mmCombo;
	private noHitMedallionBoard.MedallionCombo wwCombo;
	private noHitMedallionBoard.MedallionCombo tpCombo;
	private noHitMedallionBoard.MedallionCombo ssCombo;
	private noHitMedallionBoard.MedallionCombo botwCombo;

	public NoHitMedallionBoard() {
		
		frame = new JFrame();
		panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		
		zootCombo = new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Ocarina of Time",
				"/Volumes/Seagate 2 TB Storage/300px-Light-Medallion.png");
		mmCombo = new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Majora's Mask",
				"/Volumes/Seagate 2 TB Storage/Moon.png");
		wwCombo = new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Wind Waker",
				"/Volumes/Seagate 2 TB Storage/Moon.png");
		tpCombo = new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Twilight Princess",
				"/Volumes/Seagate 2 TB Storage/Moon.png");
		ssCombo = new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Skyward Sword",
				"/Volumes/Seagate 2 TB Storage/Moon.png");
		botwCombo = new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Breath of the Wild",
				"/Volumes/Seagate 2 TB Storage/Moon.png");

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
										.addComponent(zootCombo.getMedallionTextArea())
										.addComponent(zootCombo.getMedallionButton())
								)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 40)
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
										.addComponent(mmCombo.getMedallionTextArea())
										.addComponent(mmCombo.getMedallionButton())
								)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 40)
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
										.addComponent(wwCombo.getMedallionTextArea())
										.addComponent(wwCombo.getMedallionButton())
								)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 40)
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
										.addComponent(tpCombo.getMedallionTextArea())
										.addComponent(tpCombo.getMedallionButton())
								)
						)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
										.addComponent(ssCombo.getMedallionTextArea())
										.addComponent(ssCombo.getMedallionButton())
								)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 40)
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
										.addComponent(botwCombo.getMedallionTextArea())
										.addComponent(botwCombo.getMedallionButton())
								)
						)
		);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(zootCombo.getMedallionTextArea())
										.addComponent(zootCombo.getMedallionButton())
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(mmCombo.getMedallionTextArea())
										.addComponent(mmCombo.getMedallionButton())
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(wwCombo.getMedallionTextArea())
										.addComponent(wwCombo.getMedallionButton())
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(tpCombo.getMedallionTextArea())
										.addComponent(tpCombo.getMedallionButton())
								)
						)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 60, 60)
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(ssCombo.getMedallionTextArea())
										.addComponent(ssCombo.getMedallionButton())
								)
								.addGroup(layout.createSequentialGroup()
										.addComponent(botwCombo.getMedallionTextArea())
										.addComponent(botwCombo.getMedallionButton())
								)
						)
		);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(730, 440));
		
		frame.add(panel, BorderLayout.CENTER);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				
				zootCombo.frameResizeEvent();
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
