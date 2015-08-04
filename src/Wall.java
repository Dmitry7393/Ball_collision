import java.awt.Image;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
public class Wall {
	   private Image image;
	   public double x;
	   public double y;
	   public int count_block;
	   public int direction;
	   public double start_x;
	   public double start_y;
	   public double end_x;
	   public double end_y;
	   public double v_x;
	   public double v_y;
	   private int i = 0;
	  public  int[] vector_line;
	   public boolean move_platform = true; 
	   
	  Wall()
	 {
	     ImageIcon ii = new ImageIcon(this.getClass().getResource("Data/Image/wall2.png"));
	     image = ii.getImage();
	     vector_line = new int[15];
	 }

 public Image getImage() {
     return image;
 }
 public void Add(int n)
 {
	 vector_line[i] = n;
	 i++;
 }
 public void i_4()
 {
	 i = 4;
 }
 public int get_index_line(int h)
 {
	 return vector_line[h];
 }
}
