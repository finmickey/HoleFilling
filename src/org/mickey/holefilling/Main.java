package org.mickey.holefilling;

import org.mickey.holefilling.DefaultWeightFunction;
import org.mickey.holefilling.FloatImage;
import org.mickey.holefilling.ImageAndMask;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;

//@TODO: go over all typing, private/public and such

public class Main {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    /**
     * main will write the new images to out/ModifiedImages
     * Fixed.png = the image with the hole fixed
     * Border.png = the image with a highlighted hole (mostly for testing purposes)
     * @param args = <image address> <mask address> <neighbor type = {4,8}>
     *
     */
    public static void main(String[] args){

        //Assuming valid input
        String img_path = args[0];
        String mask_path = args[1];
        boolean is4Connected = (Integer.parseInt(args[2]) == 4);

        ImageAndMask imageMask = new ImageAndMask(img_path,mask_path, is4Connected);

        FloatImage fixedImg = imageMask.fill(new DefaultWeightFunction());
        FloatImage lazyFixImg = imageMask.fillEqual(new DefaultWeightFunction());

        Imgcodecs.imwrite("out/ModifiedImages/Fixed.png", fixedImg.toMat());
        Imgcodecs.imwrite("out/ModifiedImages/Border.png", imageMask.HighlightBorder().toMat());
        Imgcodecs.imwrite("out/ModifiedImages/LazyFix.png", lazyFixImg.toMat());

    }


}
