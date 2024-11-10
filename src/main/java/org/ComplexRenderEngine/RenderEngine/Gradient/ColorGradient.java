package org.ComplexRenderEngine.RenderEngine.Gradient;

public abstract class ColorGradient {
    public abstract int valueToGradient(double value);
    public abstract void setMaxValue(double max);
    public abstract void setMinValue(double min);
}
