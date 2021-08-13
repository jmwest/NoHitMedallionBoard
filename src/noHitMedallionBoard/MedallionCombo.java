package noHitMedallionBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class MedallionCombo implements ActionListener {

	private JButton medallionButton;
	private JTextArea medallionText;
	
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
	
	public MedallionCombo(String medStr, String imgSrcStr) {
		
		medallionText = new JTextArea(medStr);
		setMedallionButton(new JButton());
		
		getMedallionButton().addActionListener(this);
		getMedallionButton().setOpaque(false);
		getMedallionButton().setBorderPainted(false);
		
		medallionText.setEditable(false);
		medallionText.setLineWrap(true);
		medallionText.setWrapStyleWord(true);
				
		medallionButtonActionCommandStr = "change " + medStr + " button";
		getMedallionButton().setActionCommand(medallionButtonActionCommandStr);
		
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
		
		getMedallionButton().setMinimumSize(new Dimension(100, 100));
		getMedallionButton().setPreferredSize(new Dimension(100,100));
		getMedallionButton().setMaximumSize(new Dimension(100,100));
		
		getMedallionTextArea().setMinimumSize(new Dimension(150, 30));
		getMedallionTextArea().setPreferredSize(new Dimension(150, 40));
		getMedallionTextArea().setMaximumSize(new Dimension(150, 60));
		
		ImageIcon medallionIcon = getScaledIcon(medallionGSBImage, getMedallionButton());
		getMedallionButton().setIcon(medallionIcon);
	}
	
	// Public MedallionPanel functions
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
		
		BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = newImg.createGraphics();
		g.drawImage(srcImg, 0, 0, w, h, null);
		g.dispose();
		
		BufferedImage newShadowImage = dropShadow(newImg);
		
		return newShadowImage;
	}
	
	private BufferedImage getGrayScaleImage(BufferedImage colorImg) {
		
		int w = colorImg.getWidth();
		int h = colorImg.getHeight();
			
		BufferedImage gsImg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			
		try {
			for (int i=0; i<h; i++) {
				
				for (int j=0; j<w; j++) { 
					
					Color c = new Color(colorImg.getRGB(j, i), true);
					int red = (int)(c.getRed() * 0.299);
					int green = (int)(c.getGreen() * 0.587);
					int blue = (int)(c.getBlue() * 0.114);
					
					Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue, c.getAlpha());
					
					gsImg.setRGB(j, i, newColor.getRGB());
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		BufferedImage newShadowGSBImage = dropShadow(gsImg);
		
		return newShadowGSBImage;
	}
	
	private static BufferedImage dropShadow(BufferedImage img) {
	    // a filter which converts all colors except 0 to black

	    ImageProducer prod = new FilteredImageSource(img.getSource(), new RGBImageFilter() {
	        @Override
	        public int filterRGB(int x, int y, int rgb) {
	        	
	            if (rgb == 0)
	                return 0;
	            else
	                return 0xff000000;
	        }
	    });
	    // create the black image
	    Image shadow = Toolkit.getDefaultToolkit().createImage(prod);

	    // result
	    BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	    Graphics2D g = (Graphics2D) result.getGraphics();

	    // draw shadow with offset
	    g.drawImage(shadow, 10, 0, null);
	    // draw original image
	    g.drawImage(img, 0, 0, null);

	    return result;
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

	public JButton getMedallionButton() {
		return medallionButton;
	}

	public void setMedallionButton(JButton medallionButton) {
		this.medallionButton = medallionButton;
		
		return;
	}
	
	public JTextArea getMedallionTextArea() {
		return medallionText;
	}

	public void setMedallionTextArea(JTextArea medallionTextArea) {
		this.medallionText = medallionTextArea;
		
		return;
	}
}
