package Algorithms;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Preprocessing.Corpus;
import Preprocessing.Line;

public abstract class CosineSimilarityAlgorithm implements InterfaceAlgorithm
{
	protected Corpus corpus;
	
	public CosineSimilarityAlgorithm(Corpus corpus) 
	{
		this.corpus = corpus;
	}

	public abstract void calculateSimilarity();
	
	protected double calculateCosineSimilarity(Line line1, Line line2, Map<String, Integer> wordMapOfLine1, Map<String, Integer> wordMapOfLine2)
	{
		double currentScore = 0.0;
		
		for(String word: getWordsInBothLine(line1, line2))
		{
			double temp = 0.0;
			if(wordMapOfLine1.containsKey(word))
			{
				temp += wordMapOfLine1.get(word);
			}
			
			if(wordMapOfLine2.containsKey(word))
			{
				temp *= wordMapOfLine2.get(word);
			}
			else
			{
				temp = 0.0;
			}
			currentScore += temp;
		}
		currentScore /= (getModulus(wordMapOfLine1) * getModulus(wordMapOfLine2));
		return currentScore;
	}
	
	protected double getModulus(Map<String, Integer> wordMapOfline) 
	{
		double modulus = 0.0;
		
		for(String word : wordMapOfline.keySet())
		{
			modulus += Math.pow(wordMapOfline.get(word), 2);
		}
		
		return Math.sqrt(modulus);
	}

	protected Set<String> getWordsInBothLine(Line line1, Line line2)
	{
		Set<String> wordList = new TreeSet<>();
		
		for(String word: line1.getPreProcessedwordList())
		{
			wordList.add(word);
		}
		
		for(String word: line2.getPreProcessedwordList())
		{
			wordList.add(word);
		}
		
		return wordList;
	}
}
