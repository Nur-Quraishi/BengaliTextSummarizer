package Algorithms;

import java.util.List;
import java.util.Map;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class AggregateSimilarity extends CosineSimilarityAlgorithm
{
	public AggregateSimilarity(Corpus corpus) 
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		Map<Line, Map<String, Integer>> wordsInEachLineMap = corpus.getWordsInEachLineMap();
		List<Line> lineList = corpus.getLineList();
		
		for(int i = 0; i < lineList.size(); i++)
		{
			Line line1 = lineList.get(i);
			double totalScore = 0.0;
			Map<String, Integer> wordMapOfLine1 = wordsInEachLineMap.get(line1);
			
			for(int j = i + 1; j < lineList.size(); j++)
			{
				Line line2 = lineList.get(j);
				Map<String, Integer> wordMapOfLine2 = wordsInEachLineMap.get(line2);
				double currentScore = calculateCosineSimilarity(line1, line2, wordMapOfLine1, wordMapOfLine2);
				
				if(currentScore > 0.16)
				{
					totalScore += currentScore;
				}
			}
			
			List<Double> tempList = line1.getScoreList();
			tempList.add(totalScore);
			line1.setScoreList(tempList);
		}
	}
}
