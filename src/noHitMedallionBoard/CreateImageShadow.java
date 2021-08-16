package noHitMedallionBoard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.BitSet;

public class CreateImageShadow {
	
    final static int rgbWhite=new Color(255, 255, 255).getRGB();
    final static int rgbBlack=new Color(0, 0, 0).getRGB();
    final static int rgbAlpha=new Color(1.0f, 1.0f, 1.0f, 0.0f).getRGB();
	
	private BufferedImage blackWhiteImg;
	private BufferedImage imgOutline;
	private ArrayList<BitSet> shadowBitSet;
	private int shadowLength;
	private BufferedImage shadowImg;
	private BufferedImage completeShadowImg;
	
	private int bgColor = 0;
	
	public CreateImageShadow(BufferedImage img, BufferedImage backgroundImage,
							 Point imgLocation, float shadowLengthMultiplier) {
		
		int w = img.getWidth();
		int h = img.getHeight();
		
		shadowLength = (int)(Math.sqrt(Math.multiplyExact(w, w) + Math.multiplyExact(h, h))*shadowLengthMultiplier);
		
		blackWhiteImg = createBlackWhiteImage(img, shadowLength);
		imgOutline = createImageOutline(blackWhiteImg);
		shadowBitSet = createShadowBitset(imgOutline, shadowLength);
		shadowImg = createShadow(blackWhiteImg, backgroundImage, imgLocation, shadowBitSet);
		completeShadowImg = addShadowOutlineTint(shadowImg);
		
	}

	// Public Functions
	public BufferedImage getBlackWhiteImage() {
		return blackWhiteImg;
	}
	
	public BufferedImage getImageOutlineRaw() {
		return imgOutline;
	}
	
	public BufferedImage getShadowBitSetImage() {
		
		int w = blackWhiteImg.getWidth();
		int h = blackWhiteImg.getHeight();
		BufferedImage bsimg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (shadowBitSet.get(i).get(j)) {
					bsimg.setRGB(i, j, rgbBlack);
				}
				else {
					bsimg.setRGB(i, j, rgbWhite);
				}
			}
		}
		
		return bsimg;
	}
	
	public BufferedImage getShadowImage() {
		return shadowImg;
	}
	
	public BufferedImage getCompleteShadowImage() {
		return completeShadowImg;
	}
	
	// Private Functions
	private BufferedImage createBlackWhiteImage(BufferedImage img, int shadowlength) {
		
		int w = img.getWidth();
		int h = img.getHeight();
		
		int newW = w + 2*shadowlength;
		int newH = h + 2*shadowlength;		
		
		BufferedImage bwImg = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int i = 0; i < newW; i++) {
			
			for (int j = 0; j < newH; j++) {
				
				if ((i < shadowlength) || (j < shadowlength) || (i >= (newW - shadowlength)) || (j >= (newH - shadowlength))) {
					bwImg.setRGB(i, j, rgbWhite);
				}
				else if (isTransparent(img.getRGB(i-shadowlength, j-shadowlength))) {
					bwImg.setRGB(i, j, rgbWhite);
				}
				else {
					bwImg.setRGB(i, j, rgbBlack);
				}
			}
		}
		
		return bwImg;
	}
	
	private BufferedImage createImageOutline(BufferedImage bwimg) {
		
		int w = bwimg.getWidth();
		int h = bwimg.getHeight();
		BufferedImage imgOutline = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int l = 0; l < w; l++) {
			
			imgOutline.setRGB(l, 0, rgbWhite);
			imgOutline.setRGB(l, h - 1, rgbWhite);
		}
		
		for (int t = 1; t < h - 1; t++) {
			
			imgOutline.setRGB(0, t, rgbWhite);
			imgOutline.setRGB(w - 1, t, rgbWhite);
		}
		
		int tl, tm, tr, ml, mm, mr, bl, bm, br;
		
		for (int i = 1; i < w - 1; i++) {
			
			for (int j = 1; j < h - 1; j++) {
				
				tl = bwimg.getRGB(i - 1, j - 1);
				tm = bwimg.getRGB(i, j - 1);
				tr = bwimg.getRGB(i + 1, j - 1);
				ml = bwimg.getRGB(i - 1, j);
				mm = bwimg.getRGB(i, j);
				mr = bwimg.getRGB(i + 1, j);
				bl = bwimg.getRGB(i - 1, j + 1);
				bm = bwimg.getRGB(i, j + 1);
				br = bwimg.getRGB(i + 1, j + 1);
				
				if ((mm == tl) && (mm == tm) && (mm == tr) && (mm == ml) && (mm == mr)
						&& (mm == bl) && (mm == bm) && (mm == br)) {
					imgOutline.setRGB(i, j, rgbWhite);
				}
				else {
					imgOutline.setRGB(i, j, rgbBlack);
				}
			}
		}
		
		return imgOutline;
	}
	
	private ArrayList<BitSet> createShadowBitset(BufferedImage imgoutline, int shadowLength) {
		
		ArrayList<BitSet> shadowbitset = new ArrayList<BitSet>();
		int w = imgoutline.getWidth();
		int h = imgoutline.getHeight();
				
		for (int i = 0; i < w; i++) {
			
			shadowbitset.add(new BitSet(h));
		}
				
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (imgoutline.getRGB(i, j) == rgbBlack) {
										
					for (int k = i - shadowLength; k < i + shadowLength + 1; k++) {
						
						for (int m = j + Math.abs(i - k) - shadowLength; m < j - Math.abs(i - k) + shadowLength + 1; m++) {
							
							if (Math.abs(i - k) + Math.abs(j - m) > shadowLength) {
								continue;
							}
							else if ((k < 0) || (k > w - 1)) {
								continue;
							}
							else if ((m < 0) || (m > h - 1)) {
								continue;
							}
							
							shadowbitset.get(k).set(m);
						}
					}
				}
			}
		}
				
		return shadowbitset;
	}
	
	private BufferedImage createShadow(BufferedImage bwimg, BufferedImage backgroundImg,
									   Point imgloc, ArrayList<BitSet> shadowBitSet) {
		
		int w = bwimg.getWidth();
		int h = bwimg.getHeight();
		BufferedImage shadowimg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		
		bgColor = backgroundImg.getRGB(imgloc.x, imgloc.y);

		int bgshadowcolor = getShadowColor(bgColor);
		
		System.out.println("BGShadowColor: " + String.valueOf(bgshadowcolor));
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (shadowBitSet.get(i).get(j)) {
					shadowimg.setRGB(i, j, bgshadowcolor);
				}
				else if (bwimg.getRGB(i, j) == rgbBlack) {
					shadowimg.setRGB(i, j, bgshadowcolor);
				}
				else {
					shadowimg.setRGB(i, j, rgbAlpha);
				}
			}
		}
		
		return shadowimg;
	}
	
	private BufferedImage createTintShadow(BufferedImage tintOutline, ArrayList<BitSet> shadowBitSet, int tintcolor) {

		int w = tintOutline.getWidth();
		int h = tintOutline.getHeight();
		BufferedImage shadowimg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		
		for (int i = 0; i < w; i++) {
		
			for (int j = 0; j < h; j++) {
			
				if (shadowBitSet.get(i).get(j)) {
					shadowimg.setRGB(i, j, tintcolor);
				}
				else {
					shadowimg.setRGB(i, j, rgbAlpha);
				}
			}
		}

		return shadowimg;
	}
	
	private BufferedImage addShadowOutlineTint(BufferedImage shadowimg) {
		
		int w = shadowimg.getWidth();
		int h = shadowimg.getHeight();
		
		int newW = w + 2*shadowLength;
		int newH = h + 2*shadowLength;
		
		BufferedImage shadowOutlineImg = new BufferedImage(newW, newH, BufferedImage.TYPE_4BYTE_ABGR);
		
		int tintcolor = getTintColor(bgColor);
		
		BufferedImage tintOutline = createImageOutline(shadowimg);
		BufferedImage expandedTintOutline = new BufferedImage(newW, newH, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g1 = expandedTintOutline.createGraphics();
		g1.setColor(new Color(rgbWhite));
		g1.fillRect(0, 0, newW, newH);
		g1.drawImage(tintOutline, shadowLength, shadowLength, newW - shadowLength, newH - shadowLength, null);
		
		ArrayList<BitSet> tintBitSet = createShadowBitset(expandedTintOutline, shadowLength);
		BufferedImage tintShadow = createTintShadow(tintOutline, tintBitSet, tintcolor);
		
		Graphics g2 = shadowOutlineImg.createGraphics();
		g2.drawImage(tintShadow, 0, 0, newW, newH, null);
		g2.drawImage(shadowimg, 0, 0, newW, newH, null);
		
		return shadowOutlineImg;
	}
	
	//
	private int getShadowColor(int color) {
		
		Color Color = new Color(color);
		
		int RColor = Color.getRed();
		int GColor = Color.getGreen();
		int BColor = Color.getBlue();

		int RShade = (int)(RColor*0.5f);
		int GShade = (int)(GColor*0.5f);
		int BShade = (int)(BColor*0.5f);

		int shadecolor = new Color(RShade, GShade, BShade).getRGB();

		return shadecolor;
	}
	
	private int getTintColor(int color) {
		
		Color Color = new Color(color);
		
		int RColor = Color.getRed();
		int GColor = Color.getGreen();
		int BColor = Color.getBlue();
		
		System.out.println("Color: " + String.valueOf(color));
		System.out.println("RColor: " + String.valueOf(RColor));
		System.out.println("GColor: " + String.valueOf(GColor));
		System.out.println("BColor: " + String.valueOf(BColor));
		
		int RTint = RColor + (int)((255 - RColor)*0.125f);
		int GTint = GColor + (int)((255 - GColor)*0.125f);
		int BTint = BColor + (int)((255 - BColor)*0.125f);
		
		System.out.println("RTint: " + String.valueOf(RTint));
		System.out.println("GTint: " + String.valueOf(GTint));
		System.out.println("BTint: " + String.valueOf(BTint));
		
		int tintcolor = new Color(RTint, GTint, BTint).getRGB();
		
		System.out.println("TintColor: " + String.valueOf(tintcolor));
		
		return tintcolor;
	}
	
	private boolean isTransparent(int pixel) {
		  if( (pixel>>24) == 0x00 ) {
		      return true;
		  }
		  return false;
	}
}