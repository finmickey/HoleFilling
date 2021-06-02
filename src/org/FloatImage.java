package org;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class FloatImage {
    private float[][] img;

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
    public static FloatImage readFloatImage(String address) {
        //@TODO: check if I'm allowed to use this to load the img in grayscale
        Mat mat = Imgcodecs.imread(address, 0);
        return new FloatImage(grayscaleMatToNormalized2dFloatArray(mat));
    }

    private static float[][] grayscaleMatToNormalized2dFloatArray(Mat mat) {
        int rows = mat.rows();
        int cols = mat.cols();
        float[][] img_as_normalized_float_2d_array = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                img_as_normalized_float_2d_array[i][j] = (float) mat.get(i, j)[0];
            }
        }
        return img_as_normalized_float_2d_array;
    }

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
