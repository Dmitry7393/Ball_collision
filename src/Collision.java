
public class Collision {
 public Collision()
 {
	 
 }
 public boolean collision_ball_line(int radius, int center_x, int center_y, int x1, int y11, int x2, int y2, int k) 
 {
 	         if( (x1 == x2) && (y11 == y2))  //случай, если у нас просто точка 
 			 {
 			     //MessageBox(NULL, "=","—ообщение",0);
 				 double temp = Math.sqrt( ((x1-center_x)*(x1-center_x) + (y11-center_y)*(y11-center_y))+0.0f);
 				  if(temp <= k)
 				   {
 					 return true;
 				   }
 				  if(temp > k)
 				   {
 					 return false;
 				   }
 			 }
 			double f,g,s,b,m;
 			double b1, b2; //в случае если m < 0
 			double min = 0;
 			f = 0;
 			m = 0;
 			g = 0;
 			s = 0;
 			b = 0;
 			f = Math.sqrt(((x1-center_x)*(x1-center_x) + (y11-center_y)*(y11-center_y)) + 0.0f);
 			g = Math.sqrt(((x2-center_x)*(x2-center_x) + (y2-center_y)*(y2-center_y)) + 0.0f);
 			s = Math.sqrt(((x2-x1)*(x2-x1) + (y2-y11)*(y2-y11)) + 0.0f);
 			m = ((f*f-g*g+s*s)+0.0f)/(2.0*s);
 			if(m >= 0) //нормальна€ ситуаци€
 			{
 			   b = Math.sqrt(f*f - m*m); //длина перпендикул€ра 
 			}
 			if(m < 0)
 			{
 				b1 = Math.sqrt( ( (x1-center_x)*(x1-center_x) + (y11-center_y)*(y11-center_y) )+0.0f );
 				b2 = Math.sqrt( ( (x2-center_x)*(x2-center_x) + (y2-center_y)*(y2-center_y) )+0.0f );
 				if(b1 <= b2) min = b1;
 				if(b2 <= b1) min = b2;
 				b = min;
 			}
 			 if(b <= k)
 			 {
 				return true;
 			 }
 			  if(b > k)
 			  {
 				 return false;
 			  }
 			  return false;
 }
 public double normal(int center_x, int center_y, int x1, int y11, int x2, int y2)
	{
	    double f,g,s,b,m;
	    double b1, b2; //в случае если m < 0
	    double min  = 0;
	    f = 0;
	    m = 0;
	    g = 0;
	    s = 0;
	    b = 0;
	    double t1 = x1-center_x;
	    double t2 = y11-center_y;
	    double t3 = x2-center_x;
	    double t4 = y2-center_y;
	    double t5 = x2-x1;
	    double t6 = y2-y11;
	    f = Math.sqrt(((t1)*(t1) + (t2)*(t2)));
	    g = Math.sqrt(((t3)*(t3) + (t4)*(t4)));
	    s = Math.sqrt(((t5)*(t5) + (t6)*(t6)));
	    m = ((f*f-g*g+s*s)+0.0f)/(2.0*s);
	    if(m >= 0) //нормальна€ ситуаци€
	    {
	       b = Math.sqrt(f*f - m*m); //длина перпендикул€ра
        }
            if(m < 0)
            {
                    double t7 = x1-center_x;
                    double t8 = y11-center_y;
                    double t9 = x2-center_x;
                    double t10 = y2-center_y;
                    b1 = Math.sqrt( ( (t7)*(t7) + (t8)*(t8) )+0.0f );
                    b2 = Math.sqrt( ( (t9)*(t9) + (t10)*(t10) )+0.0f );
                    if(b1 <= b2) min = b1;
                    if(b2 <= b1) min = b2;
                    b = min;
            }
            return b;
	}
}
