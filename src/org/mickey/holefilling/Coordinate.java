package org.mickey.holefilling;

import java.util.Objects;

/**
 * Holds a coordinate of a single point in the image
 */

//@TODO: protected or private?
public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        assert(x>=0);
        assert (y>=0);

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    /**
     * Overides the hash function of Coordinate to identify it by (X,Y) and not by the reference address
     * @return hash value of (X,Y)
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
