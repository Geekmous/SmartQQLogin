package show;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowImage extends JFrame{
    int  height = 200, width = 200;
    public ShowImage (String Path) {
        super();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(d.width / 2 - width /2 , d.height / 2 - height / 2, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon ic = new ImageIcon(Path);
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setIcon(ic);
        panel.add(label);
        setContentPane(panel);
        setVisible(true);
    }
    public static void main(String ...strings) {
        ShowImage  s = new ShowImage("./QR.jpg");
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        s.setVisible(false);
        
    }
}
