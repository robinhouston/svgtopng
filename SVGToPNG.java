import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

/**
 * This class is designed to be invoked by the svgtopng script.
 *
 * It is based on the example code at http://xmlgraphics.apache.org/batik/using/transcoder.html
 */
public class SVGToPNG {

    public static void main(String[] args)
      throws TranscoderException, FileNotFoundException
    {
        new SVGToPNG(args).transcode();
    }
    
    public SVGToPNG(String[] args)
        throws java.io.FileNotFoundException
    {
        parseArgs(args);
    }
    
    public static final String VERSION = "1.0";

    private String scriptName;
    private String inputFilename;
    private OutputStream outputStream;
    private boolean force = false;

    private void die(String format, Object... params)
    {
        String errorMessage = String.format(format, params);
        System.err.printf("%s: %s\n", scriptName, errorMessage);
        System.exit(1);
    }

    private void printUsage()
    {
        System.err.printf("Usage: %s [options] file.svg\n", scriptName);
        System.err.printf("\t-o filename  write the PNG to the named file\n");
        System.err.printf("\t-o -         write the PNG to stdout\n");
        System.err.printf("\t-f           overwrite the output file if it already exists\n");
        System.err.printf("\n");
        System.err.printf("\t-h           display this help text\n");
        System.err.printf("\t-v           print the version number\n");
    }

    private void dieUsage()
    {
        printUsage();
        System.exit(1);
    }

    private void printVersion()
    {
        System.out.printf("SVG to PNG, version %s", VERSION);
    }

    private void parseArgs(String[] args)
        throws FileNotFoundException
    {
        scriptName = args[0];
        String outputFilename = null;
        
        int i;
        for (i=1; i < args.length - 1; ++i) {
            if (!args[i].startsWith("-")) {
                break;
            }
            else if (args[i].equals("--")) {
                i++;
                break;
            }
            else if (args[i].equals("-f")) {
                force = true;
            }
            else if (args[i].equals("-o")) {
                if (args.length <= i+1) dieUsage();
                outputFilename = args[++i];
                
            }
            else if (args[i].equals("-h")) {
                printUsage();
                System.exit(0);
            }
            else if (args[i].equals("-v")) {
                printVersion();
                System.exit(0);
            }
            else {
                die("Unrecognised option: %s", args[i]);
            }
        }
        
        inputFilename = args[i];
        if (args.length > i+1)
            die("Unexpected command-line argument: %s", args[i+1]);
        
        if (outputFilename == null) {
            if (inputFilename.endsWith(".svg")) {
              outputFilename = inputFilename.substring(0, inputFilename.length() - 4) + ".png";
            }
            else {
              outputFilename = inputFilename + ".png";
            }
        }
        if (outputFilename == "-") {
            outputStream = System.out;
        }
        else {
            if (!force && new File(outputFilename).exists()) {
                die("Output file already exists (and -f not specified): %s", outputFilename);
            }
            outputStream = new FileOutputStream(outputFilename);
        }
    }
    
    private void transcode()
        throws TranscoderException
    {
        // Create a JPEG transcoder
        ImageTranscoder t = new PNGTranscoder();

        // Create the transcoder input.
        String svgURI = new File(inputFilename).toURI().toString();
        TranscoderInput input = new TranscoderInput(svgURI);

        // Create the transcoder output.
        TranscoderOutput output = new TranscoderOutput(outputStream);

        // Save the image.
        t.transcode(input, output);
    }
}
