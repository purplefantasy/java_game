import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class Jframe extends JFrame {
	
	static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice gd = ge.getDefaultScreenDevice();
	static int width = gd.getDisplayMode().getWidth();
	static int height = gd.getDisplayMode().getHeight();
	Paint pane = null;

	static Jframe frame;
	
	public Jframe(String title) throws IOException {
		pane = new Paint(frame);
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.gray);
		getContentPane().setLayout(new BorderLayout());
		pane.setBackground(java.awt.Color.white);
		getContentPane().add(pane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		frame = new Jframe("Dice game");
		gd.setFullScreenWindow(frame);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}