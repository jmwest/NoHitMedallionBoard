package noHitMedallionBoard;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CreateNewMedallionComboFrame {

	private EditMedalListMenuFrame parentFrame;
	
	private JPanel editMLMFPanel;
	private JTextArea medalNameHeader;
	private JTextArea medalNameTextArea;
	private JTextArea medalNoHitCheckBoxHeader;
	private JTextArea medalNoHitHeader;
	private JTextArea changeMedalHeader;
	private JButton addNewMedalButton;
	private JButton saveButton;
	
	public CreateNewMedallionComboFrame(EditMedalListMenuFrame parent) {
		
		parentFrame = parent;
		
		// Initialize and set header TextAreas
		medalNameHeader = new JTextArea("New Medal Name");
		medalNameTextArea = new JTextArea("");
		medalNoHitCheckBoxHeader = new JTextArea("Include Challenge Text");
		medalNoHitHeader = new JTextArea("Edit Challenge Text");
		changeMedalHeader = new JTextArea("Change Medal Image");
		
		medalTextCheckBoxHeader.setEditable(false);
		medalTextCheckBoxHeader.setLineWrap(false);
		
		medalTextHeader.setEditable(false);
		medalTextHeader.setLineWrap(false);
		
		medalNoHitCheckBoxHeader.setEditable(false);
		medalNoHitCheckBoxHeader.setLineWrap(false);
		
		medalNoHitHeader.setEditable(false);
		medalNoHitHeader.setLineWrap(false);
		
		changeMedalHeader.setEditable(false);
		changeMedalHeader.setLineWrap(false);
		
		setJTextAreaDimensions(medalTextCheckBoxHeader, 100, 150, 200, 20);
		setJTextAreaDimensions(medalTextHeader, 100, 150, 200, 20);
		setJTextAreaDimensions(medalNoHitCheckBoxHeader, 150, 150, 200, 20);
		setJTextAreaDimensions(medalNoHitHeader, 100, 150, 200, 20);
		setJTextAreaDimensions(changeMedalHeader, 100, 150, 200, 20);
		
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
		medalTextArrayList = new ArrayList<JTextArea>();
		medalNoHitArrayList = new ArrayList<JTextArea>();
		changeMedalButtonArrayList = new ArrayList<JButton>();
		
		medalTextCheckBoxArrayList = createMedalCheckBoxs(medallionComboArrayList, medalTextCheckBoxString);
		createMedalTextAreas(medallionComboArrayList);
		medalNoHitCheckBoxArrayList = createMedalCheckBoxs(medallionComboArrayList, noHitCheckBoxString);
		createChangeMedalButtons(medallionComboArrayList);
		
		editMLMFPanel = new JPanel();
		editMLMFPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		editMLMFPanel.setOpaque(false);
		editMLMFPanel.setLayout(createBadgeGroupLayout(editMLMFPanel));
		
		setPanelDimensions(editMLMFPanel, new Dimension(1000, 50*(medallionComboArrayList.size()+ 2)));
		
		this.add(editMLMFPanel);
	}
}
