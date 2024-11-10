package org.ComplexRenderEngine.RenderEngine.Gradient;

public class ColorPoint {
    private short r;
    private short g;
    private short b;
    private double point;

    public ColorPoint(short r, short g, short b, double point) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.point = point;
    }

    public int getMedian(ColorPoint c2, double value) {
        double k = (value - this.point) / (c2.point - this.point); // normalized value
        short r_ = (short) (this.r+(c2.r - this.r) * k);
        short g_ = (short) (this.g+(c2.g - this.g ) * k);
        short b_ = (short) (this.b+(c2.b - this.b ) * k);
        int temp = colorToInt(r_, g_, b_);
        return colorToInt(r_, g_, b_);
    }

    private int colorToInt(short r, short g, short b) {
        return (r << 16) | (g << 8) | b;
    }

    public int getColor() {
        return colorToInt(this.r, this.g, this.b);
    }

    public double getPoint() {
        return point;
    }
}
