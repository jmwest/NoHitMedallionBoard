package noHitMedallionBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MedallionCombo implements ActionListener {

    final static int defaultButtonSize=100;
	
	private JButton medallionButton;
	private JTextPane medallionText;
	private JTextPane noHitTextPane;
	
	private Boolean includeMedallionText = true;
	private Boolean includeNoHitText = true;
	
	private float shadowLengthMultiplier = 0.02f;
	private int buttonSize = defaultButtonSize;
	
	// Boolean to keep track of whether the image is gray scale (GS) or not.
	private Boolean medallionBGS = true;
	private String medallionButtonActionCommandStr;
	
	/*  Need to make functionality later that allows user to:
	 *  [] upload an image,
	 *  [] save the image,
	 *  [Complete] then make a gray scale version of the image to use on the button.
	 */ 
	private BufferedImage medallionGSBImage;
	private BufferedImage medallionBImage;
	
	public MedallionCombo(String medStr, String imgSrcStr, BufferedImage backGroundImage, Point location) {
		
		medallionText = new JTextPane();
		setMedallionButton(new JButton());
		noHitTextPane = new JTextPane();
		
		getMedallionButton().addActionListener(this);
		getMedallionButton().setBorderPainted(false);
		getMedallionButton().setRolloverEnabled(false);
		getMedallionButton().setContentAreaFilled(false);
		getMedallionButton().setBorderPainted(false);
		getMedallionButton().setFocusPainted(false);
		
		//
		medallionText.setEditable(false);
		
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attributeSet, Color.white);
		StyleConstants.setBackground(attributeSet, new Color(1.0f, 1.0f, 1.0f, 0.0f));		
		medallionText.setCharacterAttributes(attributeSet, true);
		medallionText.setText(medStr);
		medallionText.setOpaque(false);
		medallionText.setEditable(false);
		
		StyledDocument medallionTextdoc = medallionText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		medallionTextdoc.setParagraphAttributes(0, medallionTextdoc.getLength(), center, false);
		
		//
		medallionButtonActionCommandStr = "change " + medStr + " button";
		getMedallionButton().setActionCommand(medallionButtonActionCommandStr);
		
		// Handle image file
		medallionBImage = openImageFile(imgSrcStr);

		CreateImageShadow iShadow = new CreateImageShadow(medallionBImage, backGroundImage,
														   location, shadowLengthMultiplier);
		
		medallionBImage = iShadow.getCompleteImage();
		medallionGSBImage = iShadow.getCompleteShadowImage();
		
		getMedallionButton().setMinimumSize(new Dimension(buttonSize, buttonSize));
		getMedallionButton().setPreferredSize(new Dimension(buttonSize, buttonSize));
		getMedallionButton().setMaximumSize(new Dimension(buttonSize, buttonSize));
		
		getMedallionTextPane().setMinimumSize(new Dimension(150, 30));
		getMedallionTextPane().setPreferredSize(new Dimension(150, 40));
		getMedallionTextPane().setMaximumSize(new Dimension(150, 60));
		
		ImageIcon medallionIcon = getScaledIcon(medallionGSBImage, getMedallionButton());
		getMedallionButton().setIcon(medallionIcon);
		getMedallionButton().setPressedIcon(medallionIcon);
		
		//
		attributeSet = new SimpleAttributeSet();
		StyleConstants.setBold(attributeSet, true);
		StyleConstants.setForeground(attributeSet, Color.white);
		StyleConstants.setBackground(attributeSet, new Color(1.0f, 1.0f, 1.0f, 0.0f));		
		noHitTextPane.setCharacterAttributes(attributeSet, true);
		noHitTextPane.setText("No Hit");
		noHitTextPane.setOpaque(false);
		noHitTextPane.setEditable(false);
		
		StyledDocument noHitdoc = noHitTextPane.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		noHitdoc.setParagraphAttributes(0, noHitdoc.getLength(), center, false);
	}
	
	// Public MedallionCombo functions
	public void frameResizeEvent() {
		if (medallionBGS) {
			changeButtonImage(getMedallionButton(), medallionGSBImage);
		}
		else {
			changeButtonImage(getMedallionButton(), medallionBImage);
		}
		
		return;
	}
	
	// Private MedallionCombo functions
	private BufferedImage openImageFile(String filename) {
		
		File imageFile = new File(filename);
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		
		try {
			System.out.println("Canonical path of target image: " + imageFile.getCanonicalPath());
            if (!imageFile.exists()) {
                System.out.println("file " + imageFile + " does not exist");
            }
            image = ImageIO.read(imageFile);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		return image;
	}
	
	private void changeButtonImage(JButton button, BufferedImage bimg) {
		ImageIcon scldicon = getScaledIcon(bimg, button);
		button.setIcon(scldicon);
		button.setPressedIcon(scldicon);
		
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
	
	// Implement inherited functions
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == medallionButtonActionCommandStr) {
			if (medallionBGS) {
				medallionBGS = false;
				
				JButton bSource = (JButton) e.getSource();
				changeButtonImage(bSource, medallionBImage);
			}
			else {
				medallionBGS = true;
				
				JButton bSource = (JButton) e.getSource();
				changeButtonImage(bSource, medallionGSBImage);
			}
		}
		
		return;
	}

	// Implement getter and setter functions
	public JButton getMedallionButton() {
		return medallionButton;
	}

	public void setMedallionButton(JButton medallionButton) {
		this.medallionButton = medallionButton;
		
		return;
	}
	
	public JTextPane getMedallionTextPane() {
		return medallionText;
	}

	public void setMedallionTextArea(JTextPane medallionTextPane) {
		this.medallionText = medallionTextPane;
		
		return;
	}
	
	public JTextPane getNoHitTextPane() {
		return noHitTextPane;
	}

	public void setNoHitTextPane(JTextPane noHitTextPane) {
		this.noHitTextPane = noHitTextPane;
		
		return;
	}
	
	public int getButtonSize() {
		return buttonSize;
	}

	public void setButtonSize(int buttonsize) {
		this.buttonSize = buttonsize;
		
		return;
	}
	
	public Boolean getIncludeMedallionText() {
		return includeMedallionText;
	}

	public void setIncludeMedallionText(Boolean includeMedallionText) {
		this.includeMedallionText = includeMedallionText;
		
		return;
	}
	
	public Boolean getIncludeNoHitText() {
		return includeNoHitText;
	}

	public void setIncludeNoHitText(Boolean includeNoHitText) {
		this.includeNoHitText = includeNoHitText;
		
		return;
	}
}
