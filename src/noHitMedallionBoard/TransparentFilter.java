package noHitMedallionBoard;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

class TransparentFilter extends RGBImageFilter {

    final static int aShift=24;
    final static int rShift=16;
    final static int gShift=8;
    final static int bShift=0;
    final static int aMask=0xff<<aShift;
    final static int rMask=0xff<<rShift;
    final static int gMask=0xff<<gShift;
    final static int bMask=0xff<<bShift;
    final static int rgbMask=rMask|gMask|bMask;

    // this fudge value is used in place of the harder (but proper) task of
    // performing statistical analysis of the saturation values (and
    // possibly distances) of highly-saturated pixels contained in the image
    float saturationFudge;
    Boolean keepColor;

    TransparentFilter(float saturationFudge, Boolean keepColor) {
        this.saturationFudge=saturationFudge;
        this.keepColor=keepColor;
        canFilterIndexColorModel=true;
    }

    public int filterRGB(int x, int y, int argb) {
        // separate the three colour channels (ignore incoming alpha)
        int r=(argb&rMask) >>> TransparentFilter.rShift;
        int g=(argb&gMask) >>> TransparentFilter.gShift;
        int b=(argb&bMask) >>> TransparentFilter.bShift;
        // convert to hsb so that we can use saturation to
        // establish the alpha (transparency) value of the pixel
        float[] hsb=Color.RGBtoHSB(r,g,b,null);
        float fa=255f*hsb[1]/this.saturationFudge;
        int a=Math.max(0,Math.min(255,Math.round(fa)))<<TransparentFilter.aShift;
        if (this.keepColor) {
            return a|(argb&TransparentFilter.rgbMask);
		}
        else {
        	/*
        	if (a < 0x80<<TransparentFilter.aShift) {
				a = 0x00<<TransparentFilter.aShift;
			}
        	else {
				a = 0xff<<TransparentFilter.aShift;
			}
			*/
            return a|000000;
        }
    }

}
