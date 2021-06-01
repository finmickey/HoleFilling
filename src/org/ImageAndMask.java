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
        int height = this.img.getImg().length;
        int width = this.img.getImg()[0].length;
        float[][] filledImg = new float[height][width];
        System.arraycopy(this.img.getImg(), 0, filledImg, 0, height);
        for (Coordinate hole_coordinate : this.mask.getHole()) {
            float numerator = 0;
            float denominator = 0;
            for (Coordinate border_coordinate : this.mask.getBorder()) {
                float wFunctionResult = w.calculate(hole_coordinate, border_coordinate);
                numerator += wFunctionResult * this.img.getImg()[border_coordinate.getX()][border_coordinate.getY()];
                denominator += wFunctionResult;
            }
            filledImg[hole_coordinate.getX()][hole_coordinate.getY()] = numerator / denominator;
        }
        return new FloatImage(filledImg);
    }

    public FloatImage HighlightBorder(){

        int height = this.img.getImg().length;
        int width = this.img.getImg()[0].length;
        float[][] highlightedImg = new float[height][width];
        System.arraycopy(this.img.getImg(), 0, highlightedImg, 0, height);

        for (Coordinate c : this.mask.getBorder()){
            highlightedImg[c.getX()][c.getY()] = 255;
        }
        for (Coordinate c : this.mask.getHole()){
            highlightedImg[c.getX()][c.getY()] = 0;
        }
        return new FloatImage(highlightedImg);
    }

}
