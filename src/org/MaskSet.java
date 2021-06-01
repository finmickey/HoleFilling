package org;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.HashSet;

public class MaskSet {
    private HashSet<Coordinate> hole;
    private HashSet<Coordinate> border;

    public HashSet<Coordinate> getHole() {
        return hole;
    }

    public HashSet<Coordinate> getBorder() {
        return border;
    }

    private MaskSet(HashSet<Coordinate> hole, HashSet<Coordinate> border) {
        this.hole = hole;
        this.border = border;
    }

    public static MaskSet readMaskImage(String address, boolean is4Neighbors) {
        Mat mat = Imgcodecs.imread(address, 0);
        HashSet<Coordinate> hole = new HashSet<>();
        HashSet<Coordinate> border = new HashSet<>();

        int rows = mat.rows();
        int cols = mat.cols();
        for (int i = 1; i < rows - 1; i++) { //No need to address holes that touch the boundaries
            for (int j = 1; j < cols - 1; j++) { //No need to address holes that touch the boundaries
                if (mat.get(i, j)[0] < 127.5)
                    hole.add(new Coordinate(i, j));
            }
        }
        for (Coordinate c : hole) {
            int x = c.getX();
            int y = c.getY();

            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++)
                    if (!(((Math.abs(dx) + Math.abs(dy)) == 2) && is4Neighbors)) //Check if the square is legal under neighbor's constrains
                        if ((mat.get(x + dx, y + dy)[0] > 127.5) && (!((dx == 0) && (dy == 0)))) //Check if not centre square and is not a hole itself
                            border.add(new Coordinate(x + dx, y + dy));
            }
        }

        return new MaskSet(hole, border);
    }


}
