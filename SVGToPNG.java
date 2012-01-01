import java.io.File;
import java.io.OutputStream;

import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

public class SVGToPNG {
  
  public static void main(String[] args)
    throws org.apache.batik.transcoder.TranscoderException
  {
        String scriptName = args[0];
        if (args.length != 2) {
            System.err.printf("Usage: %s file.svg > file.png\n", scriptName);
            System.exit(1);
        }
        String inputFilename = args[1];
        
        // Create a JPEG transcoder
        ImageTranscoder t = new PNGTranscoder();

        // Create the transcoder input.
        String svgURI = new File(inputFilename).toURI().toString();
        TranscoderInput input = new TranscoderInput(svgURI);

        // Create the transcoder output.
        TranscoderOutput output = new TranscoderOutput(System.out);

        // Save the image.
        t.transcode(input, output);

        // Flush and close the stream.
        // ostream.flush();
        // ostream.close();
        // System.exit(0);
  }
}
