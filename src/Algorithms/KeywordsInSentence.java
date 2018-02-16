package Algorithms;

import java.util.List;
import java.util.Map;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class KeywordsInSentence extends GeneralAlgorithm
{
	public KeywordsInSentence(Corpus corpus)
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		List<String> keyWordList = corpus.getKeyWordList();
		List<Line> lineList = corpus.getLineList();
		Map<Line, Map<String, Integer>> wordsInEachLineMap = corpus.getWordsInEachLineMap();
		
		for(Line line : lineList)
		{
			Map<String, Integer> wordMapOfLine = wordsInEachLineMap.get(line);
			double totalScore = 0.0;
			int totalWordInLine = line.getPreProcessedwordList().size();
			
			for(String word : wordMapOfLine.keySet())
			{
				if(keyWordList.contains(word))
				{
					totalScore += wordMapOfLine.get(word);
				}
			}
			List<Double> tempList = line.getScoreList();
			tempList.add(totalScore / totalWordInLine);
			line.setScoreList(tempList);
		}
	}

}
