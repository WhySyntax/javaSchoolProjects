import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class StartingClass implements ActionListener {
	
	JFrame frame;
	JButton button;
	JTextField inputField;
	JLabel outputField;
	
	public StartingClass() {
		frame = new JFrame();
		frame.setSize(400, 300);
		frame.setLayout(new GridLayout(3, 1));
		inputField = new JTextField("This is where your input goes.");
		frame.add(inputField);
		button = new JButton("This is a button. You press it.");
		button.addActionListener(this);
		frame.add(button);
		outputField = new JLabel("This is where your output goes.");
		frame.add(outputField);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public int vowelCount(String thisIsAString) {
		int amntVowels = 0;
		for (int i=0;i<thisIsAString.length();i++) {
			if ((thisIsAString.charAt(i)=='a')||(thisIsAString.charAt(i)=='e')||(thisIsAString.charAt(i)=='i')||(thisIsAString.charAt(i)=='o')||(thisIsAString.charAt(i)=='u')) {
				amntVowels+=1;
			}
		}
		return amntVowels;
	}
	public int consonantCount(String thisIsAString) {
		int amntConsonants = 0;
		for (int i=0;i<thisIsAString.length();i++) {
			if (!((thisIsAString.charAt(i)=='a')||(thisIsAString.charAt(i)=='e')||(thisIsAString.charAt(i)=='i')||(thisIsAString.charAt(i)=='o')||(thisIsAString.charAt(i)=='u')||(thisIsAString.charAt(i)==' '))) {
				amntConsonants+=1;
			}
		}
		return amntConsonants;
	}
	public static void main(String[] args) {
		new StartingClass();
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		String finalString = inputField.getText();
		int vwlCount = vowelCount(finalString.toLowerCase());
		int cnsnntCount = consonantCount(finalString.toLowerCase());
		int answer = vwlCount*cnsnntCount;
		outputField.setText(vwlCount+"*"+cnsnntCount+"="+answer);
	}

}
