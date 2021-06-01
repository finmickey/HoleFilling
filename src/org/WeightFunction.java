package org;

public interface WeightFunction {
    //@TODO: how to make this take any number of params?
    float calculate(Coordinate u, Coordinate v);
}
