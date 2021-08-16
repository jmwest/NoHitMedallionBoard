package noHitMedallionBoard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class EditMedalListMenuFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1061822005019075338L;

	private JPanel editMLMFPanel;
	private JTextArea medalTextCheckBoxHeader;
	private JTextArea medalTextHeader;
	private JTextArea medalNoHitCheckBoxHeader;
	private JTextArea medalNoHitHeader;
	private JTextArea changeMedalHeader;
	private ArrayList<JCheckBox> medalTextCheckBoxLArrayList;
	private ArrayList<JTextArea> medalTextArrayList;
	private ArrayList<JCheckBox> medalNoHitCheckBoxArrayList;
	private ArrayList<JTextArea> medalNoHitArrayList;
	private ArrayList<JButton> changeMedalButtonArrayList;
	private JButton addNewMedalButton;
	private JButton saveButton;
	
	private JButton importImageButton;
	
	public EditMedalListMenuFrame(ArrayList<MedallionCombo> medallionCombos) {
		
		medalTextCheckBoxHeader = new JTextArea("Include Title Text");
		medalTextHeader = new JTextArea("Edit Title Text");
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
		
		setJTextAreaDimensions(medalTextCheckBoxHeader, 100, 100, 150);
		setJTextAreaDimensions(medalTextHeader, 100, 100, 150);
		setJTextAreaDimensions(medalNoHitCheckBoxHeader, 100, 100, 150);
		setJTextAreaDimensions(medalNoHitHeader, 100, 100, 150);
		setJTextAreaDimensions(changeMedalHeader, 100, 100, 150);

		
		medalTextArrayList = new ArrayList<JTextArea>();
		medalNoHitArrayList = new ArrayList<JTextArea>();
		
		medalTextCheckBoxLArrayList = createMedalCheckBoxs(medallionCombos);
		createMedalTextAreas(medallionCombos);
		medalNoHitCheckBoxArrayList = createMedalCheckBoxs(medallionCombos);
		
		editMLMFPanel = new JPanel();
		editMLMFPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		editMLMFPanel.setOpaque(false);
		editMLMFPanel.setLayout(createBadgeGroupLayout(editMLMFPanel));
		
		setPanelDimensions(editMLMFPanel, new Dimension(800, 30*(medallionCombos.size()+ 1)));
		
		this.add(editMLMFPanel);
	}
	
	// 
	private ArrayList<JCheckBox> createMedalCheckBoxs(ArrayList<MedallionCombo> medallionCombos) {
		
		ArrayList<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
		
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			JCheckBox newCheckBox = new JCheckBox();
			
			newCheckBox.setSelected(true);
			newCheckBox.setMinimumSize(new Dimension(25, 20));
			newCheckBox.setPreferredSize(new Dimension(25, 20));
			newCheckBox.setMaximumSize(new Dimension(25, 20));
			
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
			setJTextAreaDimensions(medalTextArea, 150, 300, 600);
			
			medalTextArrayList.add(medalTextArea);
			
			JTextArea noHitTextArea = new JTextArea(currentMedallionCombo.getNoHitTextPane().getText());
			
			noHitTextArea.setEditable(true);
			noHitTextArea.setLineWrap(false);
			setJTextAreaDimensions(noHitTextArea, 100, 150, 300);
			
			medalNoHitArrayList.add(noHitTextArea);
		}
		
		return;
	}
	
	private GroupLayout createBadgeGroupLayout(JPanel panel) {
		
		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(false);
		layout.setAutoCreateContainerGaps(false);
		
		ParallelGroup columnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		
		// Iterate over the ArrayList to create horizontal layout groupings
		for (int i = 0; i < checkBoxs.size(); i++) {
			
			SequentialGroup rowSequentialGroup = layout.createSequentialGroup();
				
			rowSequentialGroup.addComponent(checkBoxs.get(i));
			rowSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, 15);
			rowSequentialGroup.addComponent(textAreas.get(i));
			
			columnParallelGroup.addGroup(rowSequentialGroup);
		}
		
		layout.setHorizontalGroup(columnParallelGroup);
		
		SequentialGroup columnSequentialGroup = layout.createSequentialGroup();
		
		// Iterate over the ArrayList to create vertical layout groupings
		for (int i = 0; i < checkBoxs.size(); i++) {
			
			ParallelGroup rowParallelGroup = layout.createParallelGroup(Alignment.CENTER);
			
			rowParallelGroup.addComponent(checkBoxs.get(i));
			rowParallelGroup.addComponent(textAreas.get(i));
						
			columnSequentialGroup.addGroup(rowParallelGroup);
			columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 5, 5);
		}
		
		layout.setVerticalGroup(columnSequentialGroup);
		
		return layout;
	}
	
	private void setPanelDimensions(JPanel panel, Dimension dim) {
		
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);
		
		return;
	}
	
	private void setJTextAreaDimensions(JTextArea textArea, int minwidth, int prefwidth, int maxwidth) {
		
		textArea.setMinimumSize(new Dimension(minwidth, 20));
		textArea.setPreferredSize(new Dimension(prefwidth, 20));
		textArea.setMaximumSize(new Dimension(maxwidth, 20));
		
		return;
	}

	// Implement Override Functions
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
