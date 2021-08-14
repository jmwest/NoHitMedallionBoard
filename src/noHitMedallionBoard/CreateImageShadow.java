package noHitMedallionBoard;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.BitSet;

public class CreateImageShadow {
	
    final static int rgbWhite=new Color(255, 255, 255).getRGB();
    final static int rgbBlack=new Color(0, 0, 0).getRGB();
	
	private BufferedImage blackWhiteImg;
	private BufferedImage imgOutline;
	private ArrayList<BitSet> shadowBitSet;
	
	public CreateImageShadow(BufferedImage img, float shadowLengthMultiplier) {
		
		blackWhiteImg = createBlackWhiteImage(img);
		imgOutline = createImageOutline(blackWhiteImg);
		shadowBitSet = createShadowBitset(imgOutline, shadowLengthMultiplier);
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
	
	// Private Functions
	private BufferedImage createBlackWhiteImage(BufferedImage img) {
		
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage bwImg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (isTransparent(img.getRGB(i, j))) {
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
	
	private ArrayList<BitSet> createShadowBitset(BufferedImage imgoutline, float shadowmult) {
		
		ArrayList<BitSet> shadowbitset = new ArrayList<BitSet>();
		int w = imgoutline.getWidth();
		int h = imgoutline.getHeight();
		
		int shadowlength = (int)(Math.sqrt(Math.multiplyExact(w, w) + Math.multiplyExact(h, h))*shadowmult);
		
		for (int i = 0; i < w; i++) {
			
			shadowbitset.add(new BitSet(h));
		}
		
		System.out.println("Entering createShadowBitset loops.");
		
		for (int i = 0; i < w; i++) {
			
			for (int j = 0; j < h; j++) {
				
				if (imgoutline.getRGB(i, j) == rgbBlack) {
					
					System.out.println("Found outline.");
					
					for (int k = i - shadowlength; k < i + shadowlength + 1; k++) {
						
						for (int m = j - shadowlength; m < j + shadowlength + 1; j++) {
							
							if (Math.abs(i - k) + Math.abs(j - m) > shadowlength) {
								continue;
							}
							else if ((k < 0) || (k > w)) {
								continue;
							}
							else if ((j < 0) || (j > h)) {
								continue;
							}
							
							shadowbitset.get(k).set(m);
						}
					}
				}
			}
		}
		
		System.out.println("Exiting createShadowBitset loops.");
		
		return shadowbitset;
	}

	private boolean isTransparent(int pixel) {
		  if( (pixel>>24) == 0x00 ) {
		      return true;
		  }
		  return false;
	}
}