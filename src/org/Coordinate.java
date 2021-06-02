package org;

import java.util.Objects;

/**
 * Holds a coordinate of a single point in the image
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
