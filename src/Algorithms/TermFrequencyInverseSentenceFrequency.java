package Algorithms;

import java.util.List;
import java.util.Map;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class TermFrequencyInverseSentenceFrequency extends GeneralAlgorithm
{
	public TermFrequencyInverseSentenceFrequency(Corpus corpus)
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		Map<Line, Map<String, Integer>> wordsInEachLineMap = corpus.getWordsInEachLineMap();
		List<Line> lineList = corpus.getLineList();
		
		for(Line line : lineList)
		{
			Map<String, Integer> wordMapOfLine = wordsInEachLineMap.get(line);
			double totalScore = 0.0;
			
			for(String word : wordMapOfLine.keySet())
			{
				totalScore += corpus.getTFISF(word);
			}
			List<Double> tempList = line.getScoreList();
			tempList.add(totalScore);
			line.setScoreList(tempList);
		}
	}
}
