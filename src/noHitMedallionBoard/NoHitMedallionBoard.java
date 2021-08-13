package noHitMedallionBoard;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoHitMedallionBoard {

	private JFrame frame;
	private JPanel panel;
	private ArrayList<MedallionCombo> medallionArrayList;
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
		
		medallionArrayList = new ArrayList<MedallionCombo>(); 
		
		medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Ocarina of Time",
								"/Volumes/Seagate 2 TB Storage/Ocarina_of_Time.png"));
		medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Majora's Mask",
								"/Volumes/Seagate 2 TB Storage/Majoras_Mask.png"));
		medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Wind Waker",
								"/Volumes/Seagate 2 TB Storage/Wind_Waker.png"));
		medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Twilight Princess",
								"/Volumes/Seagate 2 TB Storage/Fusedshadow.png"));
		medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Skyward Sword",
								"/Volumes/Seagate 2 TB Storage/SS_Zelda.png"));
		medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Breath of the Wild",
								"/Volumes/Seagate 2 TB Storage/Hestus_Gift.png"));

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		SequentialGroup rowSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create horizontal layout groupings
		for (int i = 0; (i < medallionArrayList.size()) && (i < 4); i++) {
			
			ParallelGroup columnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			for (int j = i; j < medallionArrayList.size(); j=j+4) {
				
				MedallionCombo currentMedallionCombo = medallionArrayList.get(j);
				
				ParallelGroup medallionParallelGroup = layout.createParallelGroup(Alignment.CENTER);
				medallionParallelGroup.addComponent(currentMedallionCombo.getMedallionTextArea());
				medallionParallelGroup.addComponent(currentMedallionCombo.getMedallionButton());
				
				columnParallelGroup.addGroup(medallionParallelGroup);
			}
			
			rowSequentialGroup.addGroup(columnParallelGroup);
			rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 40);
		}
		
		layout.setHorizontalGroup(rowSequentialGroup);
		
		SequentialGroup columnSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create vertical layout groupings
		for (int i = 0; i < medallionArrayList.size(); i=i+4) {
			
			ParallelGroup rowParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			for (int j = i; (j < i + 4) && (j < medallionArrayList.size()); j++) {
				
				MedallionCombo currentMedallionCombo = medallionArrayList.get(j);
				
				SequentialGroup medallionSequentialGroup = layout.createSequentialGroup();
				medallionSequentialGroup.addComponent(currentMedallionCombo.getMedallionTextArea());
				medallionSequentialGroup.addComponent(currentMedallionCombo.getMedallionButton());
				
				rowParallelGroup.addGroup(medallionSequentialGroup);
			}
			
			columnSequentialGroup.addGroup(rowParallelGroup);
			columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 60, 60);
		}
		
		layout.setVerticalGroup(columnSequentialGroup);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(730, 440));
		
		frame.add(panel, BorderLayout.CENTER);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				
				for (Iterator<MedallionCombo> iterator = medallionArrayList.iterator(); iterator.hasNext();) {
					MedallionCombo medallionCombo = (MedallionCombo) iterator.next();
					
					medallionCombo.frameResizeEvent();
				}
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
