package Preprocessing;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>
{
	private int positionId;
	private String originalSentence;
	private boolean isHeadLine = false;
	private List<String> nonProcessedwordList = new ArrayList<>();
	private List<String> preProcessedwordList = new ArrayList<>();
	private List<Double> scoreList = new ArrayList<>();
	private double netScore = 0.0;
	
	public Line(int positionId, String originalSentence) 
	{
		this.positionId = positionId;
		this.originalSentence = originalSentence;
		
		populateNonProcessedwordList();
	}
	
	private void populateNonProcessedwordList()
	{
		for (String word : originalSentence.split("[\\s।?!\n%,ঃ\"\'‘’]+")) 
		{
			if(!word.trim().equals(""))
			{
				nonProcessedwordList.add(word.trim());
			}
		}
	}
	
	public void generateNetScore()
	{
		for(Double score : scoreList)
		{
			netScore += score;
		}
	}
	
	public int compareTo(Line opponent)
	{
		return this.positionId - opponent.positionId;
	}

	public int getPositionId() 
	{
		return positionId;
	}

	public void setPositionId(int positionId) 
	{
		this.positionId = positionId;
	}

	public String getOriginalSentence() 
	{
		return originalSentence;
	}

	public void setOriginalSentence(String originalSentence) 
	{
		this.originalSentence = originalSentence;
	}

	public boolean isHeadLine() 
	{
		return isHeadLine;
	}

	public void setHeadLine(boolean isHeadLine) 
	{
		this.isHeadLine = isHeadLine;
	}

	public List<String> getNonProcessedwordList() 
	{
		return nonProcessedwordList;
	}

	public void setNonProcessedwordList(List<String> nonProcessedwordList) 
	{
		this.nonProcessedwordList = nonProcessedwordList;
	}

	public List<String> getPreProcessedwordList() 
	{
		return preProcessedwordList;
	}

	public void setPreProcessedwordList(List<String> preProcessedwordList) 
	{
		this.preProcessedwordList = preProcessedwordList;
	}

	public List<Double> getScoreList() 
	{
		return scoreList;
	}

	public void setScoreList(List<Double> scoreList) 
	{
		this.scoreList = scoreList;
	}

	public double getNetScore() 
	{
		return netScore;
	}

	public void setNetScore(double netScore) 
	{
		this.netScore = netScore;
	}	
}
