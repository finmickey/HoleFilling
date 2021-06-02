package org;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Holds a float 2d array representing a grayscale image
 */
public class FloatImage {
    private final float[][] img;

    public float[][] getImg() {
        return img;
    }

    public FloatImage(float[][] img) {
        this.img = img;
    }

    public FloatImage(FloatImage toDup){
        int height = toDup.getImg().length;
        int width = toDup.getImg()[0].length;
        float[][] newImg = new float[height][width];
        for (int i=0; i<height; i++){
            System.arraycopy(toDup.getImg()[i], 0, newImg[i], 0, width);
        }
        this.img = newImg;
    }

    /**
     *
     * @param path path to the image file
     * @return A new FloatImage object built from the image at path
     */
    public static FloatImage readFloatImage(String path) {
        //@TODO: check if I'm allowed to use this to load the img in grayscale
        Mat mat = Imgcodecs.imread(path, 0);
        return new FloatImage(MatToFloatImage(mat));
    }

    /**
     *
     * @param mat a Mat object that holds an image
     * @return a FloatImage object that represents the same image
     */
    private static float[][] MatToFloatImage(Mat mat) {
        int rows = mat.rows();
        int cols = mat.cols();
        float[][] floatImage = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                floatImage[i][j] = (float) mat.get(i, j)[0];
            }
        }
        return floatImage;
    }

    /**
     * Builds a Mat object for output purposes
     * @return Mat object build from the FloatImage float 2d array
     */
    public Mat toMat(){
        Mat m = new Mat(this.img.length,this.img[0].length, 0);
        for (int i=0; i<m.rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                m.put(i, j, this.img[i][j]);
            }
        }
        return m;
    }

}
