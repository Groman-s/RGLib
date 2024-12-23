package com.goyanov.rglib;

public class ProbableObject<T>
{
    private final T object;
    private final double probability;

    public ProbableObject(T object, double probability)
    {
        this.object = object;
        this.probability = probability;
    }

    public T getObject()
    {
        return object;
    }

    public double getProbability()
    {
        return probability;
    }
}
