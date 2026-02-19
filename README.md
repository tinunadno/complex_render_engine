# Example

<video width="800" autoplay loop muted playsinline poster="https://github.com/tinunadno/complex_render_engine/tree/master/contents/output_render.png">
  <source src="https://github.com/tinunadno/complex_render_engine/tree/master/contents/output_video.mp4" type="video/mp4">
  Ваш браузер не поддерживает видео.
</video>

# Implementation & settings description

Engine settings:
  in RenderWrapper class:
    constructor:
        PS you shouldnt change its structure, just change values
      
      public RenderWrapper() {
          BufferedImage screen = new BufferedImage(Main.SCREEN_X_SIZE, Main.SCREEN_Y_SIZE, BufferedImage.TYPE_INT_RGB);

          complexFunction = new MondelbrotSetFunction();
          //set your function class
          ColorGradient colorGradient = new ColorGradient1();
          //set your gradient class
          biRender = new BIRender(screen, complexFunction, 100, 50, new Complex(), 5, colorGradient);
          //BIRender(BufferedImage screen, ComplexFunction complexFunction, double scale, int iterationCount, Complex displacement, double range, ColorGradient colorGradient)
          //scale - is a scale(the bigger the scale the closer you look); displacement - displacing from center; range - |lim(your function)| < range
          userInterface = new UserInterface();
      }
    
in init_render() you can setup your animation, example in java files

!!!IMPORTANT!!! to render your animation, or single frame t file, use VideoWriter and ImageWriter class, for video before rendering call init function, 
then add each frame with recordFrame(BufferedImage) and write to file with stopRecording

!!!IMPORTANT!!! all *now rendering videos* saving automatically on ctrl-c interuption, in the middle of the output video
    
in biRender clas you can set up function output value, then it will map to RGB color on image in postProcessing function:

  example:
    
    if (z.module() <= range) preRender[i][j] = minModule*5;
    else preRender[i][j] =  iter/(double)iterationCount;

to set up your gradient create class extends ColorGradient and create an array of color points

see the example in colorGradient1, you can just take valuetoGradient for linear interpolation between colors

example:

        colorRamp = new ColorPoint[3];
        colorRamp[2] = new ColorPoint((short) 251, (short) 229, (short) 28, 1);
        colorRamp[1] = new ColorPoint((short) 251, (short) 0, (short) 28, 0.5);
        colorRamp[0] = new ColorPoint((short) 70, (short) 0, (short) 92, 0);

  values from biRender will map to this gradient from [min_function_uotput, max_function_uotput] to [0, 1]

    (70;0;92)  (251;0;28)  (251;229;28)

        |======---------_______|
    
        0.0       0.5          1.0
  
you can also create your own Complex function, you need to create ComplexFunction extends class
example:

    public class JustAFunction extends ComplexFunction{
      @Override
      public Complex executeFunction(Complex z, Complex c) {
          return c.pow(z.tan());
      }
    }
  
you can see all available functions in Complex class, there are base, trigonometric, hyperbolic, inverse trigonometric, inverse hyperbolic
