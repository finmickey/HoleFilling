package org;

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
