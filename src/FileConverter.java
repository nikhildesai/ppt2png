import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class FileConverter {

    public void convert(String file, float scale, int slidenum) throws IOException, InvalidFormatException {

        System.out.println("Processing " + file);
        XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(file));

        Dimension pgsize = ppt.getPageSize();
        int width = (int) (pgsize.width * scale);
        int height = (int) (pgsize.height * scale);

        XSLFSlide[] slide = ppt.getSlides();
        for (int i = 0; i < slide.length; i++) {
            if (slidenum != -1 && slidenum != (i + 1))
                continue;

            String title = slide[i].getTitle();
            System.out.println("Rendering slide " + (i + 1) + (title == null ? "" : ": " + title));

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();

            // default rendering options
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

            graphics.setColor(Color.white);
            graphics.clearRect(0, 0, width, height);

            graphics.scale(scale, scale);

            // draw stuff
            slide[i].draw(graphics);

            // save the result
            int sep = file.lastIndexOf(".");
            String fname = file.substring(0, sep == -1 ? file.length() : sep) + "-" + (i + 1) + ".png";
            FileOutputStream out = new FileOutputStream(fname);
            ImageIO.write(img, "png", out);
            out.close();
        }
        System.out.println("Done");
    }

}
