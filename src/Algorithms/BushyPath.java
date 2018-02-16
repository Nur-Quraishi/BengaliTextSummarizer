package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class BushyPath extends CosineSimilarityAlgorithm
{
	public BushyPath(Corpus corpus)
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		Map<Line, Map<String, Integer>> wordsInEachLineMap = corpus.getWordsInEachLineMap();
		List<Line> lineList = corpus.getLineList();
		List<Integer>connectedEdgeListOfEachLine = new ArrayList<>();
		int maximumNumberOfConnectedEdges = -1;
		
		for(int i = 0; i < lineList.size(); i++)
		{
			Line line1 = lineList.get(i);
			int numberOfConnectedEdges = 0;
			Map<String, Integer> wordMapOfLine1 = wordsInEachLineMap.get(line1);
			
			for(int j = i + 1; j < lineList.size(); j++)
			{
				Line line2 = lineList.get(j);
				Map<String, Integer> wordMapOfLine2 = wordsInEachLineMap.get(line2);
				double currentScore = calculateCosineSimilarity(line1, line2, wordMapOfLine1, wordMapOfLine2);
				
				if(currentScore > 0.16)
				{
					numberOfConnectedEdges ++;
				}
			}
			
			if(numberOfConnectedEdges > maximumNumberOfConnectedEdges)
			{
				maximumNumberOfConnectedEdges = numberOfConnectedEdges;
			}
			connectedEdgeListOfEachLine.add(numberOfConnectedEdges);
		}
		
		int counter = 0;
		for(Line line : lineList)
		{
			List<Double> tempList = line.getScoreList();
			double totalScore = (double)connectedEdgeListOfEachLine.get(counter) / maximumNumberOfConnectedEdges;
			tempList.add(totalScore);
			line.setScoreList(tempList);
			counter ++;
		}
	}
}
