package qq;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class QrCodePic extends JFrame{
    
    MyPanel mp=null;
    public QrCodePic(){
        mp=new MyPanel();
        this.add(mp);
        this.setSize(210, 230);
        this.setLocation(500, 400);
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
 
class MyPanel extends JPanel{
     
    Image image=null;
     
    public void paint(Graphics g){
        try {
            image=ImageIO.read(new File("D:\\jj\\test.jpg"));
            g.drawImage(image, 0, 0, 200, 200, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}