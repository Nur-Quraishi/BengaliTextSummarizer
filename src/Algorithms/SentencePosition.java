package Algorithms;

import java.util.List;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class SentencePosition extends GeneralAlgorithm
{
	public SentencePosition(Corpus corpus)
	{
		super(corpus);
	}

	public void calculateSimilarity() 
	{
		List<Line> lineList = corpus.getLineList();
		int totalLines = lineList.size();
		int counter = 0;
		
		for(Line line : lineList)
		{
			double score;
			if(counter == totalLines-1)
			{
				score = 1;
			}
			else
			{
				score = 1 - (((double)line.getPositionId()-2) / totalLines);
			}
			List<Double> tempList = line.getScoreList();
			tempList.add(score);
			line.setScoreList(tempList);
			counter ++;
		}
	}
}
