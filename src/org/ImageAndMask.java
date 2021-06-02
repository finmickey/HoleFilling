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
            float numerator = 0;
            float denominator = 0;
            for (Coordinate border_coordinate : this.mask.getBorder()) {
                float wFunctionResult = w.calculate(hole_coordinate, border_coordinate);
                numerator += wFunctionResult * this.img.getImg()[border_coordinate.getX()][border_coordinate.getY()];
                denominator += wFunctionResult;
            }
            filledImg.getImg()[hole_coordinate.getX()][hole_coordinate.getY()] = numerator / denominator;
        }
        return filledImg;
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
