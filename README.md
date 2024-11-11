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
    
in biRender clas you can set up function output value:
  example:
  if (z.module() <= range) preRender[i][j] = minModule*5;
  else preRender[i][j] =  iter/(double)iterationCount;

  
you can also create your own function, you need to create ComplexFunction extends class
example:
  public class JustAFunction extends ComplexFunction{

    @Override
    public Complex executeFunction(Complex z, Complex c) {
        return c.pow(z.tan());
    }
  }
  you can see all available functions in Complex class, there are base, trigonometric, hyperbolic, inverse trigonometric, inverse hyperbolic
