import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	public GraphPanel() {
		super();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(0, 0, 200, 400);
	}
}
