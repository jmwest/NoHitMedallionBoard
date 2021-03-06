package noHitMedallionBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MedallionCombo implements ActionListener {

    final static int defaultButtonSize=100;
    
    private String userDirectoryFilePathString = System.getProperty("user.dir") + System.getProperty("file.separator")
    												+ "resources" + System.getProperty("file.separator");
	
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
	
	/*  TODO
	 *  Need to make functionality later that allows user to:
	 *  [Complete] upload an image,
	 *  [] save the image,
	 *  [Complete] then make a gray scale version of the image to use on the button.
	 */ 
	private BufferedImage medallionGSBImage;
	private BufferedImage medallionBImage;
	
	private String medallionBImageFilePath;
	private String medallionGSBImageFilePath;
	
	// Class Constructors
	public MedallionCombo(String medStr, String imgSrcStr, BufferedImage backGroundImage, Point location) {
		
		// Handle image file
		CreateImageShadow iShadow = new CreateImageShadow(openResourceImageFile(imgSrcStr), backGroundImage,
														   location, shadowLengthMultiplier);
		
		medallionBImage = iShadow.getCompleteImage();
		medallionGSBImage = iShadow.getCompleteShadowImage();
		
		medallionComboSetup(medStr, "No Hit\nAny %");
		
		saveMedallionImagesToFile();
	}
	
	public MedallionCombo(String medStr, BufferedImage medBImage, BufferedImage medGSBImage, String noHitStr) {
		
		// Handle images
		medallionBImage = medBImage;
		medallionGSBImage = medGSBImage;
		
		medallionComboSetup(medStr, noHitStr);
		
		saveMedallionImagesToFile();
	}
	
	// Class Constructor helper function
	private void medallionComboSetup(String medStr, String noHitStr) {
		
		medallionText = new JTextPane();
		setMedallionButton(new JButton());
		noHitTextPane = new JTextPane();
		
		getMedallionButton().addActionListener(this);
		getMedallionButton().setBorderPainted(false);
		getMedallionButton().setRolloverEnabled(false);
		getMedallionButton().setContentAreaFilled(false);
		getMedallionButton().setFocusPainted(false);
		
		//		
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
		
		// Handle images
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
		noHitTextPane.setText(noHitStr);
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
	
	public void saveMedallionImagesToFile() {
		
		String medallionBImageFilePath = userDirectoryFilePathString + removeBadChars(medallionText.getText()) + ".png";
		this.medallionBImageFilePath = medallionBImageFilePath;
		
		File medallionBImageSave = new File(medallionBImageFilePath);
		
		if (!medallionBImageSave.exists()) {
			File directory = new File(medallionBImageSave.getParent());
			if (!directory.exists()) {
				directory.mkdirs();
			}
		
			try {
				System.out.println("Wrote BImage to: " + medallionBImageFilePath);
			    ImageIO.write(medallionBImage, "png", medallionBImageSave);
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		
		String medallionGSBImageFilePath = userDirectoryFilePathString + removeBadChars(medallionText.getText()) + "_GS.png";
		this.medallionGSBImageFilePath = medallionGSBImageFilePath;
		
		File medallionGSBImageSave = new File(medallionGSBImageFilePath);
		
		if (!medallionGSBImageSave.exists()) {
			File directory = new File(medallionGSBImageSave.getParent());
			if (!directory.exists()) {
				directory.mkdirs();
			}
		
			try {
				System.out.println("Wrote GSBImage to: " + medallionGSBImageFilePath);
			    ImageIO.write(medallionGSBImage, "png", medallionGSBImageSave);
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	
	private String removeBadChars(String str) {
		
		return str.replaceAll(" ", "_").replaceAll("<", "").replaceAll(">", "").replaceAll(":", "").replaceAll("\"", "")
					.replaceAll("\\/", "").replaceAll("|", "").replaceAll("\\?", "").replaceAll("\\*", "");
	}
	
	// Private MedallionCombo functions
	private BufferedImage openResourceImageFile(String filename) {
		
		System.out.println("Filename pass to openResourceImageFile: " + filename);
		InputStream imageStream = NoHitMedallionBoard.class.getClassLoader().getResourceAsStream(filename);
		
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
            
            image = openImageFile(userDirectoryFilePathString + filename);
		}
		
		return image;
	}
	
	private BufferedImage openImageFile(String filename) {
		
		System.out.println("Filename pass to openImageFile: " + filename);

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
	public BufferedImage getMedallionBImage() {
		return medallionBImage;
	}

	public void setMedallionBImage(BufferedImage medallionbimg) {
		this.medallionBImage = medallionbimg;
		
		return;
	}
	
	public BufferedImage getMedallionGSBImage() {
		return medallionGSBImage;
	}

	public void setMedallionGSBImage(BufferedImage medalliongsbimg) {
		this.medallionGSBImage = medalliongsbimg;
		
		return;
	}
	
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
		
		getMedallionTextPane().setVisible(includeMedallionText);
		
		return;
	}
	
	public Boolean getIncludeNoHitText() {
		return includeNoHitText;
	}

	public void setIncludeNoHitText(Boolean includeNoHitText) {
		this.includeNoHitText = includeNoHitText;
		
		getNoHitTextPane().setVisible(includeNoHitText);
		
		return;
	}
	
	public String getMedallionBImageFilePath() {
		return medallionBImageFilePath;
	}

	public void setMedallionBImageFilePath(String filePath) {
		this.medallionBImageFilePath = filePath;
		
		return;
	}
	
	public String getMedallionGSBImageFilePath() {
		return medallionGSBImageFilePath;
	}

	public void setMedallionGSBImageFilePath(String filePath) {
		this.medallionGSBImageFilePath = filePath;
		
		return;
	}
	
	public boolean getMedallionBGS() {
		return medallionBGS;
	}
	
	public void setMedallionBGS(boolean medallionbgs) {
		this.medallionBGS = medallionbgs;
		
		return;
	}
	
	public String getMedallionActionCommandString() {
		return medallionButtonActionCommandStr;
	}
}
