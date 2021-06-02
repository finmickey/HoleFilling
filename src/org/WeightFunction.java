package org;

/**
 * Every weight function will follow this interface
 * Takes two coordinates and returns the calculation value as a float
 */
public interface WeightFunction {

    float calculate(Coordinate u, Coordinate v);
}
