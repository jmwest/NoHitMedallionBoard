package noHitMedallionBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CreateNewMedallionComboFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9125090285533184434L;

	private EditMedalListMenuFrame parentFrame;
	private BufferedImage parentbgImage;
	private Point addMedalPoint;
	
	private JPanel createNMCFPanel;
	private JTextPane medalNameHeader;
	private JTextArea medalNameTextArea;
	private JTextPane medalNoHitHeader;
	private JTextArea medalNoHitTextArea;
	private JButton addNewMedalButton;
	private JButton saveButton;
	
	private BufferedImage medalBImage;
	private BufferedImage medalGSBImage;
	
	public CreateNewMedallionComboFrame(EditMedalListMenuFrame parent, Point medalPoint, BufferedImage bgImage) {
		
		parentFrame = parent;
		addMedalPoint = medalPoint;
		parentbgImage = bgImage;
		
		// Initialize and set TextAreas
		medalNameHeader = new JTextPane();
		medalNameTextArea = new JTextArea("");
		medalNoHitHeader = new JTextPane();
		medalNoHitTextArea = new JTextArea("No Hit\nAny %");
		
		medalNameTextArea.setEditable(true);
		medalNameTextArea.setLineWrap(false);
		
		medalNoHitTextArea.setEditable(true);
		medalNoHitTextArea.setLineWrap(false);
		
		setJTextAreaDimensions(medalNameTextArea, 150, 300, 600, 20);
		setJTextAreaDimensions(medalNoHitTextArea, 40, 50, 100, 40);
		
		//
		medalNameHeader.setMinimumSize(new Dimension(150, 20));
		medalNameHeader.setPreferredSize(new Dimension(150, 20));
		medalNameHeader.setMaximumSize(new Dimension(150, 20));
		
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attributeSet, Color.black);
		StyleConstants.setBackground(attributeSet, new Color(1.0f, 1.0f, 1.0f, 0.0f));	
		
		medalNameHeader.setCharacterAttributes(attributeSet, true);
		medalNameHeader.setText("New Medal Name");
		medalNameHeader.setOpaque(false);
		medalNameHeader.setEditable(false);
		
		StyledDocument medallionTextdoc = medalNameHeader.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		medallionTextdoc.setParagraphAttributes(0, medallionTextdoc.getLength(), center, false);
		
		medalNoHitHeader.setMinimumSize(new Dimension(150, 20));
		medalNoHitHeader.setPreferredSize(new Dimension(150, 20));
		medalNoHitHeader.setMaximumSize(new Dimension(150, 20));
		
		medalNoHitHeader.setCharacterAttributes(attributeSet, true);
		medalNoHitHeader.setText("Challenge Name");
		medalNoHitHeader.setOpaque(false);
		medalNoHitHeader.setEditable(false);
		
		StyledDocument noHitTextdoc = medalNoHitHeader.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		noHitTextdoc.setParagraphAttributes(0, noHitTextdoc.getLength(), center, false);
		
		// Initialize and Set Save and AddNewMedal Buttons        
		addNewMedalButton = new JButton("Import Image");
		saveButton = new JButton("Save New Medal");
				
		addNewMedalButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		addNewMedalButton.setMinimumSize(new Dimension(100, 100));
		addNewMedalButton.setPreferredSize(new Dimension(100, 100));
		addNewMedalButton.setMaximumSize(new Dimension(100, 100));
		
		saveButton.setMinimumSize(new Dimension(100, 20));
		saveButton.setPreferredSize(new Dimension(100, 20));
		saveButton.setMaximumSize(new Dimension(100, 20));

		// Initialize Panel		
		createNMCFPanel = new JPanel();
		createNMCFPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		createNMCFPanel.setOpaque(false);
		createNMCFPanel.setLayout(createLayout(createNMCFPanel));
		
		setPanelDimensions(createNMCFPanel, new Dimension(350, 370));
		
		this.add(createNMCFPanel);
	}
	
	// Public Class Functions
	
	
	// Private Class Functions
	private void setJTextAreaDimensions(JTextArea textArea, int minwidth, int prefwidth, int maxwidth, int height) {
		
		textArea.setMinimumSize(new Dimension(minwidth, height));
		textArea.setPreferredSize(new Dimension(prefwidth, height));
		textArea.setMaximumSize(new Dimension(maxwidth, height));
		
		return;
	}
	
	private void setPanelDimensions(JPanel panel, Dimension dim) {
		
		panel.setMinimumSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);
		
		return;
	}
	
	private GroupLayout createLayout(JPanel panel) {
		
		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(false);
		layout.setAutoCreateContainerGaps(false);
		
		ParallelGroup columnParallelGroup = layout.createParallelGroup(Alignment.CENTER);
		
		columnParallelGroup.addComponent(medalNameHeader);
		columnParallelGroup.addComponent(medalNameTextArea);
		columnParallelGroup.addComponent(addNewMedalButton);
		columnParallelGroup.addComponent(medalNoHitHeader);
		columnParallelGroup.addComponent(medalNoHitTextArea);
		columnParallelGroup.addComponent(saveButton);
		
		layout.setHorizontalGroup(columnParallelGroup);
		
		SequentialGroup columnSequentialGroup = layout.createSequentialGroup();

		columnSequentialGroup.addComponent(medalNameHeader);
		columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 5, 5);

		columnSequentialGroup.addComponent(medalNameTextArea);
		columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 40, 40);

		columnSequentialGroup.addComponent(addNewMedalButton);
		columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 40, 40);

		columnSequentialGroup.addComponent(medalNoHitHeader);
		columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 5, 5);

		columnSequentialGroup.addComponent(medalNoHitTextArea);
		columnSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 50, 50);

		columnSequentialGroup.addComponent(saveButton);
				
		layout.setVerticalGroup(columnSequentialGroup);
		
		return layout;
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
	
	private void addMedallionImage(File imgFile) {
		
		BufferedImage changedImage = openImageFile(imgFile);
		
		CreateImageShadow createImageShadow = new CreateImageShadow(changedImage, parentbgImage,
																	 addMedalPoint, 0.02f);
		
		medalBImage = createImageShadow.getCompleteImage();
		medalGSBImage = createImageShadow.getCompleteShadowImage();
		
		return;
	}
	
	private ImageIcon getScaledIcon(BufferedImage srcImg, JButton button) {
		
		Dimension d = button.getSize();
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

	// Implement Overridden Functions
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == addNewMedalButton) {
			
			JFileChooser jfc = new JFileChooser();
		    jfc.showDialog(null,"Please Select the Image File");
		    jfc.setVisible(true);
		    System.out.println("File chooser visible.");
		    
		    if (jfc.getSelectedFile() == null) {
		    	return;
		    }
		    
		    File file = jfc.getSelectedFile();
		    System.out.println("File name " + file.getName());
		    
		    addMedallionImage(file);
		    
		    addNewMedalButton.setIcon(getScaledIcon(medalBImage, addNewMedalButton));
		    
		    addNewMedalButton.setBorderPainted(false);
		    addNewMedalButton.setRolloverEnabled(false);
		    addNewMedalButton.setContentAreaFilled(false);
		    addNewMedalButton.setBorderPainted(false);
		    addNewMedalButton.setFocusPainted(false);
		}
		else if (e.getSource() == saveButton) {
			
			MedallionCombo medcombo = new MedallionCombo(medalNameTextArea.getText(), medalBImage,
														 medalGSBImage, medalNoHitTextArea.getText());
			
			parentFrame.addNewMedallionCombo(medcombo);
			
			// Exit CreateNewMedallionComboFrame
			setVisible(false);
			dispose();
		}
		
		return;
	}
}
