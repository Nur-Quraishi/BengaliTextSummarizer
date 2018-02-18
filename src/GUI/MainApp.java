package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import com.jtattoo.plaf.texture.TextureLookAndFeel;

import Algorithms.AlgorithmSimulator;
import FileIO.FileReader;
import Preprocessing.Corpus;

public class MainApp extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JTextField filePathJTextBox;
	JTextArea jTextArea, jTextArea1;
	
	private void init() {
		setTitle("Bengali Text Summarizer");
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		
		JMenuBar mBar = showMenuBar();
		
		JLabel panelLabel = new JLabel();
		
		JLabel tLabel = new JLabel("Select File");
		tLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		tLabel.setBounds(50, 10, 80, 30);
		
		filePathJTextBox = new JTextField();
		filePathJTextBox.setBounds(140, 10, 150, 30);
		filePathJTextBox.setEditable(false);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(300, 10, 60, 30);
		btnBrowse.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fileChooser();
			}
		});
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(370, 10, 60, 30);
		btnReset.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jTextArea.setText("");
				jTextArea.setEditable(true);
				filePathJTextBox.setText("");
				jTextArea1.setText("");
			}
		});
		
		JLabel qLabel = new JLabel("Or");
		qLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		qLabel.setBounds(250, 40, 50, 30);

		JLabel sLabel = new JLabel("Paste the Full Article Here:");
		sLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		sLabel.setBounds(150, 60, 300, 30);
		
		Font font = new Font("Arial Unicode MS", Font.BOLD, 14);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,MainApp.class.getResourceAsStream("/Resources/Fonts/Siyamrupali.ttf"));
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
		} catch (FontFormatException | IOException e1) {
			System.out.println("Font file does not exist. Set correct path");
			e1.printStackTrace();
		}
		
		jTextArea = new JTextArea();
		jTextArea.setEditable(true);
		jTextArea.setLineWrap(true);
		jTextArea.setBounds(5, 90, 480, 250);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setFont(new Font(font.getFontName(), Font.PLAIN, 14));
		jTextArea.setForeground(new Color(0, 0, 0));
		
		JScrollPane jScrollPane = new  JScrollPane(jTextArea);
		jScrollPane.setBounds(5, 90, 480, 230);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton generateSummary = new JButton("Generate Summary");
		generateSummary.setFont(new Font("Verdana", Font.BOLD, 14));
		generateSummary.setBounds(170, 330, 160, 40);
		panelLabel.add(generateSummary);
		generateSummary.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String originalArticle = jTextArea.getText();
				jTextArea1.setText(generateSummary(originalArticle));
			}
		});
		
		JLabel rLabel = new JLabel("Summary:");
		rLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		rLabel.setBounds(210, 370, 100, 30);
		
		jTextArea1 = new JTextArea();
		jTextArea1.setEditable(false);
		jTextArea1.setLineWrap(true);
		jTextArea1.setBounds(5, 400, 480, 200);
		jTextArea1.setWrapStyleWord(true);
		jTextArea1.setFont(new Font(font.getFontName(), Font.PLAIN, 14));
		jTextArea1.setForeground(new Color(32, 137, 109));
		
		JScrollPane jScrollPane1 = new  JScrollPane(jTextArea1);
		jScrollPane1.setBounds(5, 400, 480, 200);
		jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelLabel.add(qLabel);
		panelLabel.add(tLabel);
		panelLabel.add(sLabel);
		panelLabel.add(rLabel);
		panelLabel.add(filePathJTextBox);
		panelLabel.add(btnBrowse);
		panelLabel.add(btnReset);
		
		
		p.add(jScrollPane1, BorderLayout.CENTER);
		p.add(jScrollPane, BorderLayout.CENTER);
		p.add(panelLabel, BorderLayout.CENTER);
		
		add(p);
		setJMenuBar(mBar);
		setVisible(true);
		setSize(500, 660);
		setLocation(300, 100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JMenuBar showMenuBar() {
		JMenuBar mBar = new JMenuBar();
		
		JMenu menu1 = new JMenu("File");
		JMenuItem mit11 = new JMenuItem("Open");
		mit11.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fileChooser();
			}
		});
		
		JMenuItem mit12 = new JMenuItem("Exit");
		mit12.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JMenu menu2 = new JMenu("Help");
		
		JMenuItem mit21 = new JMenuItem("About");
		mit21.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
		
		JMenuItem mit22 = new JMenuItem("User Manual");
		mit22.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new UserManual();
			}
		});
		
		menu1.add(mit11);
		menu1.add(mit12);
		menu2.add(mit21);
		menu2.add(mit22);
		
		mBar.add(menu1);
		mBar.add(menu2);
		return mBar;
	}
	
	private void fileChooser(){
		JFileChooser fChooser = new JFileChooser();
		int filechoosen = fChooser.showOpenDialog(new JFrame());
		
		if(filechoosen == JFileChooser.APPROVE_OPTION){
			filePathJTextBox.setText(fChooser.getSelectedFile().toString());
			String filePath = filePathJTextBox.getText();
			if(!filePath.isEmpty() && filePath != null && !filePath.equals("")){
				FileReader fileReader = new FileReader();
				String fileContent = fileReader.readFile(filePath);
				jTextArea.setText(fileContent);
				jTextArea.setEditable(false);
				jTextArea1.setText("");
			}
		}
	}
	
	public String generateSummary(String originalArticle){
		String output = "";
		if(originalArticle.isEmpty() || originalArticle == null){
			 JOptionPane.showMessageDialog(null, "Article is missing!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else{
			Corpus corpus = new Corpus(originalArticle);
			corpus.populateLineList();
			corpus.buildCorpus();
			corpus.generateTFISF();
			
			AlgorithmSimulator algorithmSimulator = new AlgorithmSimulator(corpus);
			output = algorithmSimulator.generateSummary();
		}
		return output;
	}
	
	public static void main(String[] args) {
		try {
			//	UIManager.setLookAndFeel(new BernsteinLookAndFeel());
			//	UIManager.setLookAndFeel(new AcrylLookAndFeel());
			//	UIManager.setLookAndFeel(new AluminiumLookAndFeel());
			//	UIManager.setLookAndFeel(new FastLookAndFeel());
			//	UIManager.setLookAndFeel(new GraphiteLookAndFeel());
			//	UIManager.setLookAndFeel(new LunaLookAndFeel());
			//	UIManager.setLookAndFeel(new McWinLookAndFeel());
			//	UIManager.setLookAndFeel(new MintLookAndFeel());
			//	UIManager.setLookAndFeel(new NoireLookAndFeel());
			//	UIManager.setLookAndFeel(new SmartLookAndFeel());
				UIManager.setLookAndFeel(new TextureLookAndFeel());
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		new MainApp().init(); 
	}
}
