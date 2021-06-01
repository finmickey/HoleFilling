import org.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

//@TODO: lambda stuff
//@TODO: go over all typing, private/public and such

public class Main {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    /**
     *
     * @param args = <image address> <mask address> <neighbor type = {4,8}>
     */
    public static void main(String args[]){
        //@TODO: move addresses input to args[]
        String lenna_adr = "input/Lenna.png";
        String mask_adr = "input/Mask2.png";
        int neighbors = 4;
        boolean is4Neighbors = (neighbors==4);

        FloatImage img = FloatImage.readFloatImage(lenna_adr);
        MaskSet mask = MaskSet.readMaskImage(mask_adr, is4Neighbors);
        ImageAndMask iam = new ImageAndMask(img,mask);
        FloatImage newimg = iam.fill(new DefaultWeightFunction());
        Mat m = new Mat(img.getImg().length,img.getImg()[0].length, 0);

        //@TODO: works seperatly, not in the same time though
        //Imgcodecs.imwrite("input/Border.png", iam.HighlightBorder().toMat());
        Imgcodecs.imwrite("input/Fixed.png", newimg.toMat());

        System.out.println((5/2.0));

    }
}
