package org.mickey.holefilling;

/**
 * This class holds corresponding image and a mask
 * Calculations based on both an image and a mask will happen here
 */


//@TODO: change to static class with functions
public class ImageAndMask {

    private final FloatImage img;
    private final MaskSet mask;


    public ImageAndMask(String img_path, String mask_path, boolean is4Connected) {
        this.img = new FloatImage(img_path);
        this.mask = new MaskSet(mask_path, is4Connected);
    }

    /**
     *
     * @param w - a weight function of our choice
     * @return The image stored in this class with it's corresponding hole fixed
     */
    public FloatImage fill(WeightFunction w) {

        FloatImage filledImg = new FloatImage(this.img);

        for (Coordinate hole_coordinate : this.mask.getHole()) {
           float Iu = calculateI(hole_coordinate, w);
            filledImg.setPixel(hole_coordinate, Iu);
        }
        return filledImg;
    }

    /**
     * To fill the hole in O(n) we will calculate the avg coordinate of the hole.
     * For that coordinate u, we will calculate I(u) and insert it to every pixel of the hole
     * @param w a weight function of our choice
     * @return The image stored in this class with it's corresponding hole fixed with the avg value of I(u)
     */
    public FloatImage fillEqual(WeightFunction w){

        FloatImage filledImg = new FloatImage(this.img);

        //Calculate the avg coordinate position
        double avg_x=0,avg_y=0;
        for (Coordinate hole_coordinate : this.mask.getHole()){
            avg_x += hole_coordinate.getX();
            avg_y += hole_coordinate.getY();
        }
        avg_x = avg_x / this.mask.getHole().size();
        avg_y = avg_y / this.mask.getHole().size();
        Coordinate avg_coordinate = new Coordinate((int)avg_x,(int)avg_y);

        //Calculate avgI = I(u) for the average coordinate u
        float avgI = calculateI(avg_coordinate, w);

        //Fill the hole with avgI
        for (Coordinate hole_coordinate : this.mask.getHole()) {
            filledImg.setPixel(hole_coordinate, avgI);
        }

        return filledImg;
    }

    /**
     * Calculte I(u) for the formula in the doc.
     * @param u Coordinate of a pixel in the hole
     * @param w a weight function of our choice
     * @return Result of calculation
     */
    private float calculateI(Coordinate u, WeightFunction w){
        float numerator = 0;
        float denominator = 0;
        for (Coordinate border_coordinate : this.mask.getBorder()) {
            float wFunctionResult = w.calculate(u, border_coordinate);
            numerator += wFunctionResult * img.getPixel(border_coordinate);
            denominator += wFunctionResult;
        }
        return numerator / denominator;
    }

    /**
     *
     * @return The image stored in this class with it's corresponding hole highlighted
     */
    public FloatImage HighlightBorder(){

        FloatImage highlightedImg = new FloatImage(this.img);

        for (Coordinate c : this.mask.getBorder()){
            highlightedImg.setPixel(c, FloatImage.BRIGHTEST_PIXEL);
        }
        for (Coordinate c : this.mask.getHole()){
            highlightedImg.setPixel(c, FloatImage.DARKEST_PIXEL);
        }
        return highlightedImg;
    }

    /**
     * This function is not in use but is added for support of future functions that take normalized FloatImage as an input.
     * @return a FloatImage where the values are normalized from [0,255] to [0,1] and hole pixels have a value of -1
     */
    public FloatImage normalizeAndInsertHole(){
        int rows = this.img.getImg().length;
        int cols = this.img.getImg()[0].length;
        float[][] normalized = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                normalized[i][j] = this.img.getImg()[i][j] / 255;
            }
        }
        for (Coordinate hole_coordinate : this.mask.getHole()){
            normalized[hole_coordinate.getY()][hole_coordinate.getY()] = -1;
        }
        return new FloatImage(normalized);
    }

}
