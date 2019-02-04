import java.awt.Graphics;
import javax.swing.*;
public class FrmSplash extends JFrame {
	JProgressBar jpb;
	JDesktopPane jdp;
	FrmSplash(){
		jdp=new MyJDesktopPane();
		add(jdp);
		jpb=new JProgressBar(JProgressBar.HORIZONTAL,0,99);
		add(jpb,"South");
		setUndecorated(true);
		setSize(400,300);
		setLocationRelativeTo(null);
		setVisible(true);
		for(int i=0;i<99;i++) {
			jpb.setValue(i);
			try {
				Thread.sleep(40);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		dispose();
		new Notepad();
	}
	public static void main(String[] args) {
		new FrmSplash();
	}

}
class MyJDesktopPane extends JDesktopPane{
	public void paintComponent(Graphics g) {
		ImageIcon ii;
		ii=new ImageIcon("images/notepad.jpg");
		g.drawImage(ii.getImage(),0,0,getSize().width,getSize().height,null);
	}
}
