package org;

/**
 * This class holds corresponding image and a mask
 * Calculations based on both an image and a mask will happen here
 */
public class ImageAndMask {
    private FloatImage img;
    private MaskSet mask;

    public ImageAndMask(FloatImage img, MaskSet mask) {
        this.img = img;
        this.mask = mask;
    }

    public FloatImage getImg() {
        return img;
    }

    public MaskSet getMask() {
        return mask;
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
            filledImg.getImg()[hole_coordinate.getX()][hole_coordinate.getY()] = Iu;
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
            filledImg.getImg()[hole_coordinate.getX()][hole_coordinate.getY()] = avgI;
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
            numerator += wFunctionResult * this.img.getImg()[border_coordinate.getX()][border_coordinate.getY()];
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
            highlightedImg.getImg()[c.getX()][c.getY()] = 255;
        }
        for (Coordinate c : this.mask.getHole()){
            highlightedImg.getImg()[c.getX()][c.getY()] = 0;
        }
        return highlightedImg;
    }

}
