import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
 
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
public class GameScreen extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1L;
        private Color cyan;
        private Color color_white;
        private Font font;
        private Image fon;
        private Image wall_image;
    	Line temp_line;
        private Vector<Line> line;
        private Vector<Pointer> pointer;
        private Line l;
        private int level = 1;
        private boolean game_mode = false ;
        private boolean left_mouse_pressed = false; 
        private boolean right_mouse_pressed = false;
        private Timer timer; 
        private int count_add_line = 0;
        private Vector<Sprite> ball;
        private Vector<Wall> wall;
        private Vector<Border> border;
        private Basket basket;
        private int mouse_x ;
        private int mouse_y ;
        private int number_point = 0;
        private boolean point1 = true;
        Collision collision = new Collision();
    public void Add_Line(double x1, double y1, double x2, double y2, boolean visible, boolean removable, boolean move_by_right_mouse)
    {
    	temp_line = new Line();
	  	temp_line.line_x1 =  x1;  temp_line.line_y1 = y1;
	  	temp_line.line_x2 =  x2;  temp_line.line_y2 = y2;
	  	if(temp_line.line_x1 == temp_line.line_x2) temp_line.line_x2 += 0.01;
	  	if(temp_line.line_y1 == temp_line.line_y2) temp_line.line_y2 += 0.01;
	  	temp_line.visible = visible;
	  	temp_line.removable = removable;
	  	temp_line.move_by_right_mouse = move_by_right_mouse;
	  	line.addElement(temp_line);
    }
    public void add_pointer(double x1, double y1, double v_x, double v_y, int k)
    {
    	Pointer p = new Pointer();
    	Pointer str = new Pointer();
    	Pointer str2 = new Pointer();
    	p.x1 = x1 + v_x*20;
    	p.y1 = y1 + v_y*20;
    	p.x2 = p.x1 + v_x * 27;
    	p.y2 = p.y1 + v_y * 27;
		 double s3 = distance_two_point(p.x1, p.y1, p.x2, p.y2);
		 double s2 = distance_two_point(p.x2, p.y1, p.x2, p.y2);
		 double angle = Math.asin(s2 / s3);
	    //System.out.println("angle = " + angle * 180 / 3.14);
		 double dx = Math.sin(angle) * 10.1;
		 double dy = Math.cos(angle) * 10.1;
		 if(p.x2 > p.x1 && p.y2 > p.y1 || p.x1 > p.x2 && p.y1 > p.y2)
		   {
		       str.x1 = p.x2-dx - v_x*5; str.y1 = p.y2+dy - v_y*5; 
		       str.x2 = p.x2;  str.y2 = p.y2;
		       pointer.addElement(str);
		       str2.x1 = p.x2+dx - v_x*5; str2.y1 = p.y2-dy - v_y*5;
		       str2.x2 = p.x2;  str2.y2 = p.y2; 
		       pointer.addElement(str2);
		   }
		   else
		   {
		       str.x1 = p.x2-dx - v_x*5; str.y1 =  p.y2-dy - v_y*5;
		       str.x2 = p.x2; str.y2 = p.y2;
		       pointer.addElement(str);
		       str2.x1 = p.x2+dx - v_x*5; str2.y1 =  p.y2+dy - v_y*5;
		       str2.x2 = p.x2; str2.y2 = p.y2;
		       pointer.addElement(str2);
		   }
    	pointer.addElement(p);
    }
    public void Add_ball(double x1, double y1, int radius, double v_x, double v_y, boolean visible)
    {
    	Sprite s = new Sprite();
    	s.x = x1;
    	s.start_x = x1;
    	s.y = y1; 
    	s.start_y = y1;
    	s.v_x = v_x;
    	s.start_v_x = v_x;
    	s.v_y = v_y;
    	s.start_v_y = v_y;
    	s.radius = radius;
    	s.visible = true;
    	ball.addElement(s);
        ball.get(ball.size()-1).center_spr_x = (int)ball.get(ball.size()-1).x+ball.get(ball.size()-1).radius;
        ball.get(ball.size()-1).center_spr_y = (int)ball.get(ball.size()-1).y+ball.get(ball.size()-1).radius;
    	add_pointer(ball.get(ball.size()-1).center_spr_x, ball.get(ball.size()-1).center_spr_y, ball.get(ball.size()-1).v_x, ball.get(ball.size()-1).v_y, ball.size()-1);
    }
    public void Add_invisible_line(int x, int y, int count_block, int direction)
    {
    	if(direction == 0)
    	{
  		  Add_Line(x, y, x+40*count_block, y, false, false, false);
  		  Add_Line(x, y, x,  y+40, false, false, false);
  		  Add_Line(x,  y+40, x+40*count_block, y+40, false, false, false);
  		  Add_Line(x+40*count_block,  y, x+40*count_block, y+40, false, false, false);
    	}
    	if(direction == 1)
    	{
    		 Add_Line(x, y, x+40, y, false, false, false);
    		 Add_Line(x, y, x, y+40*count_block, false, false, false);
    		 Add_Line(x, y+40*count_block, x+40, y+40*count_block, false, false, false);
    		 Add_Line(x+40, y, x+40, y+40*count_block, false, false, false);
    	}
    }
    public void Add_wall(int x, int y, int count_block, int direction, double end_x, double end_y,  double speed_x, double speed_y, boolean move_platform)
    {
    	  Wall w = new Wall();
    	  w.x = x;
    	  w.y = y;
    	  w.count_block = count_block;
    	  w.direction = direction;
    	  w.move_platform = move_platform;
    	  w.start_x = x;
    	  w.start_y = y;
    	  w.end_x = end_x;
    	  w.end_y = end_y;
    	  w.v_x = speed_x;
    	  w.v_y = speed_y;
    	  Add_invisible_line(x, y, count_block, direction); //for 4 side
    	  line.get(line.size()-1).apply_move_block = true;
    	  line.get(line.size()-2).apply_move_block = true;
    	  line.get(line.size()-3).apply_move_block = true;
    	  line.get(line.size()-4).apply_move_block = true;
    	  w.Add(line.size()-4);
    	  w.Add(line.size()-3);
    	  w.Add(line.size()-2);
    	  w.Add(line.size()-1);

    	  wall.addElement(w);
    }
    public void Add_basket(int x, int y, int width, int height)
    {
    	basket.x = x;
    	basket.y = y;
    	basket.width = width;
    	basket.height = height;
    }
	public GameScreen()
	{      
	    addKeyListener(new TAdapter());
	    addMouseListener(new CustomListener());
	    addMouseMotionListener(new CustomListener());
	    setFocusable(true);
	    setBackground(Color.BLACK);
	    setDoubleBuffered(true);
	    ball = new Vector<Sprite>();
	    line = new Vector<Line>();
	    border = new Vector<Border>();
	    pointer = new Vector<Pointer>();
	    //ball.addElement(spr1);
	    timer = new Timer(5, this);
	    timer.start();
	    wall = new Vector<Wall>();
		 cyan = new Color(171,	205,	239);
		// color_circle1 = new Color(255, 0, 0);
		 color_white = new Color(255,255,255);
		 font = new Font("Tahoma", Font.BOLD|Font.ITALIC, 20);
	      ImageIcon ii = new ImageIcon(this.getClass().getResource("Data/Image/fon.png"));
	      fon = ii.getImage();
	      ImageIcon ii2 = new ImageIcon(this.getClass().getResource("Data/Image/wall.png"));
	      wall_image = ii2.getImage();
	      //////////////////////////
	      border.addElement(new Border(20, 20, 1002,20));
	      border.addElement(new Border(20, 20, 20 ,560));
	      border.addElement(new Border(20, 552, 1002, 552));
	      border.addElement(new Border(1002, 20, 1002, 560));
	      basket = new Basket();
		  start_game();
	}
	public  void Split_str(String s1)
    {
           String[] arr = s1.split(" ");  
           for(int i = 0; i < arr.length; i++)
           {
        	   if(arr[0].equals("B"))
        	   {
        		   if(i == 5)
        		   Add_ball(Double.valueOf(arr[1]), Double.valueOf(arr[2]),  Integer.valueOf(arr[5]), Double.valueOf(arr[3]), Double.valueOf(arr[4]), true);   
        	   }
        	   if(arr[0].equals("W"))
        	   {
        		   if(i == 4)
        		   Add_wall(Integer.valueOf(arr[1]), Integer.valueOf(arr[2]), Integer.valueOf(arr[3]), Integer.valueOf(arr[4]), 0, 0, 0, 0,  false);
        	   }
        	   if(arr[0].equals("M"))
        	   {
        		   if(i == 8)
            	   Add_wall(Integer.valueOf(arr[1]), Integer.valueOf(arr[2]), Integer.valueOf(arr[3]), Integer.valueOf(arr[4]), 
            			   Integer.valueOf(arr[5]), Integer.valueOf(arr[6]), Double.valueOf(arr[7]), Double.valueOf(arr[8]),  true);
            	    
        	   }
        	   if(arr[0].equals("E"))
        	   {
        		   if(i == 4)
        		   Add_basket(Integer.valueOf(arr[1]),Integer.valueOf(arr[2]),Integer.valueOf(arr[3]),Integer.valueOf(arr[4]));
        	   }
           }           
           game_mode = false;
    }
	public void Load_level(String n_level)
	{
	       BufferedReader br = null;
	        try
	        {
	                String sCurrentLine;
	                br = new BufferedReader(new FileReader(n_level));
	                while ((sCurrentLine = br.readLine()) != null)
	                {
	                        Split_str(sCurrentLine);
	                }
	        } catch (IOException e)
	        {
	                e.printStackTrace();
	        }
	        finally
	        {
	                try
	                {
	                        if (br != null)br.close();
	                }
	                catch (IOException ex)
	                {
	                        ex.printStackTrace();
	                }
	        }
	}
	public void start_game()
	{
		level = 1;
		clear_screen();
		String str = "Data/level/level" + level + ".txt";
		Load_level(str);
	}
	public void clear_screen()
	{
		line.removeAllElements();
		ball.removeAllElements();
		wall.removeAllElements();
		pointer.removeAllElements();
	}
	public void next_level()
	{
		level++;
		String str = "Data/level/level" + level + ".txt";
		clear_screen();
		Load_level(str);
	}
	public void calculation()
	{
         for(int k = 0; k < ball.size(); k++)  // ball.size()
		 {
		        ball.get(k).center_spr_x = (int)ball.get(k).x+ball.get(k).radius;
		        ball.get(k).center_spr_y = (int)ball.get(k).y+ball.get(k).radius;
		 }
		 for(int k = 0; k < ball.size(); k++)  // ball.size()
		 {
	        if(ball.get(k).allow_move == true)
	        {
			      ball.get(k).x =  ball.get(k).x + ball.get(k).v_x;
			      ball.get(k).y =  ball.get(k).y + ball.get(k).v_y;
	        }
		 }
	 	
		 //Define collision with_border
		 if(level <= 2)
		 {
			 for(int k = 0; k < ball.size(); k++)
			 {
				 for(int t = 0; t < border.size(); t++)
				 {
					   if(collision.collision_ball_line(ball.get(k).radius,(int)ball.get(k).x+ball.get(k).radius,
			                    (int)ball.get(k).y+ball.get(k).radius, border.get(t).x1, border.get(t).y1, border.get(t).x2, border.get(t).y2, ball.get(k).radius) == true)
					     {
						       change_direction(border.get(t).x1, border.get(t).y1, border.get(t).x2, border.get(t).y2, k);
					     }
				 }

			 }
		  }
			 for(int i = 0; i < line.size(); i++)
			 {
				 for(int k = 0; k < ball.size(); k++)
			     {
		             if(line.get(i).line_x1 != line.get(i).line_x2 && line.get(i).line_y1 != line.get(i).line_y2)
		             {
		                line.get(i).distance1 = Math.sqrt( (line.get(i).line_x1-ball.get(k).center_spr_x)*(line.get(i).line_x1-ball.get(k).center_spr_x) +
		                        (line.get(i).line_y1-ball.get(k).center_spr_y)*(line.get(i).line_y1-ball.get(k).center_spr_y) );
		                line.get(i).distance2 = Math.sqrt( (line.get(i).line_x2-ball.get(k).center_spr_x)*(line.get(i).line_x2-ball.get(k).center_spr_x) +
		                        (line.get(i).line_y2-ball.get(k).center_spr_y)*(line.get(i).line_y2-ball.get(k).center_spr_y) );
			              if(line.get(i).distance1 < line.get(i).distance2) //сперва должна идти 1-я точка потом 2-яна линии
			                {
		                        if(collision.collision_ball_line(ball.get(k).radius,(int)ball.get(k).x+ball.get(k).radius, (int)ball.get(k).y+ball.get(k).radius, (int)line.get(i).line_x1,  
		                                (int)line.get(i).line_y1,  (int)line.get(i).line_x2,  (int)line.get(i).line_y2, ball.get(k).radius) == true)
		                        {
		                        	change_direction((int)line.get(i).line_x1,  
		                                                (int)line.get(i).line_y1,  (int)line.get(i).line_x2,  (int)line.get(i).line_y2, k);
		                        }
			                }
			                else //2
			                {
		                         if(collision.collision_ball_line(ball.get(k).radius,(int)ball.get(k).x+ball.get(k).radius, (int)ball.get(k).y+ball.get(k).radius,
		                                            (int)line.get(i).line_x2, (int)line.get(i).line_y2,  (int)line.get(i).line_x1,  (int)line.get(i).line_y1, ball.get(k).radius) == true)
		                         {                
		                        	 change_direction((int)line.get(i).line_x2, (int)line.get(i).line_y2,  (int)line.get(i).line_x1,  (int)line.get(i).line_y1, k);
		                         }
			                }
		               }
			  } //end cycle line
		 }  //end cycle ball
		 	for(int k = 0; k < ball.size(); k++)  // ball.size()
		    {
	           if(ball.get(k).x >= basket.x && ball.get(k).y >= basket.y &&
	                                ball.get(k).x+ball.get(k).radius*2 <= basket.x + basket.width &&
	                                ball.get(k).y+ball.get(k).radius*2 <= basket.y + basket.height)
               {      
	        	   ball.get(k).visible = false;
               }
	         }
		 	for(int k = 0; k < ball.size(); k++)
		    {
		 		if(ball.get(k).visible == true) break;
		 		if(k == ball.size()-1)
		 		{
		 			System.out.println("Level " + level + " completed!");
		 			JOptionPane.showMessageDialog(null, "Level " + level + " completed!");
		 			if(level <= 2)
		 			next_level();
		 			else
		 			{
		 				JOptionPane.showMessageDialog(null, "All level completed!!! Congratulations!!!");
		 				start_game();
		 			}
		 		}	
		    } 
        for(int k = 0; k < wall.size(); k++)
 	 	 {
 	 		if(wall.get(k).move_platform == true)
 	 		{
 	 			if(wall.get(k).x > wall.get(k).end_x || wall.get(k).x < wall.get(k).start_x)  wall.get(k).v_x = -wall.get(k).v_x;
 	 			if(wall.get(k).y > wall.get(k).end_y || wall.get(k).y < wall.get(k).start_y)  wall.get(k).v_y = -wall.get(k).v_y;
 	 			
 	 			wall.get(k).x +=  wall.get(k).v_x;
 	 			wall.get(k).y +=  wall.get(k).v_y;
 	 			int count_line = 4;
 	 			if(game_mode == true)
 	 			{
 	 				count_line = 12;
 	 			}
 	 			
 	 			for(int h = 0; h < count_line; h++)
 	 			{
 		 			line.get(wall.get(k).get_index_line(h)).line_x1   +=  wall.get(k).v_x;
 		 			line.get(wall.get(k).get_index_line(h)).line_x2   +=  wall.get(k).v_x;
 		 			line.get(wall.get(k).get_index_line(h)).line_y1   +=  wall.get(k).v_y;
 		 			line.get(wall.get(k).get_index_line(h)).line_y2   +=  wall.get(k).v_y;
 		 			//System.out.println(" " + wall.get(k).get_index_line(h));
 	 			}	
 	 		}
 	 	 }

	}
		public void paint(Graphics g)
		{
		        super.paint(g);
		        g.setFont(font);
		        Graphics2D g2 = (Graphics2D) g;
		        //Draw background
		        g.drawImage(fon, 0, 0, 1024, 600, null);
			    //Draw Border
		        if(level <= 2)
		        {
				    for(int i = 0; i < 1024; i += 20)
				    {
				            g.drawImage(wall_image, i, 0, 20, 20, null);
				            g.drawImage(wall_image, i, 552, 20, 20, null);
				    }
				   
				    for(int j = 0; j < 552; j += 20)
				    {
				            g.drawImage(wall_image, 0, j, 20, 20, null);
				            g.drawImage(wall_image, 999, j, 20, 20, null);
				    }
		        }
			    //Draw wall
			    for(int i = 0; i < wall.size(); i++)
			    {
			    	if(wall.get(i).direction == 0)
			    	{
			    		int temp_x_wall = (int)wall.get(i).x;
			    		for(int k = 0; k < wall.get(i).count_block; k++)
			    		{	 
			    			g.drawImage(wall.get(i).getImage(), temp_x_wall, (int)wall.get(i).y, 40, 40, null);  //draw block wall
			    			temp_x_wall += 40;
			    		}	
			    	}
			    	if(wall.get(i).direction == 1)
			    	{
			    		int temp_y_wall = (int)wall.get(i).y;
			    		for(int k = 0; k < wall.get(i).count_block; k++)
			    		{
			    			g.drawImage(wall.get(i).getImage(), (int)wall.get(i).x, temp_y_wall, 40, 40, null);  //draw block wall
			    			temp_y_wall += 40;
			    		}	
			    	}
			    }
                g.setColor(color_white);
                g2.setStroke(new BasicStroke(3.0f));
                //Draw basket
                g.drawRect(basket.x, basket.y, basket.width,  basket.height);	
                g.setColor(color_white);
                g2.setStroke(new BasicStroke(5.0f));  // толщина равна 5
		    
			    if(left_mouse_pressed == true)
			   	g.drawLine((int)l.line_x1, (int)l.line_y1, (int)l.line_x2, (int)l.line_y2);
			    
			    for(int i = 0; i < line.size(); i++)
			    {
			    	if(line.get(i).visible == true)
			        g.drawLine((int)line.get(i).line_x1,  (int)line.get(i).line_y1, (int)line.get(i).line_x2,  (int)line.get(i).line_y2);
			    }
			    g.setColor(cyan);
			    if(game_mode == false)
			    {
			    	for(int j = 0; j < pointer.size(); j++)
				    {	
				    	 g.drawLine((int)pointer.get(j).x1,  (int)pointer.get(j).y1, (int)pointer.get(j).x2, (int)pointer.get(j).y2);
				    }
			    }
			    
			   //g.drawString("b: " + Boolean.toString(c), 220, 20);
			   //g.drawString("ball.get(k).y: " + Double.toString(ball.get(k).y), 220, 40);  
			    g2.setStroke(new BasicStroke(1.0f));  // толщина равна 10
	            for(int k = 0;  k < ball.size(); k++)
	            {
	            	if(ball.get(k).visible == true)
	                g.drawImage(ball.get(k).getImage(), (int)ball.get(k).x,(int)ball.get(k).y, ball.get(k).radius*2, ball.get(k).radius*2, null);
	            }   
		}
    public void change_direction(int x1, int y1, int x2, int y2, int k)
    {
         double n1 = y1 - y2;
         double n2 = x2 - x1; //+
         double dr = (ball.get(k).v_x*n1 + ball.get(k).v_y*n2) / (n1*n1 + n2*n2);
         double two_n1 = 2 * n1;
         double two_n2 = 2 * n2;
         double second_express_1 =  two_n1 * dr;
         double second_express_2 =  two_n2 * dr;
         ball.get(k).v_x = ball.get(k).v_x - second_express_1;
         ball.get(k).v_y = ball.get(k).v_y - second_express_2;
    }
	    public double distance_two_point(double x1, double y1, double x2, double y2)
	    {
	    	return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
	    }
        public void actionPerformed(ActionEvent e)
        {
            calculation();
            repaint();  
        }      
    public class CustomListener extends MouseAdapter implements MouseListener, MouseMotionListener                                                                                             
    {      
        public void mouseMoved(MouseEvent e)
        {
    		mouse_x = e.getX();
     	    mouse_y = e.getY();
        	if(game_mode == false)
        	{
	        	 if (e.getButton() == MouseEvent.BUTTON1) //Left button pressed
	        	 {
	        		 if(left_mouse_pressed == true)
	                 {
	                          l.line_x2 = e.getX();
	                          l.line_y2 = e.getY();
	                 }
	        	 }    
        	}	  	 
        }
        public void mouseDragged(MouseEvent e)
        {
        	if(game_mode == false)
        	{
        		if(left_mouse_pressed == true)
                {
                        l.line_x2 = e.getX();
                        l.line_y2 = e.getY();
                }        
	           	if(right_mouse_pressed == true)  //Right button
	           	{
	           	    	if(point1 == true)
	           	    	{
	           	    		if(line.get(number_point).move_by_right_mouse == true)
	           	    		{
	                          	line.get(number_point).line_x1 = e.getX();
		           	    		line.get(number_point).line_y1 = e.getY();	
	           	    		}
	           	    	}
	           	    	else
	           	    	{
	           	    		if(line.get(number_point).move_by_right_mouse == true)
	           	    		{
		           	    		line.get(number_point).line_x2 = e.getX();	
		           	    		line.get(number_point).line_y2 = e.getY();	
	           	    		}
	           	    	}
	           	}
        	}
        }
	    public void mouseClicked(MouseEvent e) {
	      }
	    public void mouseEntered(MouseEvent e) {
	    }
	    public void mouseExited(MouseEvent e) {
	    }
	    public void mousePressed(MouseEvent e) {
	    	if(game_mode == false)
	    	{
		        if (e.getButton() == MouseEvent.BUTTON1)
		        {
		            l = new Line();
		            l.line_x1 = e.getX();
		            l.line_y1 = e.getY();
		            l.line_x2 = e.getX();
		            l.line_y2 = e.getY();
		            left_mouse_pressed = true;
		        }
		        double d1  = 0;
		        double d2  = 0;
		        boolean first_point = false;
		        double min_d = 0;
			    if(line.size() != 0)
			    { 
			        if(e.getButton() == MouseEvent.BUTTON3)
			        {
			        	right_mouse_pressed = true;
			        	for(int i = 0; i < line.size(); i++)
			        	{
			        		if(line.get(i).move_by_right_mouse == true)
			        		{
			        			d1 = distance_two_point(e.getX(), e.getY(), line.get(i).line_x1, line.get(i).line_y1);
				            	d2 = distance_two_point(e.getX(), e.getY(), line.get(i).line_x2, line.get(i).line_y2);
			        			if(first_point == false)
			        			{
			        				if(d1 < d2)
					            	{
					            		point1 = true;
					            		number_point = i;
					                    min_d = d1;
					                    first_point = true;
					            	}
					            	if(d2 < d1)
					            	{
					            		min_d = d2;
					            		number_point = i;
					            		point1 = false;
					            		first_point = true;
					            	}
			        			}
			        			else
			        			{
			        				 if(d1 < min_d)
                                     {
                                         point1 = true;
                                         number_point = i;
                                         min_d = d1;
                                     }
                                     if(d2 < min_d)
                                     {
                                         min_d = d2;
                                         number_point = i;
                                         point1 = false;
                                     }
			        			}
			        		}
			        	}
			        }
		    	}
	    	}
	    }
	    public void mouseReleased(MouseEvent e)
	    {
	    	if(game_mode == false)
	    	{ 
	    		if(left_mouse_pressed == true)
	    		{
	    			 Add_Line(l.line_x1, l.line_y1, l.line_x2, l.line_y2, true, true, true);
	    		}
		       if (e.getButton() == MouseEvent.BUTTON1)
		       {
		    	  // Add_Line(l.line_x1, l.line_y1, l.line_x2, l.line_y2, true, true, true); 
		    	   left_mouse_pressed = false;       
		       }
		       		if(line.size() != 0) //если есть хотя бы одна линия 
		       		{
		       		    if(e.getButton() == MouseEvent.BUTTON3) //Right mouse
			            {
		       		    	right_mouse_pressed = false;
		            	    if(line.get(number_point).line_y1 == line.get(number_point).line_y2)
		            	    {
		            	    	line.get(number_point).line_y2 += 0.01;
		            	    	//line.get(n_point+1).l
		            	    	
		            	    }
		            	    if(line.get(number_point).line_x1 == line.get(number_point).line_x2)
		            	    {
		            	    	line.get(number_point).line_x2 += 0.01;
		            	    	
		            	    }
			            }
		       		}
	           }
        }
    }
	private class TAdapter extends KeyAdapter  implements KeyListener //
	{
	        public void keyReleased(KeyEvent e)
	        {
	        }
	        public void keyTyped(KeyEvent e) {
	        }
	        public void keyPressed(KeyEvent e) 
	        {
	           int key = e.getKeyCode();
	           boolean add_line_visible = false;
	           if(key == KeyEvent.VK_SPACE)
	           {
	        	   
	        	   if(left_mouse_pressed == false && right_mouse_pressed == false)
	        	   {
		        	     game_mode = !game_mode;
		        	    
		        	     if(game_mode == true) 
		        	     {
		        	    	 int line_size = line.size();
		        	    	 count_add_line = 0;
		        	    	 for(int i = 0; i < line_size; i++)
		        	    	 {
		        	    		 //if(line.get(i).removable == true)
		        	    		/// {
			    	    			 double s3 = distance_two_point(line.get(i).line_x1, line.get(i).line_y1, line.get(i).line_x2, line.get(i).line_y2);
			    	    			 double s2 = distance_two_point(line.get(i).line_x2, line.get(i).line_y1, line.get(i).line_x2, line.get(i).line_y2);
			    	    			 double angle = Math.asin(s2 / s3);
			    	    			 double dx = Math.sin(angle) * 4;
			    	    			 double dy = Math.cos(angle) * 4;
	
			    	    			 if(line.get(i).line_x2 > line.get(i).line_x1 && line.get(i).line_y2 > line.get(i).line_y1 || line.get(i).line_x1 > line.get(i).line_x2 && line.get(i).line_y1 > line.get(i).line_y2)
			    	    			 {
			    	    				 Add_Line(line.get(i).line_x1-dx, line.get(i).line_y1+dy, line.get(i).line_x1+dx, line.get(i).line_y1-dy, add_line_visible, false, false);
			    	    				Add_Line(line.get(i).line_x1+dx, line.get(i).line_y1-dy, 
			    	    						  line.get(i).line_x1+dx+dy, line.get(i).line_y1-dy+dx, add_line_visible, false, false);
			    	    				 Add_Line(line.get(i).line_x1+dx+dy, line.get(i).line_y1-dy+dx,
			    	    					 	  line.get(i).line_x1+dx+dy-2*dx, line.get(i).line_y1-dy+dx+2*dy,
			    	    					 	 add_line_visible, false, false);
			    	    				 Add_Line(line.get(i).line_x1-dx, line.get(i).line_y1+dy,
			    	    					  	  line.get(i).line_x1-dx+dy, line.get(i).line_y1+dy+dx, add_line_visible, false, false);
			    	    				 //end2
			    	    				 Add_Line(line.get(i).line_x2-dx, line.get(i).line_y2+dy, line.get(i).line_x2+dx, line.get(i).line_y2-dy, add_line_visible, false, false);
			    	    				 Add_Line(line.get(i).line_x2-dx, line.get(i).line_y2+dy, line.get(i).line_x2+dx, line.get(i).line_y2-dy, add_line_visible, false, false);
			    	    				 Add_Line(line.get(i).line_x2-dx, line.get(i).line_y2+dy, 
			    	    						  line.get(i).line_x2-dx+dy, line.get(i).line_y2+dy+dx,  add_line_visible, false, false);
			    	    				 Add_Line(line.get(i).line_x2-dx+dy, line.get(i).line_y2+dy+dx,
			    	    						 line.get(i).line_x2-dx+dy+2*dx, line.get(i).line_y2+dy+dx-2*dy,add_line_visible, false, false);
			    	    				 Add_Line(line.get(i).line_x2+dx, line.get(i).line_y2-dy,
			    	    					  	  line.get(i).line_x2+dx+dy, line.get(i).line_y2-dy+dx,
			    	    					  	add_line_visible, false, false);
			    	    				 
			    	    				
			    	    				 count_add_line += 9;
			    	    			 }
			    	    			 else
			    	    			 {
			    	    			 Add_Line(line.get(i).line_x1-dx, line.get(i).line_y1-dy, line.get(i).line_x1+dx, line.get(i).line_y1+dy, add_line_visible, false, false);
			    	    			 Add_Line(line.get(i).line_x1-dx, line.get(i).line_y1-dy,
			    	    					  line.get(i).line_x1-dx-dy, line.get(i).line_y1-dy+dx, add_line_visible, false, false);
			    	    			 Add_Line(line.get(i).line_x1-dx-dy, line.get(i).line_y1-dy+dx,
			    	    					  line.get(i).line_x1-dx-dy+2*dx, line.get(i).line_y1-dy+dx+2*dy, add_line_visible, false, false);
			    	    			 Add_Line(line.get(i).line_x1+dx, line.get(i).line_y1+dy,
			    	    					  line.get(i).line_x1+dx-dy, line.get(i).line_y1+dy+dx, add_line_visible, false, false);
				    	    		//end2	
			    	    			 Add_Line(line.get(i).line_x2-dx, line.get(i).line_y2-dy, line.get(i).line_x2+dx, line.get(i).line_y2+dy, add_line_visible , false, false);
			    	    			 Add_Line(line.get(i).line_x2-dx, line.get(i).line_y2-dy, 
			    	    					  line.get(i).line_x2-dx+dy, line.get(i).line_y2-dy-dx, add_line_visible , false, false);
			    	    			 Add_Line(line.get(i).line_x2-dx+dy, line.get(i).line_y2-dy-dx,
			    	    					  line.get(i).line_x2-dx+dy+2*dx, line.get(i).line_y2-dy-dx+2*dy, add_line_visible , false, false);
			    	    			 Add_Line(line.get(i).line_x2+dx, line.get(i).line_y2+dy,
			    	    					  line.get(i).line_x2+dx+dy, line.get(i).line_y2+dy-dx, add_line_visible , false, false);
				    	    			
			    	    			 count_add_line += 8;
			    	    			 }	
		        	    		// }
		        	    	 }
		        	    	 for(int i = 0 ; i < ball.size(); i++)
		        	    	 {
		        	    		 ball.get(i).allow_move = true;
		        	    		
		        	    	 }	
		        	     }
		        	     else
		        	     {      
		        	    	// wall.get(0).i_4();
		        	    	 for(int i = 0; i < ball.size(); i++)
		        	    	 {
			        	    	  ball.get(i).allow_move = false;
			        	    	  ball.get(i).x = ball.get(i).start_x;
			        	    	  ball.get(i).y = ball.get(i).start_y;
			        	    	  ball.get(i).visible = true;
			        	    	  ball.get(i).v_x = ball.get(i).start_v_x;
			        	    	  ball.get(i).v_y = ball.get(i).start_v_y;
		        	    	 }
		        	    	 for(int i = 0; i < count_add_line; i++)
		        	    	 {
		        	    		
		        	    		 line.remove(line.size()-1);
		        	    	 }
		        	     }   
	        	   }
	           }
	           if(key == KeyEvent.VK_DELETE)
	           {
	        	   if(game_mode == false && right_mouse_pressed == false)
	        	   {
	        		   if(line.size() != 0)
		        	   {
	        			   double min_p = 0;
	        			   boolean first = false;
	        			   int number_line = 0;
	        			   for(int i = 0; i < line.size(); i++)
			        	   {
	        				  if(line.get(i).removable == true)
	        				   {
	        					   if(first == false)
	        					   {
		        					   min_p = collision.normal(mouse_x, mouse_y, (int)line.get(i).line_x1, (int)line.get(i).line_y1, (int)line.get(i).line_x2, (int)line.get(i).line_y2) ;   
		        					   number_line = i;
		        					   first = true;
	        					   }
		        				   else
		        				   {
		        					   if(collision.normal(mouse_x, mouse_y, (int)line.get(i).line_x1, (int)line.get(i).line_y1, (int)line.get(i).line_x2, (int)line.get(i).line_y2 ) < min_p)
					        		   {
		        						   min_p = collision.normal(mouse_x, mouse_y, (int)line.get(i).line_x1, (int)line.get(i).line_y1, (int)line.get(i).line_x2, (int)line.get(i).line_y2) ;
					        			   number_line = i;
					        		   }
		        				   }
	        				   }  
			        	   }
	        			   if(line.get(number_line).removable == true)
			        	   {
			        		   line.remove(number_line);
			        	   }
		        	   }
	        	   }   
	           }
	      }
	}
}