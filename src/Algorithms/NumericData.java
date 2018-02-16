package Algorithms;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class NumericData extends RegexParseAlgorithm
{
	public NumericData(Corpus corpus, String regex)
	{
		super(corpus, regex);
	}

	public void calculateSimilarity() 
	{
		Pattern pattern = Pattern.compile(regex);
		List<Line> lineList = corpus.getLineList();
		int totalNumberOfWordsInTitle = lineList.get(0).getPreProcessedwordList().size();
		
		for(Line line : lineList)
		{
			double totalScore = 0.0;
			String originalSentence = line.getOriginalSentence();
			
			Matcher matcher = pattern.matcher(originalSentence);
			while(matcher.find())
			{
				totalScore ++;
			}
			
			List<Double> tempList = line.getScoreList();
			tempList.add(totalScore / totalNumberOfWordsInTitle);
			line.setScoreList(tempList);
		}
	}
}
