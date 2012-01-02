import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.SVGAnimationEngine;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;

import org.w3c.dom.svg.SVGDocument;

/**
 * This was supposed to be a proof of concept for rendering a
 * complex SVG animation a frame at a time (so the frames could
 * then be combined into a movie with ffmpeg).
 *
 * Unfortunately it turns out that Batik is too bloated and slow
 * to do this effectively. It cannot render even a single frame
 * of a not-especially-complicated animation that Safari can render
 * at 1fps or so.
 *
 * We shall have to cut out the middle-man and render the frames
 * directly to PNG. I’m going to try using Cairo.
 *
 * I don’t blame Java; I blame the programming culture that has
 * grown up around it, that prizes abstraction to the detriment of
 * simplicity and speed.
 */
public class RenderAnimation {
    public static void main(String[] args)
        throws IOException
    {
        render(args[0]);
    }

    // Thanks to Oliver Mihatsch on batik-users-xmlgraphics.apache.org
    private static void render(String uri)
        throws IOException
    {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        SVGDocument doc = f.createSVGDocument(uri); 
        UserAgentAdapter userAgent = new UserAgentAdapter();
        BridgeContext ctx = new BridgeContext(userAgent);

        // parse document
        GVTBuilder builder = new GVTBuilder();
        GraphicsNode gvtRoot = null;
        ctx.setDynamicState(BridgeContext.DYNAMIC);
        gvtRoot = builder.build(ctx, doc);

        // create animation engine
        ctx.setAnimationLimitingNone();
        SVGAnimationEngine animationEngine = ctx.getAnimationEngine();
        
        // draw image
        if(!animationEngine.hasStarted()) {
            animationEngine.start(0);
        }
        animationEngine.setCurrentTime(1000);
        
        Dimension2D documentSize = ctx.getDocumentSize();
        int width = (int) documentSize.getWidth();
        int height = (int) documentSize.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = GraphicsUtil.createGraphics(bufferedImage);
        gvtRoot.paint(g2d);
        g2d.dispose();
        
        ImageIO.write(bufferedImage, "PNG", System.out);
    }
}
