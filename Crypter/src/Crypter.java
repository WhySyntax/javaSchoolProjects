import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Crypter implements ActionListener{
	//initilize swing components
	JFrame frame = new JFrame();
	JRadioButton[] methods = new JRadioButton[3];
	ButtonGroup cryptionMethods = new ButtonGroup();
	JButton encrypt = new JButton();
	JButton decrypt = new JButton();
	Container top = new Container();
	Container commands = new Container();
	JTextField uncrypted = new JTextField();
	JTextField crypted = new JTextField();
	JTextField key = new JTextField();
	//alphabet array that is used by caesar and vigenere
	char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static void main(String[] args) {
		//runs program
		new Crypter();
	}
	public Crypter() {
		frame.setSize(600,300);
		frame.setLayout(new BorderLayout());
		
		//set up top layout with encryption options
		top.setLayout(new FlowLayout());
		for (int i=0;i<methods.length;i++) {
			methods[i]=new JRadioButton();
			cryptionMethods.add(methods[i]);
			top.add(methods[i]);
			methods[i].addActionListener(this);
		}
		methods[0].setText("Caesar");
		methods[1].setText("Scytale");
		methods[2].setText("Vigenere");
		frame.add(top,BorderLayout.PAGE_START);
		
		//sets up middle with buttons to do the functions and the text boxes to write and see in
		commands.setLayout(new GridLayout(1,2));
		uncrypted.setText("input");
		commands.add(uncrypted);
		commands.add(encrypt);
		encrypt.setText("Encrypt Message");
		encrypt.addActionListener(this);
		commands.add(decrypt);
		decrypt.setText("Decrypt Message");
		decrypt.addActionListener(this);
		crypted.setText("Output");
		commands.add(crypted);
		frame.add(commands,BorderLayout.CENTER);
		
		//just the bottom area where you put the keys
		key.setText("Put Key Here");
		frame.add(key,BorderLayout.PAGE_END);
		
		//sets frame to end the program when closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void CaesarCryption(int key) {
		//does the caesar cipher encryption
		String outputString = new String();
		String[] charsOnly = uncrypted.getText().split("\\W+");
		String input = new String();
		for (int i=0;i<charsOnly.length;i++) {input+=charsOnly[i];}
		input=input.toLowerCase();
		for (int i=0;i<input.length();i++) {
			//finds where in the alphabet the letters are and shifts them accordingly
			int nextIndex = Arrays.binarySearch(alphabet, input.charAt(i))+key;
			if (nextIndex<0) {nextIndex+=26;}
			if (nextIndex>25) {nextIndex-=26;}
			outputString+=alphabet[nextIndex];
		}
		crypted.setText(outputString.toUpperCase());
	}
	public void ScytaleEncryption(int columnCount) {
		//scytale encryption
		ArrayList<ArrayList<Character>> rawOutput = new ArrayList<ArrayList<Character>>();
		for (int i=0;i<columnCount;i++) {rawOutput.add(new ArrayList<Character>());}
		String[] charsOnly = uncrypted.getText().split("\\W+");
		String input = new String();
		for (int i=0;i<charsOnly.length;i++) {input+=charsOnly[i];}
		input=input.toLowerCase();
		String output = new String();
		for (int i=0;i<input.length();i++) {
			//puts the input int eh proper columns of the raw output
			rawOutput.get(i%columnCount).add(input.charAt(i));
		}
		for (int i=0;i<rawOutput.size();i++) {
			for (int j=0;j<rawOutput.get(i).size();j++) {
				output+=""+rawOutput.get(i).get(j);
			}
		}
		crypted.setText(output.toUpperCase());
	}
	public void ScytaleDecryption(int columnCount) {
		ArrayList<ArrayList<Character>> rawOutput = new ArrayList<ArrayList<Character>>();
		for (int i=0;i<columnCount;i++) {rawOutput.add(new ArrayList<Character>());}
		String[] charsOnly = uncrypted.getText().split("\\W+");
		String input = new String();
		for (int i=0;i<charsOnly.length;i++) {input+=charsOnly[i];}
		input=input.toLowerCase();
		String output = new String();
		if (input.length()%columnCount==0) {ScytaleEncryption(input.length()/columnCount);}
		else {
			int lastUsed = 0;
			for (int i=0;i<columnCount;i++) {
				if (i<input.length()%columnCount) {
					for (int j=lastUsed;j<1+lastUsed+input.length()/columnCount;j++) {
						lastUsed++;
						rawOutput.get(i).add(input.charAt(j));
					}
				} else {
					for (int j=lastUsed;j<lastUsed+input.length()/columnCount;j++) {
						lastUsed++;
						rawOutput.get(i).add(input.charAt(j));
					}
				}
			}
			for (int i=0;i<rawOutput.size();i++) {
				for (int j=0;j<rawOutput.get(i).size();j++) {
					output+=""+rawOutput.get(i).get(j);
				}
			}
			crypted.setText(output.toUpperCase());
		}
	}
	public void VigenereEncryption() {
		String[] charsOnly = uncrypted.getText().split("\\W+");
		String input = new String();
		for (int i=0;i<charsOnly.length;i++) {input+=charsOnly[i];}
		input=input.toLowerCase();
		charsOnly = key.getText().split("\\W+");
		String key = new String();
		for (int i=0;i<charsOnly.length;i++) {key+=charsOnly[i];}
		key=key.toLowerCase();
		String outputString = new String();
		for (int i=0;i<input.length();i++) {
			int nextIndex = Arrays.binarySearch(alphabet, input.charAt(i))+Arrays.binarySearch(alphabet,key.charAt(i%key.length()));
			if (nextIndex<0) {nextIndex+=26;}
			if (nextIndex>25) {nextIndex-=26;}
			outputString+=alphabet[nextIndex];
		}
		crypted.setText(outputString.toUpperCase());
	}
	public void VigenereDecryption() {
		String[] charsOnly = uncrypted.getText().split("\\W+");
		String input = new String();
		for (int i=0;i<charsOnly.length;i++) {input+=charsOnly[i];}
		input=input.toLowerCase();
		charsOnly = key.getText().split("\\W+");
		String key = new String();
		for (int i=0;i<charsOnly.length;i++) {key+=charsOnly[i];}
		key=key.toLowerCase();
		String outputString = new String();
		for (int i=0;i<input.length();i++) {
			int nextIndex = Arrays.binarySearch(alphabet, input.charAt(i))-Arrays.binarySearch(alphabet,key.charAt(i%key.length()));
			if (nextIndex<0) {nextIndex+=26;}
			if (nextIndex>25) {nextIndex-=26;}
			outputString+=alphabet[nextIndex];
		}
		crypted.setText(outputString.toUpperCase());
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(encrypt)) {
			if (methods[0].isSelected()) {CaesarCryption(Integer.parseInt(key.getText()));}
			else if (methods[1].isSelected()) {ScytaleEncryption(Integer.parseInt(key.getText()));}
			else if (methods[2].isSelected()) {VigenereEncryption();}
		} else if (e.getSource().equals(decrypt)) {
			if (methods[0].isSelected()) {CaesarCryption(-Integer.parseInt(key.getText()));}
			else if (methods[1].isSelected()) {ScytaleDecryption(Integer.parseInt(key.getText()));}
			else if (methods[2].isSelected()) {VigenereDecryption();}
		}
	}

}
