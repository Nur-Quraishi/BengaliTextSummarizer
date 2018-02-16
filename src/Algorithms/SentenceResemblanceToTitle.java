package Algorithms;

import java.util.List;
import java.util.Map;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class SentenceResemblanceToTitle extends GeneralAlgorithm
{
	public SentenceResemblanceToTitle(Corpus corpus)
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		List<Line> lineList = corpus.getLineList();
		Line title = lineList.get(0);
		int totalNumberOfWordsInTitle = title.getPreProcessedwordList().size();
		Map<Line, Map<String, Integer>> wordsInEachLineMap = corpus.getWordsInEachLineMap();
		Map<String, Integer> wordMapOfTitle = wordsInEachLineMap.get(title);
		
		for(Line line : lineList)
		{
			double score = 0.0;
			List<String> wordListOfLine = line.getPreProcessedwordList();
			for(String word : wordListOfLine)
			{
				if(wordMapOfTitle.containsKey(word))
				{
					score ++;
				}
			}
			
			score /= totalNumberOfWordsInTitle;
			List<Double> tempList = line.getScoreList();
			tempList.add(score);
			line.setScoreList(tempList);
		}
	}
}
