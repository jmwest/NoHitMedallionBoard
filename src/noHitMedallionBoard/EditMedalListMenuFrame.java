package noHitMedallionBoard;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;



public class EditMedalListMenuFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1061822005019075338L;
	
	private static final String deleteMedalString = "Delete Medal";
	private static final String medalTextCheckBoxString = "Medal Text Check Box";
	private static final String noHitCheckBoxString = "No Hit Text Check Box";
	private static final String changeMedalButtonString = "Change Medal Button";
	
	private float shadowLengthMultiplier = 0.02f;

	private NoHitMedallionBoard parentFrame;
	private ArrayList<MedallionCombo> medallionComboArrayList;
	
	private JPanel editMLMFPanel;
	
	private JTextPane deleteMedalHeader;
	private JTextPane medalTextCheckBoxHeader;
	private JTextPane medalTextHeader;
	private JTextPane medalNoHitCheckBoxHeader;
	private JTextPane medalNoHitHeader;
	private JTextPane changeMedalHeader;
	
	private ArrayList<JButton> deleteMedalButtonArrayList;
	private ArrayList<JCheckBox> medalTextCheckBoxArrayList;
	private ArrayList<JTextArea> medalTextArrayList;
	private ArrayList<JCheckBox> medalNoHitCheckBoxArrayList;
	private ArrayList<JTextArea> medalNoHitArrayList;
	private ArrayList<JButton> changeMedalButtonArrayList;
	
	private JButton addNewMedalButton;
	private JButton saveButton;
		
	public EditMedalListMenuFrame(NoHitMedallionBoard parent) {
		
		parentFrame = parent;
		medallionComboArrayList = parent.getMedallionArrayList();
		
		// Initialize and set header TextAreas
		deleteMedalHeader = new JTextPane();
		medalTextCheckBoxHeader = new JTextPane();
		medalTextHeader = new JTextPane();
		medalNoHitCheckBoxHeader = new JTextPane();
		medalNoHitHeader = new JTextPane();
		changeMedalHeader = new JTextPane();
		
		setJTextPaneDimensions(deleteMedalHeader, 50, 75, 150, 20);
		setJTextPaneDimensions(medalTextCheckBoxHeader, 100, 150, 200, 20);
		setJTextPaneDimensions(medalTextHeader, 100, 150, 200, 20);
		setJTextPaneDimensions(medalNoHitCheckBoxHeader, 150, 150, 200, 20);
		setJTextPaneDimensions(medalNoHitHeader, 100, 150, 200, 20);
		setJTextPaneDimensions(changeMedalHeader, 100, 150, 200, 20);
		
		setTextPaneAttributes(noHitMedallionBoard.Alignment.CENTER, deleteMedalHeader, "Delete", false, true);
		setTextPaneAttributes(noHitMedallionBoard.Alignment.CENTER, medalTextCheckBoxHeader, "Include Title Text", false, true);
		setTextPaneAttributes(noHitMedallionBoard.Alignment.CENTER, medalTextHeader, "Edit Title Text", false, true);
		setTextPaneAttributes(noHitMedallionBoard.Alignment.CENTER, medalNoHitCheckBoxHeader, "Include Challenge Text", false, true);
		setTextPaneAttributes(noHitMedallionBoard.Alignment.CENTER, medalNoHitHeader, "Edit Challenge Text", false, true);
		setTextPaneAttributes(noHitMedallionBoard.Alignment.CENTER, changeMedalHeader, "Change Medal Image", false, true);
		
		// Initialize and Set Save and AddNewMedal Buttons        
		addNewMedalButton = new JButton();
		saveButton = new JButton();
		
		addNewMedalButton.setText("Add New Medal");
		saveButton.setText("Save");
		
		addNewMedalButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		addNewMedalButton.setMinimumSize(new Dimension(100, 20));
		addNewMedalButton.setPreferredSize(new Dimension(100, 20));
		addNewMedalButton.setMaximumSize(new Dimension(100, 20));
		
		saveButton.setMinimumSize(new Dimension(100, 20));
		saveButton.setPreferredSize(new Dimension(100, 20));
		saveButton.setMaximumSize(new Dimension(100, 20));

		// Initialize Array Lists
		deleteMedalButtonArrayList = new ArrayList<JButton>();
		medalTextArrayList = new ArrayList<JTextArea>();
		medalNoHitArrayList = new ArrayList<JTextArea>();
		changeMedalButtonArrayList = new ArrayList<JButton>();
		
		createDeleteMedalButtons(medallionComboArrayList);
		medalTextCheckBoxArrayList = createMedalTextCheckBoxs(medallionComboArrayList, medalTextCheckBoxString);
		createMedalTextAreas(medallionComboArrayList);
		medalNoHitCheckBoxArrayList = createNoHitCheckBoxs(medallionComboArrayList, noHitCheckBoxString);
		createChangeMedalButtons(medallionComboArrayList);
		
		editMLMFPanel = new JPanel();
		editMLMFPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		editMLMFPanel.setOpaque(false);
		editMLMFPanel.setLayout(createBadgeGroupLayout(editMLMFPanel));
		
		setPanelDimensions(editMLMFPanel, new Dimension(1100, 50*(medallionComboArrayList.size()+ 2)));
		
		this.add(editMLMFPanel);
	}
	
	// Public Class Functions
	public void addNewMedallionCombo(MedallionCombo combo) {
		
		medallionComboArrayList.add(combo);
		
		// Create new MedalTextCheckBox
		JCheckBox medalTextCheckBox = new JCheckBox();
		
		medalTextCheckBox.setSelected(true);
		medalTextCheckBox.setMinimumSize(new Dimension(25, 20));
		medalTextCheckBox.setPreferredSize(new Dimension(25, 20));
		medalTextCheckBox.setMaximumSize(new Dimension(25, 20));
		
		medalTextCheckBox.addActionListener(this);
		medalTextCheckBox.setActionCommand(medalTextCheckBoxString);
		medalTextCheckBox.setName(combo.getMedallionButton().getActionCommand());
		
		medalTextCheckBoxArrayList.add(medalTextCheckBox);
		
		// Create new MedalTextArea
		JTextArea medalTextArea = new JTextArea(combo.getMedallionTextPane().getText());
		
		medalTextArea.setEditable(true);
		medalTextArea.setLineWrap(false);
		setJTextAreaDimensions(medalTextArea, 150, 300, 600, 20);
		
		medalTextArrayList.add(medalTextArea);
		
		// Create new NoHitTextCheckBox
		JCheckBox noHitTextCheckBox = new JCheckBox();
		
		noHitTextCheckBox.setSelected(true);
		noHitTextCheckBox.setMinimumSize(new Dimension(25, 20));
		noHitTextCheckBox.setPreferredSize(new Dimension(25, 20));
		noHitTextCheckBox.setMaximumSize(new Dimension(25, 20));
		
		noHitTextCheckBox.addActionListener(this);
		noHitTextCheckBox.setActionCommand(medalTextCheckBoxString);
		noHitTextCheckBox.setName(combo.getMedallionButton().getActionCommand());
		
		medalNoHitCheckBoxArrayList.add(noHitTextCheckBox);
		
		// Create new NoHitTextArea
		JTextArea noHitTextArea = new JTextArea(combo.getNoHitTextPane().getText());
		
		noHitTextArea.setEditable(true);
		noHitTextArea.setLineWrap(false);
		setJTextAreaDimensions(noHitTextArea, 40, 50, 100, 40);
		
		medalNoHitArrayList.add(noHitTextArea);
		
		// Create new ChangeButton
		JButton changeButton = new JButton();
		
		changeButton.setName(combo.getMedallionButton().getActionCommand());
		changeButton.setActionCommand(changeMedalButtonString);
		changeButton.setText("Import");
		changeButton.addActionListener(this);
		
		changeButton.setMinimumSize(new Dimension(50, 20));
		changeButton.setPreferredSize(new Dimension(50, 20));
		changeButton.setMaximumSize(new Dimension(50, 20));
		
		changeMedalButtonArrayList.add(changeButton);
		
		// Redo Panel Layout
		setPanelDimensions(editMLMFPanel, new Dimension(1000, 50*(medallionComboArrayList.size()+ 2)));
		editMLMFPanel.setLayout(createBadgeGroupLayout(editMLMFPanel));
		
		return;
	}
	
	// Private Class Functions
	private ArrayList<JCheckBox> createMedalTextCheckBoxs(ArrayList<MedallionCombo> medallionCombos, String namestr) {
		
		ArrayList<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
		
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			MedallionCombo currentMedallionCombo = medallionCombos.get(i);
			JCheckBox newCheckBox = new JCheckBox();
			
			newCheckBox.setSelected(currentMedallionCombo.getIncludeMedallionText());
			newCheckBox.setMinimumSize(new Dimension(25, 20));
			newCheckBox.setPreferredSize(new Dimension(25, 20));
			newCheckBox.setMaximumSize(new Dimension(25, 20));
			
			newCheckBox.addActionListener(this);
			newCheckBox.setActionCommand(namestr);
			newCheckBox.setName(currentMedallionCombo.getMedallionButton().getActionCommand());
			
			checkBoxs.add(newCheckBox);
		}
		
		return checkBoxs;
	}
	
	private ArrayList<JCheckBox> createNoHitCheckBoxs(ArrayList<MedallionCombo> medallionCombos, String namestr) {
		
		ArrayList<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
		
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			MedallionCombo currentMedallionCombo = medallionCombos.get(i);
			JCheckBox newCheckBox = new JCheckBox();
			
			newCheckBox.setSelected(currentMedallionCombo.getIncludeNoHitText());
			newCheckBox.setMinimumSize(new Dimension(25, 20));
			newCheckBox.setPreferredSize(new Dimension(25, 20));
			newCheckBox.setMaximumSize(new Dimension(25, 20));
			
			newCheckBox.addActionListener(this);
			newCheckBox.setActionCommand(namestr);
			newCheckBox.setName(currentMedallionCombo.getMedallionButton().getActionCommand());
			
			checkBoxs.add(newCheckBox);
		}
		
		return checkBoxs;
	}
	
	private void createMedalTextAreas(ArrayList<MedallionCombo> medallionCombos) {
				
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			MedallionCombo currentMedallionCombo = medallionCombos.get(i);
			JTextArea medalTextArea = new JTextArea(currentMedallionCombo.getMedallionTextPane().getText());
			
			medalTextArea.setEditable(true);
			medalTextArea.setLineWrap(false);
			setJTextAreaDimensions(medalTextArea, 150, 300, 600, 20);
			medalTextArea.setName(currentMedallionCombo.getMedallionButton().getActionCommand());
			
			medalTextArrayList.add(medalTextArea);
			
			JTextArea noHitTextArea = new JTextArea(currentMedallionCombo.getNoHitTextPane().getText());
			
			noHitTextArea.setEditable(true);
			noHitTextArea.setLineWrap(false);
			setJTextAreaDimensions(noHitTextArea, 40, 50, 100, 40);
			noHitTextArea.setName(currentMedallionCombo.getMedallionButton().getActionCommand());

			medalNoHitArrayList.add(noHitTextArea);
		}
		
		return;
	}
	
	private void createChangeMedalButtons(ArrayList<MedallionCombo> medallionCombos) {
		
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			MedallionCombo currentMedallionCombo = medallionCombos.get(i);
			JButton changeButton = new JButton();
			
			changeButton.setName(currentMedallionCombo.getMedallionButton().getActionCommand());
			changeButton.setActionCommand(changeMedalButtonString);
			changeButton.setText("Import");
			changeButton.addActionListener(this);
			
			changeButton.setMinimumSize(new Dimension(50, 20));
			changeButton.setPreferredSize(new Dimension(50, 20));
			changeButton.setMaximumSize(new Dimension(50, 20));
			
			changeMedalButtonArrayList.add(changeButton);
		}
		
		return;
	}
	
	private void createDeleteMedalButtons(ArrayList<MedallionCombo> medallionCombos) {
		
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			MedallionCombo currentMedallionCombo = medallionCombos.get(i);
			JButton deleteButton = new JButton();
			
			deleteButton.setName(currentMedallionCombo.getMedallionButton().getActionCommand());
			deleteButton.setActionCommand(deleteMedalString);
			deleteButton.setText("X");
			deleteButton.setForeground(Color.RED);
			deleteButton.addActionListener(this);
			deleteButton.setBorderPainted(false);
			deleteButton.setRolloverEnabled(false);
			deleteButton.setContentAreaFilled(false);
			deleteButton.setFocusPainted(false);
			deleteButton.setBorder(null);
			
			deleteButton.setMinimumSize(new Dimension(20, 20));
			deleteButton.setPreferredSize(new Dimension(30, 20));
			deleteButton.setMaximumSize(new Dimension(40, 20));
			
			deleteMedalButtonArrayList.add(deleteButton);
		}
		
		return;
	}
	
	private GroupLayout createBadgeGroupLayout(JPanel panel) {

		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(false);
		layout.setAutoCreateContainerGaps(false);
		
		////////////////////////////
		/*
		 *  Horizontal Alignment
		 */
		////////////////////////////
		ParallelGroup topLevelParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		
		SequentialGroup rowSequentialGroup = layout.createSequentialGroup();
		
		// Instead of renaming all the columns, adding the delete button to the front as a 'zero' column
		// ZEROTH COLUMN
		ParallelGroup zeroColumnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		zeroColumnParallelGroup.addComponent(deleteMedalHeader);
		
		// Iterate over the ArrayList to create horizontal layout groupings
		for (int i = 0; i < deleteMedalButtonArrayList.size(); i++) {
							
			zeroColumnParallelGroup.addComponent(deleteMedalButtonArrayList.get(i));			
		}
		
		rowSequentialGroup.addGroup(zeroColumnParallelGroup);
		rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15);
		
		// FIRST COLUMN
		ParallelGroup firstColumnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		firstColumnParallelGroup.addComponent(medalTextCheckBoxHeader);
		
		// Iterate over the ArrayList to create horizontal layout groupings
		for (int i = 0; i < medalTextCheckBoxArrayList.size(); i++) {
							
			firstColumnParallelGroup.addComponent(medalTextCheckBoxArrayList.get(i));			
		}
		
		rowSequentialGroup.addGroup(firstColumnParallelGroup);
		rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15);
		
		// SECOND COLUMN
		ParallelGroup secondColumnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		secondColumnParallelGroup.addComponent(medalTextHeader);
		
		// Iterate over the ArrayList to create next parallel grouping
		for (int i = 0; i < medalTextArrayList.size(); i++) {
							
			secondColumnParallelGroup.addComponent(medalTextArrayList.get(i));			
		}
		
		rowSequentialGroup.addGroup(secondColumnParallelGroup);
		rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15);
		
		// THIRD COLUMN
		ParallelGroup thirdColumnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		thirdColumnParallelGroup.addComponent(medalNoHitCheckBoxHeader);
		
		// Iterate over the ArrayList to create next parallel grouping
		for (int i = 0; i < medalNoHitCheckBoxArrayList.size(); i++) {
							
			thirdColumnParallelGroup.addComponent(medalNoHitCheckBoxArrayList.get(i));			
		}
		
		rowSequentialGroup.addGroup(thirdColumnParallelGroup);
		rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15);
		
		// FOURTH COLUMN
		ParallelGroup fourthColumnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		fourthColumnParallelGroup.addComponent(medalNoHitHeader);
		
		// Iterate over the ArrayList to create next parallel grouping
		for (int i = 0; i < medalNoHitArrayList.size(); i++) {
							
			fourthColumnParallelGroup.addComponent(medalNoHitArrayList.get(i));			
		}
		
		rowSequentialGroup.addGroup(fourthColumnParallelGroup);
		rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15);
		
		// FIFTH COLUMN
		ParallelGroup fifthColumnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		fifthColumnParallelGroup.addComponent(changeMedalHeader);
		
		// Iterate over the ArrayList to create next parallel grouping
		for (int i = 0; i < changeMedalButtonArrayList.size(); i++) {
							
			fifthColumnParallelGroup.addComponent(changeMedalButtonArrayList.get(i));			
		}
		
		rowSequentialGroup.addGroup(fifthColumnParallelGroup);
		rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15);
		
		/* 
		 * Add Current Medallion Layout Group and AddMedallionButton/SaveButton Layout Group
		 * to TopLevelParallelGroup
		 */
		topLevelParallelGroup.addGroup(rowSequentialGroup);
		
		SequentialGroup buttonSequentialGroup = layout.createSequentialGroup();
		
		buttonSequentialGroup.addComponent(addNewMedalButton);
		buttonSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, 50);
		buttonSequentialGroup.addComponent(saveButton);

		topLevelParallelGroup.addGroup(buttonSequentialGroup);
		
		// Finalize Horizontal Layout
		layout.setHorizontalGroup(topLevelParallelGroup);
		
		///////////////////////////
		/*
		 *  Vertical Alignment
		 */
		///////////////////////////
		SequentialGroup columnSequentialGroup = layout.createSequentialGroup();
		
		// Set Layout for Header
		ParallelGroup headerParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		
		headerParallelGroup.addComponent(deleteMedalHeader);
		headerParallelGroup.addComponent(medalTextCheckBoxHeader);
		headerParallelGroup.addComponent(medalTextHeader);
		headerParallelGroup.addComponent(medalNoHitCheckBoxHeader);
		headerParallelGroup.addComponent(medalNoHitHeader);
		headerParallelGroup.addComponent(changeMedalHeader);
		
		columnSequentialGroup.addGroup(headerParallelGroup);
		columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 15, 15);
		
		// Iterate over the ArrayLists to create vertical layout groupings
		for (int i = 0; i < medalTextCheckBoxArrayList.size(); i++) {
			
			ParallelGroup rowParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			rowParallelGroup.addComponent(deleteMedalButtonArrayList.get(i));
			rowParallelGroup.addComponent(medalTextCheckBoxArrayList.get(i));
			rowParallelGroup.addComponent(medalTextArrayList.get(i));
			rowParallelGroup.addComponent(medalNoHitCheckBoxArrayList.get(i));
			rowParallelGroup.addComponent(medalNoHitArrayList.get(i));
			rowParallelGroup.addComponent(changeMedalButtonArrayList.get(i));
						
			columnSequentialGroup.addGroup(rowParallelGroup);
			columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 5, 5);
		}
		
		// Create AddMedallionButton/SaveButton Layout Group 
		ParallelGroup buttonParallelGroup = layout.createParallelGroup();
		
		buttonParallelGroup.addComponent(addNewMedalButton);
		buttonParallelGroup.addComponent(saveButton);
		
		columnSequentialGroup.addGroup(buttonParallelGroup);

		// Finalize Vertical Layout
		layout.setVerticalGroup(columnSequentialGroup);
		
		return layout;
	}
	
	private void changeMedallionComboImage(MedallionCombo combo, File imgFile, Point comboPoint) {
		
		BufferedImage changedImage = openImageFile(imgFile);
		
		CreateImageShadow changedImageShadow = new CreateImageShadow(changedImage, parentFrame.getBackgroundLabelImage(),
																	 comboPoint, shadowLengthMultiplier);
		
		combo.setMedallionBImage(changedImageShadow.getCompleteImage());
		combo.setMedallionGSBImage(changedImageShadow.getCompleteShadowImage());
		
		combo.frameResizeEvent();
		
		return;
	}
	
	private void deleteMedallionCombo(JButton delButton) {
		
		for (int i = 0; i < deleteMedalButtonArrayList.size(); i++) {

			if (delButton.getName() == deleteMedalButtonArrayList.get(i).getName()) {
				editMLMFPanel.remove(deleteMedalButtonArrayList.get(i));
				editMLMFPanel.getLayout().removeLayoutComponent(deleteMedalButtonArrayList.get(i));
				deleteMedalButtonArrayList.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < medalTextCheckBoxArrayList.size(); i++) {

			if (delButton.getName() == medalTextCheckBoxArrayList.get(i).getName()) {
				editMLMFPanel.remove(medalTextCheckBoxArrayList.get(i));
				editMLMFPanel.getLayout().removeLayoutComponent(medalTextCheckBoxArrayList.get(i));
				medalTextCheckBoxArrayList.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < medalTextArrayList.size(); i++) {

			if (delButton.getName() == medalTextArrayList.get(i).getName()) {
				editMLMFPanel.remove(medalTextArrayList.get(i));
				editMLMFPanel.getLayout().removeLayoutComponent(medalTextArrayList.get(i));
				medalTextArrayList.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < medalNoHitCheckBoxArrayList.size(); i++) {

			if (delButton.getName() == medalNoHitCheckBoxArrayList.get(i).getName()) {
				editMLMFPanel.remove(medalNoHitCheckBoxArrayList.get(i));
				editMLMFPanel.getLayout().removeLayoutComponent(medalNoHitCheckBoxArrayList.get(i));
				medalNoHitCheckBoxArrayList.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < medalNoHitArrayList.size(); i++) {

			if (delButton.getName() == medalNoHitArrayList.get(i).getName()) {
				editMLMFPanel.remove(medalNoHitArrayList.get(i));
				editMLMFPanel.getLayout().removeLayoutComponent(medalNoHitArrayList.get(i));
				medalNoHitArrayList.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < changeMedalButtonArrayList.size(); i++) {

			if (delButton.getName() == changeMedalButtonArrayList.get(i).getName()) {
				editMLMFPanel.remove(changeMedalButtonArrayList.get(i));
				editMLMFPanel.getLayout().removeLayoutComponent(changeMedalButtonArrayList.get(i));
				changeMedalButtonArrayList.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < medallionComboArrayList.size(); i++) {
			
			if (delButton.getName() == medallionComboArrayList.get(i).getMedallionActionCommandString()) {
				parentFrame.removeMedallionComboFromPanel(medallionComboArrayList.get(i));
				medallionComboArrayList.remove(i);
				i--;
			}
		}
		
		editMLMFPanel.revalidate();
		editMLMFPanel.repaint();
		//editMLMFPanel.setLayout(createBadgeGroupLayout(editMLMFPanel));
		//parentFrame.refreshCasePanelLayout();
		
		return;
	}
	
	private BufferedImage openImageFile(File file) {
		
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
	
	private void setPanelDimensions(JPanel panel, Dimension dim) {
		
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);
		
		return;
	}
	
	private void setJTextAreaDimensions(JTextArea textArea, int minwidth, int prefwidth, int maxwidth, int height) {
		
		textArea.setMinimumSize(new Dimension(minwidth, height));
		textArea.setPreferredSize(new Dimension(prefwidth, height));
		textArea.setMaximumSize(new Dimension(maxwidth, height));
		
		return;
	}
	
	private void setJTextPaneDimensions(JTextPane textPane, int minwidth, int prefwidth, int maxwidth, int height) {
		
		textPane.setMinimumSize(new Dimension(minwidth, height));
		textPane.setPreferredSize(new Dimension(prefwidth, height));
		textPane.setMaximumSize(new Dimension(maxwidth, height));
		
		return;
	}
	
	private void setTextPaneAttributes(noHitMedallionBoard.Alignment align, JTextPane pane, String paneText,
			boolean editable, boolean bold) {

		// Create Alignment
		SimpleAttributeSet alignment = new SimpleAttributeSet();
		
		if (align == noHitMedallionBoard.Alignment.RIGHT) {
			StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_RIGHT);
		}
		else if (align == noHitMedallionBoard.Alignment.CENTER) {
			StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_CENTER);
		}
		else if (align == noHitMedallionBoard.Alignment.LEFT) {
			StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_LEFT);
		}
		else if (align == noHitMedallionBoard.Alignment.JUSTIFIED) {
			StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_JUSTIFIED);
		}
		
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attributeSet, Color.black);
		StyleConstants.setBackground(attributeSet, new Color(1.0f, 1.0f, 1.0f, 0.0f));	
		StyleConstants.setBold(attributeSet, bold);
		pane.setCharacterAttributes(attributeSet, true);
		pane.setText(paneText);
		pane.setOpaque(false);
		pane.setEditable(editable);
		
		StyledDocument paneDoc = pane.getStyledDocument();
		paneDoc.setParagraphAttributes(0, paneDoc.getLength(), alignment, false);
		
		return;
	}

	// Implement Override Functions
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == medalTextCheckBoxString) {
			JCheckBox source = (JCheckBox) e.getSource();
			
			for (int i = 0; i < medallionComboArrayList.size(); i++) {
				
				if (source.getName() == medallionComboArrayList.get(i).getMedallionButton().getActionCommand()) {
					
					if (source.isSelected()) {
						medallionComboArrayList.get(i).setIncludeMedallionText(true);
					}
					else {
						medallionComboArrayList.get(i).setIncludeMedallionText(false);
					}
				}
			}
		}
		else if (e.getActionCommand() == noHitCheckBoxString) {
			JCheckBox source = (JCheckBox) e.getSource();

			for (int i = 0; i < medallionComboArrayList.size(); i++) {
				
				if (source.getName() == medallionComboArrayList.get(i).getMedallionButton().getActionCommand()) {
					
					if (source.isSelected()) {
						medallionComboArrayList.get(i).setIncludeNoHitText(true);
					}
					else {
						medallionComboArrayList.get(i).setIncludeNoHitText(false);
					}
				}
			}
		}
		else if (e.getActionCommand() == changeMedalButtonString) {
			JButton source = (JButton) e.getSource(); 
			
			for (int i = 0; i < medallionComboArrayList.size(); i++) {
				
				MedallionCombo combo = medallionComboArrayList.get(i);
				
				if (source.getName() == combo.getMedallionButton().getActionCommand()) {
					
					JFileChooser jfc = new JFileChooser();
				    jfc.showDialog(null,"Please Select the Image File");
				    jfc.setVisible(true);
				    
				    if (jfc.getSelectedFile() == null) {
				    	return;
				    }
				    
				    File file = jfc.getSelectedFile();

				    Dimension bgDimension = parentFrame.getBadgePanelDimension();
				    ArrayList<Point> pointArrayList = parentFrame.calculateMedallionLocations(medallionComboArrayList.size(),
				    																		  bgDimension.width, bgDimension.height);
				    
					changeMedallionComboImage(combo, file, pointArrayList.get(i));
				}
			}
		}
		else if (e.getActionCommand() == deleteMedalString) {
			JButton source = (JButton) e.getSource(); 

			deleteMedallionCombo(source);
		}
		else if (e.getSource() == addNewMedalButton) {
			
			Dimension dim = parentFrame.getBadgePanelDimension();
			ArrayList<Point> pts = parentFrame.calculateMedallionLocations(medallionComboArrayList.size() + 1, dim.width, dim.height);
			Point pt = pts.get(pts.size() - 1);
			
			CreateNewMedallionComboFrame cnmcFrame = new CreateNewMedallionComboFrame(this, pt, parentFrame.getBackgroundLabelImage());
			cnmcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			cnmcFrame.setTitle("Add New Medal");
			cnmcFrame.pack();
			cnmcFrame.setAlwaysOnTop(false);
			cnmcFrame.setVisible(true);
		}
		else if (e.getSource() == saveButton) {
						
			for (int i = 0; i < medallionComboArrayList.size(); i++) {
				
				MedallionCombo currentCombo = medallionComboArrayList.get(i);
				
				currentCombo.getMedallionTextPane().setVisible(currentCombo.getIncludeMedallionText());
				currentCombo.getNoHitTextPane().setVisible(currentCombo.getIncludeNoHitText());
				currentCombo.getMedallionTextPane().setText(medalTextArrayList.get(i).getText());
				currentCombo.getNoHitTextPane().setText(medalNoHitArrayList.get(i).getText());
			}
			
			parentFrame.setMedallionArrayList(medallionComboArrayList);

			parentFrame.saveEditMedalListMenuFrame();
		}
		
		return;
	}
	
}
