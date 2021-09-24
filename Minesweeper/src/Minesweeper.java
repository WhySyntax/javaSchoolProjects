import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Minesweeper implements ActionListener, MouseListener {
	final int[] size = {20,50};
	JFrame frame = new JFrame();
	Container center = new Container();
	JButton[][] tiles = new JButton[size[0]][size[0]];
	Container endGame = new Container();
	JButton playAgain = new JButton();
	JLabel gameEnd = new JLabel();
	boolean[][] bombs = new boolean[size[0]][size[0]];
	boolean[][] victory = new boolean[size[0]][size[0]];
	boolean ended = false;
	final Color Green = Color.green.darker().darker();
	final Color Lime = Color.green.darker();
	public static void main(String[] args) {
		new Minesweeper();
	}
	public Minesweeper() {
		frame.setSize(900,900);
		frame.setLayout(new BorderLayout());
		
		//setting up buttons and bomb array
		center.setLayout(new GridLayout(20,20));
		for (int i=0;i<tiles.length;i++) {
			for (int j=0;j<tiles[i].length;j++) {
				tiles[i][j]=new JButton();
				tiles[i][j].addActionListener(this);
				tiles[i][j].addMouseListener(this);
				bombs[i][j]=false;
				victory[i][j]=false;
				center.add(tiles[i][j]);
				tiles[i][j].setBackground(Green);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		
		//initialize 25 bombs
		Random rand = new Random();
		for (int i=0;i<size[1];i++) {
			int j = rand.nextInt(20);
			int k = rand.nextInt(20);
			bombs[j][k]=true;
			//tiles[j][k].setBackground(Color.red); //debugging easy mode
			victory[j][k]=true;
		}
		
		//sets up the container for the end of the game so that it doesnt have to be done in the end method itself
		endGame.setLayout(new FlowLayout());
		endGame.add(gameEnd);
		playAgain.addActionListener(this);
		endGame.add(playAgain);
		
		//opens frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void end(boolean victory,int i,int j) {
		//puts a label on the top depending on if you won or not
		if (victory) {gameEnd.setText("Congratulations on winning, would you like to play again?");}
		else {gameEnd.setText("You lost this time, but would you like to try again?");}
		playAgain.setText("Yes");
		frame.add(endGame,BorderLayout.PAGE_START);
		ended=true;
		tiles[i][j].setText("boom");
	}
	public void checkMines(int rowCheck, int columnCheck) {
		int surroundingMines = 0;
		
		//checked the amound of bombs near the tile you clicked
		for (int i=rowCheck-1;i<rowCheck+2;i++) {
			if (i<0) {continue;}
			if (i>=size[0]) {break;}
			for (int j=columnCheck-1;j<columnCheck+2;j++) {
				if (j<0) {continue;}
				if (j>=size[0]) {continue;}
				if (bombs[i][j]) {surroundingMines++;}
			}
		}
		
		//reveals the tile you clicked
		tiles[rowCheck][columnCheck].setText(Integer.toString(surroundingMines));
		tiles[rowCheck][columnCheck].setBackground(Lime);
		victory[rowCheck][columnCheck] = true;
		
		//presses all surrounding buttons if the tile you clicked was a 0
		if (surroundingMines==0) {
			for (int i=rowCheck-1;i<rowCheck+2;i++) {
				if (i<0) {continue;}
				if (i>=size[0]) {break;}
				for (int j=columnCheck-1;j<columnCheck+2;j++) {
					if (j<0) {continue;}
					if (j>=size[0]) {continue;}
					tiles[i][j].doClick();
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//restarts the game if you decided to play again
		if (e.getSource()==playAgain) {
			frame.setVisible(false);
			new Minesweeper();
		}
		//checks which tile you clicked
		for (int i=0;i<tiles.length;i++) {
			if (ended) {break;}
			for (int j=0;j<tiles[i].length;j++) {
				if (e.getSource()==tiles[i][j]) {
					if (bombs[i][j]) {
						tiles[i][j].setBackground(Color.red);
						end(false,i,j);
					} else if (!bombs[i][j]&&!victory[i][j]) {checkMines(i,j);}
				}
			}
		}
		boolean won = true;
		//checks if you have uncovered all the tiles yet
		for (int i=0;i<victory.length;i++) {for (int j=0;j<victory[i].length;j++) {
			if (!victory[i][j]) {won=false;}
		}
		}
		if (won) {end(won,0,0);}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//sets tile orange if you right click it
		// TODO Auto-generated method stub
		for (int i=0;i<tiles.length;i++) {
			for (int j=0;j<tiles[i].length;j++) {
				if (e.getSource()==tiles[i][j]) {
					if (SwingUtilities.isRightMouseButton(e)) {
						if (tiles[i][j].getBackground()==Color.orange) {tiles[i][j].setBackground(Green);}
						else if (tiles[i][j].getBackground()==Green) {tiles[i][j].setBackground(Color.orange);}
					}
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
