package org.mickey.holefilling;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.HashSet;
import java.util.Set;

/**
 * Holds the mask information as HashSets of the hole coordinates and border coordinates
 */
public class MaskSet {

    private final Set<Coordinate> hole;
    private final Set<Coordinate> border;

    public Set<Coordinate> getHole() {
        return hole;
    }

    public Set<Coordinate> getBorder() {
        return border;
    }

    /**
     *
     * @param path = path to the mask file
     * @param is4Connected = indicator of the type of connectivity. True = 4 connected, False = 8 connected
     * @return A new MaskSet object built from the image of a mask at path
     */
    public  MaskSet(String path, boolean is4Connected){
        Mat mat = Imgcodecs.imread(path, 0);
        hole = new HashSet<>();
        border = new HashSet<>();

        int rows = mat.rows();
        int cols = mat.cols();

        // For each point check if the value is <0.5 (<127.5 without normalization)
        // If it is, add it to the hole collection.

        for (int i = 1; i < rows - 1; i++) { //No need to address holes that touch the boundaries
            for (int j = 1; j < cols - 1; j++) { //No need to address holes that touch the boundaries
                if (mat.get(i, j)[0] < FloatImage.THRESHOLD)
                    hole.add(new Coordinate(i, j));
            }
        }

        // Instead of checking all coordinates again for borders, we will only check coordinates
        // that are neighbors of known hole points

        for (Coordinate c : hole) {
            int x = c.getX();
            int y = c.getY();

            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++){
                    boolean isCorner = (Math.abs(dx) + Math.abs(dy)) == 2;
                    boolean isCenter = (dx == 0) && (dy == 0);
                    boolean isLegalNeighbor = !(isCorner && is4Connected);
                    boolean isHole = (mat.get(x + dx, y + dy)[0] < FloatImage.THRESHOLD);

                    if (isLegalNeighbor && !isCenter && !isHole) {
                        border.add(new Coordinate(x + dx, y + dy));
                    }
                }
            }
        }
    }



}
