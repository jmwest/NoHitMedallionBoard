package noHitMedallionBoard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.OverlayLayout;

public class NoHitMedallionBoard implements ActionListener {

    final static int caseTop=100;
    final static int caseLeft=70;
    final static int caseRight=70;
    final static int caseBottom=90;
    final static int caseAvailableWidth=485;
    final static int caseAvailableHeight=274;
    
    private Dimension badgePanelDimension = new Dimension();

    private String userLoadFilePathString = System.getProperty("user.dir") + System.getProperty("file.separator")
											+ "resources" + System.getProperty("file.separator");
    private String userSaveFilePathString = System.getProperty("user.dir") + System.getProperty("file.separator")
    										+ "resources" + System.getProperty("file.separator") + "No_Hit_Medallion_usersave.txt";
    private boolean useZeldaPreset = false;
    
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem preferencesMenuItem;
	private JMenu editMenu;
	private JMenuItem editMedalListMenuItem;
	
	private EditMedalListMenuFrame editMedalListMenuFrame;
	
	private JFrame frame;
	private JPanel badgePanel;
	private JPanel casePanel;
	private JLabel caseLabel;
	private JLabel backgroundLabel;
	private BufferedImage caseLabelImage;
	private BufferedImage backgroundLabelImage;
	private ArrayList<MedallionCombo> medallionArrayList;

	public NoHitMedallionBoard() {
		
		System.out.println("Working Directory: " + System.getProperty("user.dir"));
		
		frame = new JFrame();
		badgePanel = new JPanel();
		casePanel = new JPanel();
		
		Insets insets = frame.getInsets();
		
		medallionArrayList = new ArrayList<MedallionCombo>();
		
		// Menu setup
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit"); 
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		// Create MenuItems and handle them.
		saveMenuItem = new JMenuItem("Save...");
		saveMenuItem.addActionListener(this);
		
		preferencesMenuItem = new JMenuItem("Preferences");
		preferencesMenuItem.addActionListener(this);

		editMedalListMenuItem = new JMenuItem("Medal List");
		editMedalListMenuItem.addActionListener(this);
		
		//
		fileMenu.add(saveMenuItem);
		/* TODO
		 * Implement preferencesMenuItem
		 *fileMenu.add(preferencesMenuItem);
		 */
		
		editMenu.add(editMedalListMenuItem);
		
		// Handle caseLabel image file
		caseLabelImage = openResourceImageFile("Medal_Case_Background_Transparent_Gradient.png");
		
		// Handle caseLabel image file
		backgroundLabelImage = openResourceImageFile("Medal_Case_Inset_With_Gradient.png");
		
		// 
		badgePanelDimension.width = 150*4 + 20*3;
		badgePanelDimension.height = 100*2 + 50*2 + 40*2 + 10*2 + 60;
		
		// Load Program Data from Save
		loadProgram();
		
		if (useZeldaPreset) {
			//
			ArrayList<Point> medAL = calculateMedallionLocations(6, badgePanelDimension.width, badgePanelDimension.height);
			
			medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Ocarina of Time",
								   "Ocarina_of_Time.png", backgroundLabelImage, medAL.get(0)));
			medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Majora's Mask",
								   "Majoras_Mask.png", backgroundLabelImage, medAL.get(1)));
			medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Wind Waker",
								   "Wind_Waker.png", backgroundLabelImage, medAL.get(2)));
			medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Twilight Princess",
									"Fusedshadow.png", backgroundLabelImage, medAL.get(3)));
			medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Skyward Sword",
									"SS_Zelda.png", backgroundLabelImage, medAL.get(4)));
			medallionArrayList.add(new noHitMedallionBoard.MedallionCombo("The Legend of Zelda: Breath of the Wild",
									"Hestus_Gift.png", backgroundLabelImage, medAL.get(5)));
		}
		
		//
		badgePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		badgePanel.setOpaque(false);
		badgePanel.setLayout(createBadgeGroupLayout(badgePanel, medallionArrayList));
		
		setPanelDimensions(badgePanel, badgePanelDimension);
		
		badgePanel.setAlignmentX(0.5f);
		badgePanel.setAlignmentY(0.49f);
		
		//
		float caseRatioMult = calculateCaseRatio(badgePanelDimension);
		int caseLabelWidth = (int)((caseLeft + caseAvailableWidth + caseRight) * caseRatioMult);
		int caseLabelHeight = (int)((caseTop + caseAvailableHeight + caseBottom) * caseRatioMult);
		
		caseLabel = new JLabel(getScaledLabelIcon(caseLabelImage, caseLabelWidth, caseLabelHeight));
		caseLabel.setOpaque(false);
		caseLabel.setMinimumSize(new Dimension(caseLabelWidth, caseLabelHeight));
		caseLabel.setPreferredSize(new Dimension(caseLabelWidth, caseLabelHeight));
		caseLabel.setMaximumSize(new Dimension(caseLabelWidth, caseLabelHeight));
		caseLabel.setAlignmentX(0.5f);
		caseLabel.setAlignmentY(0.5f);
				
		backgroundLabel = new JLabel(getScaledLabelIcon(backgroundLabelImage, caseLabelWidth, caseLabelHeight));
		backgroundLabel.setOpaque(false);
		backgroundLabel.setMinimumSize(new Dimension(caseLabelWidth, caseLabelHeight));
		backgroundLabel.setPreferredSize(new Dimension(caseLabelWidth, caseLabelHeight));
		backgroundLabel.setMaximumSize(new Dimension(caseLabelWidth, caseLabelHeight));
		backgroundLabel.setAlignmentX(0.5f);
		backgroundLabel.setAlignmentY(0.5f);
		
		int casePanelWidth = insets.left + 10 + caseLabelWidth + 10 + insets.right;
		int casePanelHeight = insets.top + 10 + caseLabelHeight + 10 + insets.bottom;
		
		casePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		casePanel.setLayout(createCaseOverlayLayout(casePanel));
		casePanel.setPreferredSize(new Dimension(casePanelWidth, casePanelHeight));
		casePanel.add(caseLabel);
		casePanel.add(badgePanel);
		casePanel.add(backgroundLabel);
		
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
		
		// Set up EditMedalListMenuFrame
		editMedalListMenuFrame = new EditMedalListMenuFrame(this);
		editMedalListMenuFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		editMedalListMenuFrame.setTitle("Edit Medal List");
		editMedalListMenuFrame.pack();
		editMedalListMenuFrame.setVisible(false);
	}
	
	public static void main(String[] args) {
		
		
		File file = new File(System.getProperty("user.dir") + "/No_Hit_Medallion_log.txt");
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			
			// Create new print stream for file.
	        PrintStream ps = new PrintStream(fos);

	        // Set file print stream.
	        System.setOut(ps);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Working dir:  " + System.getProperty("user.dir"));
		
		new NoHitMedallionBoard();

	}
	
	// Public Functions	
	public void saveEditMedalListMenuFrame() {
		
		badgePanel.setLayout(createBadgeGroupLayout(badgePanel, medallionArrayList));
		
		editMedalListMenuFrame.setAlwaysOnTop(false);
		editMedalListMenuFrame.setVisible(false);
		
		return;
	}
	
	public ArrayList<Point> calculateMedallionLocations(int arraySize, int panelWidth, int panelHeight) {
		
		ArrayList<Point> locationAL = new ArrayList<Point>();
		
		int array_rows = 0;
		
		if (arraySize != 0) {
			array_rows = 1 + (arraySize / 4);
		}
		
		for (int i = 0; i < arraySize; i++) {
			
			int medallionrow = 1 + (i / 4);
			int medallioncol = 1 + Math.floorMod(i, 4);
			
			int pointy = ((medallionrow * panelHeight) / (2 * array_rows));
			int pointx = (medallioncol * panelWidth) / 8;
			
			locationAL.add(new Point(pointx, pointy));
		}
		
		return locationAL;
	}
	
	public void removeMedallionComboFromPanel(MedallionCombo combo) {
		
		badgePanel.remove(combo.getMedallionButton());
		badgePanel.remove(combo.getMedallionTextPane());
		badgePanel.remove(combo.getNoHitTextPane());
		
		badgePanel.getLayout().removeLayoutComponent(combo.getMedallionButton());
		badgePanel.getLayout().removeLayoutComponent(combo.getMedallionTextPane());
		badgePanel.getLayout().removeLayoutComponent(combo.getNoHitTextPane());
		
		badgePanel.revalidate();
		badgePanel.repaint();
		
		return;
	}
	
	public void refreshCasePanelLayout() {
		casePanel.setLayout(createCaseOverlayLayout(casePanel));

		return;
	}
	
	// Implement getter/setter functions
	public ArrayList<MedallionCombo> getMedallionArrayList() {
		return medallionArrayList;
	}
	
	public void setMedallionArrayList(ArrayList<MedallionCombo> medArrayList) {
		medallionArrayList = medArrayList;
		
		return;
	}
	
	public BufferedImage getBackgroundLabelImage() {
		return backgroundLabelImage;
	}
	
	public void setBackgroundLabelImage(BufferedImage bgimg) {
		backgroundLabelImage = bgimg;
		
		return;
	}
	
	public Dimension getBadgePanelDimension() {
		return badgePanelDimension;
	}
	
	public void setBadgePanelDimension(Dimension badgedim) {
		badgePanelDimension = badgedim;
		
		return;
	}
	
	// Implement private Class functions
	private GroupLayout createBadgeGroupLayout(JPanel panel, ArrayList<MedallionCombo> medallions) {
		
		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(false);
		layout.setAutoCreateContainerGaps(false);
		
		/*
		 *  Horizontal Layout
		 */
		SequentialGroup rowSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create horizontal layout groupings
		for (int i = 0; (i < medallions.size()) && (i < 4); i++) {
			
			ParallelGroup columnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			for (int j = i; j < medallions.size(); j=j+4) {
				
				MedallionCombo currentMedallionCombo = medallions.get(j);
				
				ParallelGroup medallionParallelGroup = layout.createParallelGroup(Alignment.CENTER);
				medallionParallelGroup.addComponent(currentMedallionCombo.getMedallionTextPane());
				medallionParallelGroup.addComponent(currentMedallionCombo.getMedallionButton());
				medallionParallelGroup.addComponent(currentMedallionCombo.getNoHitTextPane());
				
				layout.setHonorsVisibility(currentMedallionCombo.getMedallionTextPane(), false);
				layout.setHonorsVisibility(currentMedallionCombo.getNoHitTextPane(), false);
				
				columnParallelGroup.addGroup(medallionParallelGroup);
			}
			
			rowSequentialGroup.addGroup(columnParallelGroup);
			rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 20);
		}
		
		layout.setHorizontalGroup(rowSequentialGroup);
		
		/*
		 * Vertical Layout
		 */
		SequentialGroup columnSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create vertical layout groupings
		for (int i = 0; i < medallions.size(); i=i+4) {
			
			ParallelGroup rowParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			for (int j = i; (j < i + 4) && (j < medallions.size()); j++) {
				
				MedallionCombo currentMedallionCombo = medallions.get(j);
				
				SequentialGroup medallionSequentialGroup = layout.createSequentialGroup();
				medallionSequentialGroup.addComponent(currentMedallionCombo.getMedallionTextPane());
				medallionSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 10);
				medallionSequentialGroup.addComponent(currentMedallionCombo.getMedallionButton());
				medallionSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 10);
				medallionSequentialGroup.addComponent(currentMedallionCombo.getNoHitTextPane());
				
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
	
	private float calculateCaseRatio(Dimension inlayDimension) {
		
		float caseRatio;
		float ratioX;
		float ratioY;
		
		ratioX = ((float) (inlayDimension.width)) / ((float) (caseAvailableWidth));
		ratioY = ((float) (inlayDimension.height)) / ((float) (caseAvailableHeight));
		
		if (ratioX > ratioY) {
			caseRatio = ratioX;
		}
		else {
			caseRatio = ratioY;
		}
		
		return caseRatio;
	}
	
	private void setPanelDimensions(JPanel panel, Dimension dim) {
		
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);
		
		return;
	}
	
	private void loadProgram() {
		
		System.out.println("Load savefile: " + userSaveFilePathString);

		File saveFile = new File(userSaveFilePathString);
		
		try (BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
		    String line;
		    
		    MedallionCombo currentCombo;
		    String medallionText = "";
		    boolean includeMedallionText = true;
		    BufferedImage medallionBImageFromFile = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		    BufferedImage medallionGSBImageFromFile = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		    String noHitText = "";
		    boolean includeNoHitText = true;
		    boolean medallionBGS = true;

		    int i = 0;
		    while ((line = br.readLine()) != null) {
		    	// process the line.
		    	if (i == 0) {
					medallionText = line;
				}
		    	else if (i == 1) {
		    		if (line.equalsIgnoreCase("true")) {
						includeMedallionText = true;
					}
		    		else {
						includeMedallionText = false;
					}
				}
		    	else if (i == 2) {
					medallionBImageFromFile = openImageFile(line);
				}
		    	else if (i == 3) {
					medallionGSBImageFromFile = openImageFile(line);
				}
				else if (i == 4) {
					noHitText = line;
					noHitText = noHitText.replaceAll("@@@@@@@@@@", System.getProperty("line.separator"));
				}
				else if (i == 5) {					
		    		if (line.equalsIgnoreCase("true")) {
						includeNoHitText = true;
					}
		    		else {
		    			includeNoHitText = false;
					}
				}
				else if (i == 6) {
					i = -1;

					if (line.equalsIgnoreCase("true")) {
						medallionBGS = true;
					}
		    		else {
		    			medallionBGS = false;
					}
					
					currentCombo = new MedallionCombo(medallionText, medallionBImageFromFile,
							  medallionGSBImageFromFile, noHitText);
					currentCombo.setIncludeMedallionText(includeMedallionText);
					currentCombo.setIncludeNoHitText(includeNoHitText);
					currentCombo.setMedallionBGS(medallionBGS);
					
					medallionArrayList.add(currentCombo);
				}
		    	
		    	i++;
		    }
		}
		catch (Exception e) {
			System.out.println("Excepton Occured: " + e.toString());
			useZeldaPreset = true;
		}
		
		return;
	}
	
	private void saveProgram() {
		
		File userSave = new File(userSaveFilePathString);
		
		if (!userSave.exists()) {
			try {
				File directory = new File(userSave.getParent());
				if (!directory.exists()) {
					directory.mkdirs();
				}
				userSave.createNewFile();
			} catch (IOException e) {
				System.out.println("Excepton Occured: " + e.toString());
			}
		}
		
		try {
			FileWriter saveWriter;
			saveWriter = new FileWriter(userSave.getAbsoluteFile(), false);
 
			// Writes text to a character-output stream
			BufferedWriter bufferWriter = new BufferedWriter(saveWriter);
			for (int i = 0; i < medallionArrayList.size(); i++) {
				MedallionCombo currentCombo = medallionArrayList.get(i);
				
				bufferWriter.write(currentCombo.getMedallionTextPane().getText());
				bufferWriter.newLine();
				bufferWriter.write(String.valueOf(currentCombo.getIncludeMedallionText()));
				bufferWriter.newLine();
				bufferWriter.write(currentCombo.getMedallionBImageFilePath());
				bufferWriter.newLine();
				bufferWriter.write(currentCombo.getMedallionGSBImageFilePath());
				bufferWriter.newLine();
				
				String noHitString = currentCombo.getNoHitTextPane().getText();
				noHitString = noHitString.replaceAll("\r\n", "@@@@@@@@@@");
				noHitString = noHitString.replaceAll("\n", "@@@@@@@@@@");
				
				bufferWriter.write(noHitString);
				bufferWriter.newLine();
				bufferWriter.write(String.valueOf(currentCombo.getIncludeNoHitText()));
				bufferWriter.newLine();
				bufferWriter.write(String.valueOf(currentCombo.getMedallionBGS()));
				bufferWriter.newLine();
			}
			
			// Close FileWriter
			bufferWriter.close();
 
		} catch (IOException e) {
			System.out.println("Excepton Occured: " + e.toString());
		}
		
		return;
	}
	
	// Private Image functions
	private BufferedImage openResourceImageFile(String filename) {
		
		System.out.println("Filename pass to openResourceImageFile: " + filename);
		InputStream imageStream = this.getClass().getClassLoader().getResourceAsStream(filename);
		
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);

		if (imageStream != null) {

			try {
	            image = ImageIO.read(imageStream);
			} catch (Exception ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		else {
            System.out.println("file " + filename + " does not exist");
            
            image = openImageFile(userLoadFilePathString + filename);
		}
		
		return image;
	}
	
	private BufferedImage openImageFile(String filename) {
		
		File file = new File(filename);
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		
		try {
			System.out.println("Canonical path of target image: " + file.getCanonicalPath());
            if (!file.exists()) {
                System.out.println("file " + file + " does not exist");
            }
            image = ImageIO.read(file);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		return image;
	}
	
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
	
	private ImageIcon getScaledLabelIcon(BufferedImage srcImg, int width, int height) {
		
		BufferedImage scldImg = getScaledImage(srcImg, width, height);
		
		ImageIcon icon = new ImageIcon(scldImg);
		
		return icon;
	}
	
	private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {
		
		if (w < 1) {
			w = 100;
		}
		
		if (h < 1) {
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

			editMedalListMenuFrame.setAlwaysOnTop(false);
			editMedalListMenuFrame.setVisible(true);
		}
	}

}
