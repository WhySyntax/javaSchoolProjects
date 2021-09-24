import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GraphCreator {
	
	JFrame frame = new JFrame();
	GraphPanel panel = new GraphPanel();
	
	public GraphCreator() {
		frame.setSize(600, 600);
		frame.setLayout(new BorderLayout());
		frame.add(panel,BorderLayout.CENTER);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GraphCreator();
	}
}
