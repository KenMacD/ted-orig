package ted.ui.addshowdialog;

import java.awt.*;
import java.awt.image.*;

public class ImageCanvas extends Canvas {
  Image image;

  public ImageCanvas(String name) {
    MediaTracker media = new MediaTracker(this);
    image = Toolkit.getDefaultToolkit().getImage(name);
    media.addImage(image, 0);
    try {
      media.waitForID(0);  
      }
    catch (Exception e) {}
    }

  public ImageCanvas(ImageProducer imageProducer) {
    image = createImage(imageProducer);
    }

  public void paint(Graphics g) {
    g.drawImage(image, 0,0, this);
    }
}