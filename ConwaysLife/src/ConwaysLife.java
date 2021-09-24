import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("unused")

public class ConwaysLife implements ActionListener, Runnable, ChangeListener {
	//public variables that the entire game needs access to
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[25][25];
	boolean[][] neighborhood = new boolean [25][25];
	JButton[] control = new JButton[4];
	Container center = new Container();
	Container bottom = new Container();
	Container north = new Container();
	int living = 0;
	JLabel livingLabel = new JLabel(living+" people are alive         Change Speed:");
	JSlider speedControl = new JSlider(0,400,0);
	JButton start = new JButton("Start");
	JButton step = new JButton("Step");
	JButton stop = new JButton("Stop");
	final boolean ALIVE = true;
	final boolean DEAD = false;
	boolean stopped = true;
	int GameSpeed=500;
	
	public ConwaysLife() {
		//setting up frame
		frame.setSize(1200,800);
		frame.setLayout(new BorderLayout());
		
		//center layout
		center.setLayout(new GridLayout(25,25));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[j][i] = new JButton("");
				button[j][i].setBackground(Color.WHITE);
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
				neighborhood[j][i]=DEAD;
			}
		}
		frame.add(center,BorderLayout.CENTER);
		
		//bottom layout
		bottom.setLayout(new FlowLayout());
		control[0] = new JButton("Start");
		bottom.add(control[0]);
		control[0].addActionListener(this);
		control[1] = new JButton("Step");
		bottom.add(control[1]);
		control[1].addActionListener(this);
		control[2] = new JButton("Stop");
		bottom.add(control[2]);
		control[2].addActionListener(this);
		control[3] = new JButton("Clear");
		bottom.add(control[3]);
		control[3].addActionListener(this);
		frame.add(bottom,BorderLayout.PAGE_END);
		
		//top frame
		north.setLayout(new FlowLayout());
		north.add(livingLabel);
		speedControl.addChangeListener(this);
		north.add(speedControl);
		frame.add(north,BorderLayout.NORTH);
		
		//opening frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public boolean calculateLife(int rowCheck, int columnCheck) {
		//checks amount of neighbors and returns alive or dead
		int liveNeighbors = 0;
		
		//checking corners
		if ((rowCheck>0&&columnCheck>0)&&(neighborhood[rowCheck-1][columnCheck-1])) {
			liveNeighbors++;
		}
		if ((rowCheck>0&&columnCheck<24)&&(neighborhood[rowCheck-1][columnCheck+1])) {
			liveNeighbors++;
		}
		if ((rowCheck<24&&columnCheck>0)&&(neighborhood[rowCheck+1][columnCheck-1])) {
			liveNeighbors++;
		}
		if ((rowCheck<24&&columnCheck<24)&&(neighborhood[rowCheck+1][columnCheck+1])) {
			liveNeighbors++;
		}
		
		//checking sides
		if ((rowCheck>0)&&(neighborhood[rowCheck-1][columnCheck])) {
			liveNeighbors++;
		}
		if ((columnCheck>0)&&(neighborhood[rowCheck][columnCheck-1])) {
			liveNeighbors++;
		}
		if ((rowCheck<24)&&(neighborhood[rowCheck+1][columnCheck])) {
			liveNeighbors++;
		}
		if ((columnCheck<24)&&(neighborhood[rowCheck][columnCheck+1])) {
			liveNeighbors++;
		}
		
		//returning alive or dead
		if (liveNeighbors == 3) {
			return true;
		}
		if (liveNeighbors==2&&neighborhood[rowCheck][columnCheck]) {
			return true;
		}
		return false;
	}
	public void TakeStep() {
		//going through each cell and applying previous method and changing the living label
		living = 0;
		boolean[][] mockNeighborhood = new boolean[neighborhood.length][neighborhood[0].length];
		boolean livingCell = true;
		for (int i=0;i<neighborhood.length;i++) {
			for (int j=0;j<neighborhood[i].length;j++) {
				livingCell = calculateLife(j,i);
				if (livingCell) {
					mockNeighborhood[j][i]=ALIVE;
					button[j][i].setBackground(Color.BLUE);
					living++;
				} else {
					mockNeighborhood[j][i]=DEAD;
					button[j][i].setBackground(Color.WHITE);
				}
			}
		}
		livingLabel.setText(living+" people are alive         Change Speed:");
		neighborhood=mockNeighborhood;
	}
	
	public void clearBoard() {
		//clears the board in case you want to try something else w/o restarting the entire program
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[j][i].setBackground(Color.WHITE);
				neighborhood[j][i]=DEAD;
				living=0;
				livingLabel.setText(living+" people are alive         Change Speed:");
				
			}
		}
	}
	
	public static void main(String[] args) {
		//runnning program and making board
		new ConwaysLife();
	}

	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		//checking if grid button was pressed
		for (int i = 0; i<button.length;i++) {
			for (int j = 0; j<button[i].length;j++) {
				if (event.getSource().equals(button[j][i])) {
					gridButton = true;
					current = button[j][i];
					if (neighborhood[j][i]==DEAD) {
						neighborhood[j][i]=ALIVE;
						living++;
						livingLabel.setText(living+" people are alive         Change Speed:");
						button[j][i].setBackground(Color.BLUE);
					} else if (neighborhood[j][i] == ALIVE) {
						neighborhood[j][i]=DEAD;
						button[j][i].setBackground(Color.WHITE);
						living--;
						livingLabel.setText(living+" people are alive         Change Speed:");
					}
				}
			}
		}
		//checking if control button was pressed
		for (int i = 0;i<control.length;i++) {
			if (event.getSource().equals(control[i])) {
				if (i==0) {
					if (stopped) {
						stopped = false;
						Thread t = new Thread(this);
						t.start();
					}
				} else if (i==1) {
					TakeStep();
				} else if (i==2) {
					stopped = true;
				} else if (i==3) {
					clearBoard();
				}
			}
		}
	}
	
	
	public void run() {
		//keeps step running
		while (!stopped) {
			TakeStep();
			try {
				Thread.sleep(GameSpeed);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		GameSpeed = 500-source.getValue();
		
	}
}
