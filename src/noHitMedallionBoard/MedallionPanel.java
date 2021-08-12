package noHitMedallionBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class MedallionPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1095808259862088000L;

	private JPanel medallionPanel;
	private JButton medallionButton;
	private JTextArea medallionText;
	
	// Boolean to keep track of whether the image is gray scale (GS) or not.
	private Boolean medallionBGS = true;
	private String medallionButtonActionCommandStr;
	
	/*  Need to make functionality later that allows user to upload an image,
	 *  save the image, then make a gray scale version of the image to use
	 *  on the button.
	 */ 
	private BufferedImage medallionGSBImage;
	private BufferedImage medallionBImage;
	
	public MedallionPanel(String medStr, String imgSrcStr) {
		
		medallionPanel = new JPanel();
		
		medallionText = new JTextArea(medStr);
		medallionButton = new JButton();
		
		medallionText.setEditable(false);
		medallionText.setLineWrap(true);
		medallionText.setWrapStyleWord(true);
		
		medallionButton.addActionListener(this);
		
		medallionButtonActionCommandStr = "change " + medStr + " button";
		medallionButton.setActionCommand(medallionButtonActionCommandStr);
		
		// Handle image file
		File medallionImageFile = new File(imgSrcStr);
		
		try {
			System.out.println("Canonical path of target image: " + medallionImageFile.getCanonicalPath());
            if (!medallionImageFile.exists()) {
                System.out.println("file " + medallionImageFile + " does not exist");
            }
            medallionBImage = ImageIO.read(medallionImageFile);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		medallionGSBImage = getGrayScaleImage(medallionBImage);
		
		medallionButton.setPreferredSize(new Dimension(100,100));
		ImageIcon medallionIcon = getScaledIcon(medallionGSBImage, medallionButton);
		medallionButton.setIcon(medallionIcon);
		
		medallionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		medallionPanel.setLayout(new GridLayout(0, 1));
		medallionPanel.add(medallionText);
		medallionPanel.add(medallionButton);
	}
	
	// Public MedallionPanel functions
	public void frameResizeEvent() {
		if (medallionBGS) {
			changeButtonImage(medallionButton, medallionGSBImage);
		}
		else {
			changeButtonImage(medallionButton, medallionBImage);
		}
		
		return;
	}
	
	// Private MedallionPanel functions
	private void changeButtonImage(JButton button, BufferedImage bimg) {
		button.setIcon(getScaledIcon(bimg, button));
		
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
		
		BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = newImg.createGraphics();
		g.drawImage(srcImg, 0, 0, w, h, null);
		g.dispose();
		
		return newImg;
	}
	
	private BufferedImage getGrayScaleImage(BufferedImage colorImg) {
		
		int w = colorImg.getWidth();
		int h = colorImg.getHeight();
			
		BufferedImage gsImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			
		try {
			for (int i=0; i<h; i++) {
				
				for (int j=0; j<w; j++) { 
					
					Color c = new Color(colorImg.getRGB(j, i));
					int red = (int)(c.getRed() * 0.299);
					int green = (int)(c.getGreen() * 0.587);
					int blue = (int)(c.getBlue() * 0.114);
					
					Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
					
					gsImg.setRGB(j, i, newColor.getRGB());
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return gsImg;
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
}
