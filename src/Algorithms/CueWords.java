package Algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class CueWords extends GeneralAlgorithm
{
	public CueWords(Corpus corpus)
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		List<String> cueWordList = getCueWordList();
		List<Line> lineList = corpus.getLineList();
		
		for(Line line : lineList)
		{
			double totalScore = 0.0;
			int totalWordInLine = line.getPreProcessedwordList().size();
			String originalSentence = line.getOriginalSentence();
			
			for(String word : cueWordList)
			{
				if(originalSentence.contains(word))
				{
					Pattern pattern = Pattern.compile(word);
					Matcher matcher = pattern.matcher(originalSentence);
					
					while (matcher.find())
					{
						totalScore ++;
					}
				}
			}
			List<Double> tempList = line.getScoreList();
			tempList.add(totalScore / totalWordInLine);
			line.setScoreList(tempList);
		}
	}
	
	private List<String> getCueWordList()
	{
		List<String> cueWordList = new ArrayList<>();
		try 
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("").getAbsolutePath() + "/src/Resources/Files/cueWordList.txt"));
			String line = bufferedReader.readLine();

			 while(line != null)
			 {
				 cueWordList.add(line.trim());
				 line = bufferedReader.readLine();
			 }
			 bufferedReader.close();
		} 
		catch (Exception e) 
		{
			System.out.println("Cue word list file does not exist. Set correct path");
			e.printStackTrace();
		}
		
		return cueWordList;
	}
}
