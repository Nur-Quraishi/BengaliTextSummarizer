package Algorithms;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class PresenceOfURLOrEmail extends RegexParseAlgorithm
{
	public PresenceOfURLOrEmail(Corpus corpus, String regex)
	{
		super(corpus, regex);
	}

	public void calculateSimilarity() 
	{
		Pattern pattern = Pattern.compile(regex);
		List<Line> lineList = corpus.getLineList();
		int totalNumberOfURLandEmail = corpus.getTotalNumberOfURLandEmail();
		
		if(totalNumberOfURLandEmail > 0)
		{
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
				tempList.add(totalScore / totalNumberOfURLandEmail);
				line.setScoreList(tempList);
			}
		}
		else
		{
			for(Line line : lineList)
			{
				List<Double> tempList = line.getScoreList();
				tempList.add(0.0);
				line.setScoreList(tempList);
			}
		}		
	}
}
