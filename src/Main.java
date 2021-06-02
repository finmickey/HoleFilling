import org.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
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

        String img_path = args[0];
        String mask_path = args[1];
        boolean is4Connected = (Integer.parseInt(args[2]) == 4); //Assuming valid input

        FloatImage img = FloatImage.readFloatImage(img_path);
        MaskSet mask = MaskSet.readMaskImage(mask_path, is4Connected);
        ImageAndMask imageMask = new ImageAndMask(img,mask);
        FloatImage fixedImg = imageMask.fill(new DefaultWeightFunction());

        Imgcodecs.imwrite("out/ModifiedImages/Fixed.png", fixedImg.toMat());
        Imgcodecs.imwrite("out/ModifiedImages/Border.png", imageMask.HighlightBorder().toMat());

    }
}
