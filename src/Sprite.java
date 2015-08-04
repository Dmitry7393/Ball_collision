import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
 
public class Sprite{
 
    private String sprite = "Data/Image/ball300.png"; 
    public  double x;
    public  double y;
    public int radius = 30;
    public boolean visible ;
    public int center_spr_x;
    public int center_spr_y;
    public  double v_x ;
    public  double v_y ; 
    public double start_x;
    public double start_y;
    public  double start_v_x ;
    public  double start_v_y ; 
    public boolean allow_move = false;
    private Image image;
    AffineTransform affine ;
    public Sprite() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
        x = 0;
        y = 0;
    }
    public AffineTransform scale()
    {
    	   affine = new AffineTransform();
    	//   affine.scale(1.0/2.0, 1.0/2.0);
    	  //affine.setToScale(1.0/2.0, 1.0/2.0);
    	 // affine.translate(dx, dy);
          return affine;   
    }
  public double get_spr_w()
  {
	  return image.getWidth(null);
  }
  public double get_spr_h()
  {
	  return image.getHeight(null);
  }
    public Image getImage() {
        return image;
    }

}