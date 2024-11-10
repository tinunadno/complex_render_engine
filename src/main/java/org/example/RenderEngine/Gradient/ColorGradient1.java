package org.example.RenderEngine.Gradient;

public class ColorGradient1 extends ColorGradient{

    private double max;

    private ColorPoint[] colorRamp;

    public ColorGradient1(){
        //#46015c
        colorRamp = new ColorPoint[2];
        colorRamp[1]= new ColorPoint((short)251, (short)229, (short)28,   1);

        colorRamp[0]= new ColorPoint((short)70, (short)0, (short)92, 0);

    }

    @Override
    public int valueToGradient(double value){
        value/=max;
        if(value >= 1)
            return colorRamp[colorRamp.length-1].getColor();
        if(value <= 0)
            return colorRamp[0].getColor();
        for(int i=0; i<colorRamp.length-1; i++){
            if(value >= colorRamp[i].getPoint() && value <= colorRamp[i+1].getPoint()){
                return colorRamp[i].getMedian(colorRamp[i+1], value);
            }
        }
        return 0;
    }

    public void setMaxValue(double max){
        this.max=max;
    }
}
