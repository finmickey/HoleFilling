package org;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Holds the mask information as HashSets of the hole coordinates and border coordinates
 */
public class MaskSet {
    private final HashSet<Coordinate> hole;
    private final HashSet<Coordinate> border;

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

    /**
     *
     * @param path = path to the mask file
     * @param is4Connected = indicator of the type of connectivity. True = 4 connected, False = 8 connected
     * @return A new MaskSet object built from the image of a mask at path
     */
    public static MaskSet readMaskImage(String path, boolean is4Connected) {
        Mat mat = Imgcodecs.imread(path, 0);
        HashSet<Coordinate> hole = new HashSet<>();
        HashSet<Coordinate> border = new HashSet<>();

        int rows = mat.rows();
        int cols = mat.cols();

        // For each point check if the value is <0.5 (<127.5 without normalization)
        // If it is, add it to the hole collection.

        for (int i = 1; i < rows - 1; i++) { //No need to address holes that touch the boundaries
            for (int j = 1; j < cols - 1; j++) { //No need to address holes that touch the boundaries
                if (mat.get(i, j)[0] < 127.5)
                    hole.add(new Coordinate(i, j));
            }
        }

        // Instead of checking all coordinates again for borders, we will only check coordinates
        // that are neighbors of known hole points

        for (Coordinate c : hole) {
            int x = c.getX();
            int y = c.getY();

            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++)
                    if (!(((Math.abs(dx) + Math.abs(dy)) == 2) && is4Connected)) //Check if the square is legal under neighbor's constrains
                        if ((mat.get(x + dx, y + dy)[0] > 127.5) && (!((dx == 0) && (dy == 0)))) //Check if not centre square and is not a hole itself
                            border.add(new Coordinate(x + dx, y + dy));
            }
        }

        return new MaskSet(hole, border);
    }

    /*public LinkedHashSet<Coordinate> orderedBorder(boolean is4Connected){
        LinkedHashSet<Coordinate> borderLinkedHashSet = new LinkedHashSet<Coordinate>();
        Coordinate start = border.iterator().next();

        borderLinkedHashSet.add(start);
        while(true){
            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++)
                    if (!(((Math.abs(dx) + Math.abs(dy)) == 2) && is4Connected)) //Check if the square is legal under neighbor's constrains
                        if (!((dx == 0) && (dy == 0))) //Check if not centre square and is not a hole itself
                            border.add(new Coordinate(x + dx, y + dy));
        }
        return borderLinkedHashSet;
    }

     */
}
