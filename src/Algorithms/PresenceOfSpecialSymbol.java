package Algorithms;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class PresenceOfSpecialSymbol extends RegexParseAlgorithm
{
	public PresenceOfSpecialSymbol(Corpus corpus, String regex)
	{
		super(corpus, regex);
	}

	public void calculateSimilarity() 
	{
		Pattern pattern = Pattern.compile(regex);
		List<Line> lineList = corpus.getLineList();
		
		for(Line line : lineList)
		{
			double totalScore = 0.0;
			int totalWordInLine = line.getPreProcessedwordList().size();
			String originalSentence = line.getOriginalSentence();
			
			Matcher matcher = pattern.matcher(originalSentence);
			while(matcher.find())
			{
				totalScore ++;
			}
			
			List<Double> tempList = line.getScoreList();
			tempList.add(totalScore / totalWordInLine);
			line.setScoreList(tempList);
		}
	}
}
