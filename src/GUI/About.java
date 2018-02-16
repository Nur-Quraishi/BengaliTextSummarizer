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

public class About extends JFrame{

	private static final long serialVersionUID = 1L;


	public About(){
		init();
	}

	private void init() {
		setTitle("Bengali Text Summarizer");
		
		JPanel p = new JPanel();
		p.setBorder(new TitledBorder( new EtchedBorder(), "Description"));
		p.setLayout(new BorderLayout());
		
		JTextArea jTextArea = new JTextArea();
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setFont(new Font("Verdana", Font.BOLD, 14));
		jTextArea.setForeground(new Color(32, 137, 109));
		jTextArea.setText("\tDeveloped By: M. A. Nur Quraishi \n\t               Version- 1.0\n\n" +
				"Bengali Text Summarizer is an open-source tool for summarizing Bengali article or text and developed " +
				"in java Programming language. This tool used several renown text summarization algorithms for summarizing " +
				"a bengali passage or article.\n\nThis Tool takes a .txt file or typed/copied text as input which contains " +
				"Bengali text. Then it generates the summary according to the given article using the implemented algorithms. " +
				"The algorithms which are used in this summarizer are:\n\n" +
				"1. Aggregate Cosine Similarity\n" +
				"2. Bushy Path Analysis\n" +
				"3. Sentence Position Analysis\n" +
				"4. Sentence Resemblace to Title Analysis\n" +
				"5. Term Frequency-Inverse Sentence Frequency Analysis\n" +
				"6. Cue Words Analysis\n" +
				"7. Keywords in Sentence Analysis\n" +
				"8. Date Format Analysis\n" +
				"9. Numeric  Data Analysis\n" +
				"10. Presence of Inverted Comma Analysis\n" +
				"11. Presence of Special Symbol Analysis\n" +
				"12. Presence of URL or Email Address Analysis");
		
		
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
		new About();
	}
}
