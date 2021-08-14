package noHitMedallionBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class NoHitMedallionBoard implements ActionListener {

    final static int caseTop=100;
    final static int caseLeft=70;
    final static int caseRight=70;
    final static int caseBottom=90;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem preferencesMenuItem;
	private JMenu editMenu;
	private JMenuItem editMedalListMenuItem;

	private JFrame frame;
	private JPanel badgePanel;
	private JPanel casePanel;
	private JLabel caseLabel;
	private BufferedImage caseLabelImage;
	private ArrayList<MedallionCombo> medallionArrayList;

	public NoHitMedallionBoard() {
		
		frame = new JFrame();
		badgePanel = new JPanel();
		casePanel = new JPanel() {
		      /**
			 * 
			 */
			private static final long serialVersionUID = -1966498432290649222L;

			public boolean isOptimizedDrawingEnabled() {
		          return false;
		      }
		};
		caseLabel = new JLabel();
		
		Insets insets = frame.getInsets();
		
		// Menu setup
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		saveMenuItem = new JMenuItem("Save...");
		preferencesMenuItem = new JMenuItem("Preferences");
		editMedalListMenuItem = new JMenuItem("Medal List");
		
		fileMenu.add(saveMenuItem);
		fileMenu.add(preferencesMenuItem);
		
		editMenu.add(editMedalListMenuItem);
		
		//
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
		
		// 
		int badgePanelWidth = 150*4 + 20*3;
		int badgePanelHeight = 100*2 + 10*2 + 60;
		
		badgePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		badgePanel.setLayout(createBadgeGroupLayout(badgePanel, medallionArrayList));
		badgePanel.setPreferredSize(new Dimension(badgePanelWidth, badgePanelHeight));
		badgePanel.setAlignmentX(0.5f);
		badgePanel.setAlignmentY(0.5f);
		
		// Handle caseLabel image file
		File caseLabelImageFile = new File("/Volumes/Seagate 2 TB Storage/Medal_Case_Background_Transparent.png");
		
		try {
			System.out.println("Canonical path of target image: " + caseLabelImageFile.getCanonicalPath());
            if (!caseLabelImageFile.exists()) {
                System.out.println("file " + caseLabelImageFile + " does not exist");
            }
            caseLabelImage = ImageIO.read(caseLabelImageFile);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		int caseLabelWidth = caseLeft + 10 + badgePanelWidth + 10 + caseRight;
		int caseLabelHeight = caseTop + 10 + badgePanelHeight + 10 + caseBottom;
		
		caseLabel = new JLabel(new ImageIcon(caseLabelImage));
		caseLabel.setOpaque(false);
		caseLabel.setMinimumSize(new Dimension(caseLabelWidth, caseLabelHeight));
		caseLabel.setPreferredSize(new Dimension(caseLabelWidth, caseLabelHeight));
		caseLabel.setMaximumSize(new Dimension(caseLabelWidth, caseLabelHeight));
		caseLabel.setAlignmentX(0.5f);
		caseLabel.setAlignmentY(0.5f);
		
		int casePanelWidth = insets.left + 10 + caseLabelWidth + 10 + insets.right;
		int casePanelHeight = insets.top + 10 + caseLabelHeight + 10 + insets.bottom;
		
		casePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		casePanel.setLayout(createCaseOverlayLayout(casePanel));
		casePanel.setPreferredSize(new Dimension(casePanelWidth, casePanelHeight));
		casePanel.add(caseLabel);
		casePanel.add(badgePanel);
		
		frame.setJMenuBar(menuBar);
		frame.add(casePanel, BorderLayout.CENTER);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				
				changeLabelImage(caseLabel, caseLabelImage);
				
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
	
	// Implement private Class functions
	private GroupLayout createBadgeGroupLayout(JPanel panel, ArrayList<MedallionCombo> medallions) {
		
		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		SequentialGroup rowSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create horizontal layout groupings
		for (int i = 0; (i < medallions.size()) && (i < 4); i++) {
			
			ParallelGroup columnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			for (int j = i; j < medallions.size(); j=j+4) {
				
				MedallionCombo currentMedallionCombo = medallions.get(j);
				
				ParallelGroup medallionParallelGroup = layout.createParallelGroup(Alignment.CENTER);
				medallionParallelGroup.addComponent(currentMedallionCombo.getMedallionTextArea());
				medallionParallelGroup.addComponent(currentMedallionCombo.getMedallionButton());
				
				columnParallelGroup.addGroup(medallionParallelGroup);
			}
			
			rowSequentialGroup.addGroup(columnParallelGroup);
			rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 20);
		}
		
		layout.setHorizontalGroup(rowSequentialGroup);
		
		SequentialGroup columnSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create vertical layout groupings
		for (int i = 0; i < medallions.size(); i=i+4) {
			
			ParallelGroup rowParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			for (int j = i; (j < i + 4) && (j < medallions.size()); j++) {
				
				MedallionCombo currentMedallionCombo = medallions.get(j);
				
				SequentialGroup medallionSequentialGroup = layout.createSequentialGroup();
				medallionSequentialGroup.addComponent(currentMedallionCombo.getMedallionTextArea());
				medallionSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 10);
				medallionSequentialGroup.addComponent(currentMedallionCombo.getMedallionButton());
				
				rowParallelGroup.addGroup(medallionSequentialGroup);
			}
			
			columnSequentialGroup.addGroup(rowParallelGroup);
			columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 60, 60);
		}
		
		layout.setVerticalGroup(columnSequentialGroup);
		
		return layout;
	}
	
	private OverlayLayout createCaseOverlayLayout(JPanel panel) {
		
		OverlayLayout layout = new OverlayLayout(panel);

		/*
		 *  Add any additional OverlayLayout customization here.
		 */
		
		return layout;
	}
	
	private void saveProgram() {
		
	}
	
	// Private Image functions
		private void changeLabelImage(JLabel label, BufferedImage bimg) {
			label.setIcon(getScaledLabelIcon(bimg, label));
			
			return;
		}
		
		private ImageIcon getScaledLabelIcon(BufferedImage srcImg, JLabel label) {
			
			Dimension d = label.getSize();
			BufferedImage scldImg = getScaledImage(srcImg, d.width, d.height);
			
			ImageIcon icon = new ImageIcon(scldImg);
			
			return icon;
		}
		
		private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {
			
			if (w == 0) {
				w = 100;
			}
			
			if (h == 0) {
				h = 100;
			}
			
			BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g = newImg.createGraphics();
			g.drawImage(srcImg, 0, 0, w, h, null);
			g.dispose();
			
			return newImg;
		}

	// Implement Overridden functions
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == saveMenuItem) {
			saveProgram();
		}
		else if (e.getSource() == preferencesMenuItem) {
			
		}
		else if (e.getSource() == editMedalListMenuItem) {
			
		}
	}

}
