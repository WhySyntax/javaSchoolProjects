import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Farkle implements ActionListener {
	//initilizes the objects
	JFrame frame = new JFrame();
	JCheckBox[] die = new JCheckBox[6];
	int[] dieValues = new int[6];
	JButton[] controls = new JButton[3];
	int currentRound = 0;
	int currentScore = 0;
	JLabel round = new JLabel("Round:"+currentRound);
	JLabel score = new JLabel("Score:"+currentScore);
	Container center = new Container();
	Container bottom = new Container();
	Container top = new Container();
	ImageIcon[] diceSides = new ImageIcon[6];
	
	public Farkle() {
		frame.setLayout(new BorderLayout());
		frame.setSize(600,600);
		center.setLayout(new GridLayout(2,3));
		bottom.setLayout(new FlowLayout());
		top.setLayout(new FlowLayout());
		
		//sets up the image list
		for (int i=1;i<=6;i++) {
			diceSides[i-1]=new ImageIcon("Dice"+i+".png");
			dieValues[i-1]=0;
		}
		
		//sets up the dice
		for (int i=0;i<die.length;i++) {
			die[i]=new JCheckBox();
			die[i].setIcon(diceSides[0]);
			center.add(die[i]);
		}
		frame.add(center,BorderLayout.CENTER);
		
		//sets up the control buttons
		controls[0]=new JButton();
		controls[0].setText("ROLL");
		controls[0].addActionListener(this);
		bottom.add(controls[0]);
		controls[1]=new JButton();
		controls[1].setText("SCORE");
		controls[1].addActionListener(this);
		bottom.add(controls[1]);
		controls[1].setEnabled(false);
		controls[2]=new JButton();
		controls[2].setText("RESET");
		controls[2].addActionListener(this);
		bottom.add(controls[2]);
		frame.add(bottom,BorderLayout.SOUTH);
		controls[2].setEnabled(false);
		
		//sets up the top with the labels
		top.add(round);
		top.add(score);
		frame.add(top,BorderLayout.NORTH);
		
		//activates frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Farkle();
	}

	public void Roll() {
		//rolls the dice
		for (int i=0;i<die.length;i++) {
			dieValues[i]=(int) (Math.random()*5);
			die[i].setIcon(diceSides[dieValues[i]]);
		}
		controls[0].setEnabled(false);
		controls[1].setEnabled(true);
		controls[2].setEnabled(true);
	}
	
	public void Score() {
		//checks how many times each number appears in the buttons you have selected
		int[] roundScore = {0,0,0,0,0,0};
		boolean straight = true;
		for (int i=0;i<die.length;i++) {
			if (!die[i].isSelected()) {straight=false;}
			if (die[i].isSelected()) {
				roundScore[dieValues[i]]++;
				die[i].setSelected(false);
			}
		}
		//checks how many 2,3,4,5, and 6 of a kinds you have
		int[] amountFrequencies = {0,0,0,0,0};
		for (int i=0;i<roundScore.length;i++) {
			if (roundScore[i]>1) {
				amountFrequencies[roundScore[i]-2]++;
				straight=false;
			}
		}
		//this is the scoring logic, I used the first result for farkle scoring I found on google, I would've used yours
		//but it didn't have available scores for many scenarios
		//and it would've been troublesome to try and cobble it together with what I found
		if (amountFrequencies[4]==1) {currentScore+=3000;}
		else if (amountFrequencies[1]==2) {currentScore+=2500;}
		else if (amountFrequencies[3]==1) {currentScore+=2000;}
		else if ((amountFrequencies[0]==1&&amountFrequencies[2]==1)||(straight)||(amountFrequencies[0]==3)) {currentScore+=1500;}
		else if (amountFrequencies[2]==1) {currentScore+=1000;}
		else if (amountFrequencies[1]==1) {
			for (int i=0;i<roundScore.length;i++) {
				if (roundScore[i]==3) {
					if (i==0) {currentScore+=500;}
					else {currentScore+=(i+1)*100;}
				}
			}
		}
		else {currentScore+=roundScore[0]*100+roundScore[4]*50;}
		
		//makes the roll button avalible again
		controls[0].setEnabled(true);
		controls[1].setEnabled(false);
		controls[2].setEnabled(false);
		//increases round and updates label
		currentRound++;
		round.setText("Round:"+currentRound);
		score.setText("Score:"+currentScore);
	}
	
	public void actionPerformed(ActionEvent e) {
		//does all the commands, fortunately it was simple for this program
		if (e.getSource()==controls[0]) {Roll();}
		if (e.getSource()==controls[1]) {Score();}
		if (e.getSource()==controls[2]) {
			frame.setVisible(false);
			new Farkle();
		}
		
	}

}
