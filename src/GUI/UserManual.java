package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class UserManual extends JFrame{

	private static final long serialVersionUID = 1L;


	public UserManual(){
		init();
	}

	private void init() {
		setTitle("Bengali Text Summarizer");
		
		JPanel p = new JPanel();
		p.setBorder(new TitledBorder( new EtchedBorder(), "User Manual"));
		p.setLayout(new BorderLayout());
		
		JTextArea jTextArea = new JTextArea();
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setFont(new Font("Verdana", Font.BOLD, 14));
		jTextArea.setForeground(new Color(32, 137, 109));
		jTextArea.setText("\t              User Manual \n\n" +
				"      This Manual will guide a user about how to use this \n" +
				"      software. This will give the complete overview of \n" +
				"      this software \n\n" +
				"\t     To generate Summary\n\n" +
				"      1. Select the .txt file contains Bangla texts\n" +
				"      2. Click on the Generate Summary button\n\n" +
				"\t\tOr\n\n" +
				"      1. Type or paste text in Bengali in the upper text area\n" +
				"      2. Click on the Generate Summary button");
		
		
		JScrollPane jScrollPane = new  JScrollPane(jTextArea);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		p.add(jScrollPane, BorderLayout.CENTER);
		
		add(p);
		setVisible(true);
		setSize(500, 660);
		setLocation(300, 100);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public static void main(String[] args) {
		new UserManual();
	}

}
