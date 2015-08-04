import javax.swing.JFrame;
 
public class GameWindow {
        JFrame frame;
        public GameWindow(){
            frame = new JFrame("Game window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setSize(1024,600);
           frame.setResizable(false);
            frame.add(new GameScreen());
          //  frame.addKeyListener(new GameScreen());
       //   GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
       //   gd.setFullScreenWindow(frame);
            frame.setVisible(true);
         //   frame.setExtendedState(frame.MAXIMIZED_BOTH);
           }
 
        public static void main(String [] args){
             new GameWindow();
           }
}
