package noHitMedallionBoard;

import java.awt.Color;
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
	private int tintLength;
	private BufferedImage shadowImg;
	private BufferedImage blackWhiteShadowImg;
	private BufferedImage shadowImgOutline;
	private ArrayList<BitSet> shadowOutlineBitSet;
	private BufferedImage shadowOutlineImg;
	
	private BufferedImage completeShadowImg;
	private BufferedImage completeImg;
	
	private int bgColor = 0;
	private int shadowColor = 0;
	private int tintColor = 0;
	
	public CreateImageShadow(BufferedImage img, BufferedImage backgroundImage,
							 Point imgLocation, float shadowLengthMultiplier) {
		
		int w = img.getWidth();
		int h = img.getHeight();
		
		shadowLength = (int)(Math.sqrt(Math.multiplyExact(w, w) + Math.multiplyExact(h, h))*shadowLengthMultiplier);
		tintLength = shadowLength/2;
		
		// Create dark shadow
		blackWhiteImg = createBlackWhiteImage(img, shadowLength);
		imgOutline = createImageOutline(blackWhiteImg);
		shadowBitSet = createShadowBitset(imgOutline, shadowLength);
		shadowImg = createShadow(blackWhiteImg, backgroundImage, imgLocation, shadowBitSet);
		
		// Create tinted border along shadow
		blackWhiteShadowImg = createBlackWhiteImage(shadowImg, tintLength);
		shadowImgOutline = createImageOutline(blackWhiteShadowImg);
		shadowOutlineBitSet = createShadowBitset(shadowImgOutline, tintLength);
		shadowOutlineImg = createShadow(blackWhiteShadowImg, backgroundImage, imgLocation, shadowOutlineBitSet);
				
		// Finalize the images
		completeShadowImg = addShadowOutlineTint(shadowOutlineImg, blackWhiteShadowImg);
		completeImg = addCompleteShadowToImg(img, completeShadowImg);
	}

	// Public Getter/Setter Functions
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
	
	public BufferedImage getBlackWhiteShadowImage() {
		return blackWhiteShadowImg;
	}
	
	public BufferedImage getShadowImageOutline() {
		return shadowImgOutline;
	}
	
	public BufferedImage getShadowOutlineBitSetImage() {
		
		int w = blackWhiteShadowImg.getWidth();
		int h = blackWhiteShadowImg.getHeight();
		BufferedImage bsimg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (shadowOutlineBitSet.get(i).get(j)) {
					bsimg.setRGB(i, j, rgbBlack);
				}
				else {
					bsimg.setRGB(i, j, rgbWhite);
				}
			}
		}
		
		return bsimg;
	}
	
	public BufferedImage getShadowOutlineImage() {
		return shadowOutlineImg;
	}
	
	public BufferedImage getCompleteShadowImage() {
		return completeShadowImg;
	}
	
	public BufferedImage getCompleteImage() {
		return completeImg;
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

		shadowColor = getShadowColor(bgColor);
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (shadowBitSet.get(i).get(j)) {
					shadowimg.setRGB(i, j, shadowColor);
				}
				else if (bwimg.getRGB(i, j) == rgbBlack) {
					shadowimg.setRGB(i, j, shadowColor);
				}
				else {
					shadowimg.setRGB(i, j, rgbAlpha);
				}
			}
		}
		
		return shadowimg;
	}
	
	private BufferedImage addShadowOutlineTint(BufferedImage tintimg, BufferedImage shadowimg) {
		
		int w = tintimg.getWidth();
		int h = tintimg.getHeight();
		
		tintColor = getTintColor(bgColor);
		
		BufferedImage tintShadow = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (tintimg.getRGB(i, j) == shadowColor) {
					tintShadow.setRGB(i, j, tintColor);
				}
				else {
					tintShadow.setRGB(i, j, tintimg.getRGB(i, j));
				}
				
				if (shadowimg.getRGB(i, j) == rgbBlack) {
					tintShadow.setRGB(i, j, shadowColor);
				}
			}
		}
		
		return tintShadow;
	}
	
	private BufferedImage addCompleteShadowToImg(BufferedImage img, BufferedImage shadowimg) {
		
		int shadowW = shadowimg.getWidth();
		int shadowH = shadowimg.getHeight();
				
		int imgW = img.getWidth();
		int imgH = img.getHeight();
				
		int imgBorder = shadowLength + tintLength;
				
		BufferedImage imgwithshadow = new BufferedImage(shadowW, shadowH, BufferedImage.TYPE_4BYTE_ABGR);
		
		for (int i = 0; i < shadowW; i++) {
			
			for (int j = 0; j < shadowH; j++) {
				
				try {
					if ((i < imgBorder) || (j < imgBorder) || (i > imgW + imgBorder - 1) || (j > imgH + imgBorder - 1)) {
						imgwithshadow.setRGB(i, j, shadowimg.getRGB(i, j));
					}
					else if (isTransparent(img.getRGB(i-imgBorder, j-imgBorder))) {
						imgwithshadow.setRGB(i, j, shadowimg.getRGB(i, j));
					}
					else {
						imgwithshadow.setRGB(i, j, img.getRGB(i-imgBorder, j-imgBorder));
					}
				}
				catch (Exception e) {
					System.out.println("i: " + String.valueOf(i));
					System.out.println("j: " + String.valueOf(j));
					System.out.println(e.getStackTrace());
					System.exit(1);
				}
			}
		}
	
		return imgwithshadow;
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
		
		int RTint = RColor + (int)((float)(255 - RColor)*0.25f);
		int GTint = GColor + (int)((float)(255 - GColor)*0.25f);
		int BTint = BColor + (int)((float)(255 - BColor)*0.25f);
		
		int tintcolor = new Color(RTint, GTint, BTint).getRGB();
				
		return tintcolor;
	}
	
	private boolean isTransparent(int pixel) {
		  if( (pixel>>24) == 0x00 ) {
		      return true;
		  }
		  return false;
	}
}