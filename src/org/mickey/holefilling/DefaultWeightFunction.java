package org.mickey.holefilling;

public class DefaultWeightFunction implements WeightFunction {

    final float e = 0.01f;
    final double z = 3;

    /**
     *
     * @param u coordinate of the first point
     * @param v coordinate of the second point
     * @return the result of the default weight function as follows:
     * 1 / (|u-v|^z + epsilon) where z=3, epsilon = 0.01
     */
    @Override
    public float calculate(Coordinate u, Coordinate v) {
        float result = (float) (
                1 / (
                        e + (Math.pow(Math.pow(u.getX() - v.getX(), 2) + Math.pow(u.getY() - v.getY(), 2), z/2))
                )
        );
        assert (result>=0);
        return result;
    }
}
