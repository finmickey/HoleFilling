package org;

public class DefaultWeightFunction implements WeightFunction {

    @Override
    public float calculate(Coordinate u, Coordinate v) {
        float e = 0.01f;
        return (float) (1 / (e + (Math.pow(Math.pow(u.getX() - v.getX(), 2) + Math.pow(u.getY() - v.getY(), 2), 1.5))));
    }
}
