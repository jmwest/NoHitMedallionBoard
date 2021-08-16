package noHitMedallionBoard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private ArrayList<JCheckBox> medalCheckBoxLArrayList;
	private ArrayList<JTextArea> medalTextArrayList;
	private JButton saveButton;
	
	private JButton importImageButton;
	
	public EditMedalListMenuFrame(ArrayList<MedallionCombo> medallionCombos) {
		
		medalCheckBoxLArrayList = createMedalCheckBoxs(medallionCombos);
		medalTextArrayList = createMedalTextAreas(medallionCombos);
		
		editMLMFPanel = new JPanel();
		editMLMFPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		editMLMFPanel.setOpaque(false);
		editMLMFPanel.setLayout(createBadgeGroupLayout(editMLMFPanel, medalCheckBoxLArrayList, medalTextArrayList));
		
		setPanelDimensions(editMLMFPanel, new Dimension(650, 30*(medallionCombos.size()+ 1)));
		
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
	
	private ArrayList<JTextArea> createMedalTextAreas(ArrayList<MedallionCombo> medallionCombos) {
		
		ArrayList<JTextArea> textAreas = new ArrayList<JTextArea>();
		
		for (int i = 0; i < medallionCombos.size(); i++) {
			
			MedallionCombo currentMedallionCombo = medallionCombos.get(i);
			JTextArea newTextArea = new JTextArea(currentMedallionCombo.getMedallionTextPane().getText());
			
			newTextArea.setEditable(true);
			newTextArea.setLineWrap(false);
			newTextArea.setMinimumSize(new Dimension(150, 20));
			newTextArea.setPreferredSize(new Dimension(300, 20));
			newTextArea.setMaximumSize(new Dimension(600, 20));
			
			textAreas.add(newTextArea);
		}
		
		return textAreas;
	}
	
	private GroupLayout createBadgeGroupLayout(JPanel panel, ArrayList<JCheckBox> checkBoxs, ArrayList<JTextArea> textAreas) {
		
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

	// Implement Override Functions
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
