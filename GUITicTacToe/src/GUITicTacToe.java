import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("unused")

public class GUITicTacToe implements ActionListener{
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[3][3];
	int[][] board = new int [3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	int X_WINS = 0;
	int O_WINS = 0;
	String X_PLAYER_NAME = "x";
	String O_PLAYER_NAME = "o";
	Container center = new Container();
	JLabel xLabel = new JLabel(X_PLAYER_NAME+" wins:"+X_WINS);
	JLabel oLabel = new JLabel(O_PLAYER_NAME+" wins:"+O_WINS);
	JButton xChangeName = new JButton("Change X's name");
	JButton oChangeName = new JButton("Change O's name");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container();
	
	public boolean[] check_win_tie() {
		for (int i=0;i<3;i++) {
			if ((board[i][0]==board[i][1])&&(board[i][1]==board[i][2])) {
				if (board[i][0]==X_MOVE) {
					boolean[] returned = {true,false,true};
					return returned;
				} else if (board[i][0]==O_MOVE) {
					boolean[] returned = {true,false,false};
					return returned;
				}
			} else if ((board[0][i]==board[1][i])&&(board[1][i]==board[2][i])) {
				if (board[1][i]==X_MOVE) {
					boolean[] returned = {true,false,true};
					return returned;
				} else if (board[0][i]==O_MOVE) {
					boolean[] returned = {true,false,false};
					return returned;
				}
			}
		}
		if ((board[0][0]==board[1][1])&&(board[1][1]==board[2][2])||(board[0][2]==board[1][1])&&(board[1][1]==board[2][0])) {
			if (board[1][1]==X_MOVE) {
				boolean[] returned = {true,false,true};
				return returned;
			} else if (board[1][1]==O_MOVE) {
				boolean[] returned = {true,false,false};
				return returned;
			}
		}
		boolean tie = true;
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				if (board[i][j] == 0) {
					tie = false;
					break;
				}
			}
		}
		if (tie) {
			boolean[] returned = {true, true};
			return returned;
		}
		boolean[] returned = {false};
		return returned;
	}
	
	public GUITicTacToe() {
		frame.setSize(600,600);
		frame.setLayout(new BorderLayout());
		
		//center grid container
		center.setLayout(new GridLayout(3,3));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[j][i] = new JButton("");
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
			}
		}
		frame.add(center,BorderLayout.CENTER);
		
		//north grid container
		north.setLayout(new GridLayout(3,2));
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		frame.add(north,BorderLayout.NORTH);
		
		//set program to close if window closes and open window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new GUITicTacToe();
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		//setting button text
		for (int i = 0; i<button.length;i++) {
			for (int j = 0; j<button[i].length;j++) {
				if (event.getSource().equals(button[j][i])) {
					gridButton = true;
					current = button[j][i];
					if (board[j][i] == BLANK) {
						if (turn == X_TURN) {
							current.setText("X");
							board[j][i] = X_MOVE;
							turn = O_TURN;
						} else if (turn == O_TURN) {
							current.setText("O");
							board[j][i] = O_MOVE;
							turn = X_TURN;
						}
						//check for win and ties
						boolean[] status = check_win_tie();
						if (status[0]) {
							//either someone has won or there is a tie
							clearBoard();
							if (!status[1]) {
								//someone won
								if (status[2]) {
									//X wins
									System.out.println(X_PLAYER_NAME+" won");
									X_WINS++;
									xLabel.setText(X_PLAYER_NAME+" wins:"+X_WINS);
								} else {
									//O wins
									System.out.println(O_PLAYER_NAME+" won");
									O_WINS++;
									oLabel.setText(O_PLAYER_NAME+" wins:"+O_WINS);
								}
							}else {
								//there is a tie
								System.out.println("there is a tie");
							}
						}
					}
				}
			}
			if (!gridButton) {
				if (event.getSource().equals(xChangeName)) {
					X_PLAYER_NAME = xChangeField.getText();
					xLabel.setText(X_PLAYER_NAME+" wins:"+X_WINS);
				} else if (event.getSource().equals(oChangeName)) {
					O_PLAYER_NAME = oChangeField.getText();
					oLabel.setText(O_PLAYER_NAME+" wins:"+O_WINS);
				}
			}
		}
	}
	public void clearBoard() {
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board[a].length; b++) {
				board[a][b] = BLANK;
				button[a][b].setText("");
			}
		}
		turn = X_TURN;
	}
}
