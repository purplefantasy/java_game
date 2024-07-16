import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class Jframe extends JFrame {
	
	Paint pane = null;

	static Jframe frame;
	
	public Jframe(String title) throws IOException {
		pane = new Paint(frame);
		setTitle(title);
		setSize(1000,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.gray);
		getContentPane().setLayout(new BorderLayout());
		pane.setBackground(java.awt.Color.white);
		getContentPane().add(pane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		frame = new Jframe("Dice game");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);
		frame.setSize(1024, 768);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}