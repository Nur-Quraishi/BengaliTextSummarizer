package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Preprocessing.Corpus;
import Preprocessing.Line;

public class PresenceOfInvertedComma extends RegexParseAlgorithm
{
	public PresenceOfInvertedComma(Corpus corpus, String regex)
	{
		super(corpus, regex);
	}

	public void calculateSimilarity() 
	{
		Pattern pattern = Pattern.compile(regex);
		List<Line> lineList = corpus.getLineList();
		String fragmentedSymbol = "";
		
		for(Line line : lineList)
		{
			double totalScore = 0.0;
			int totalWordInLine = line.getNonProcessedwordList().size();
			String originalSentence = fragmentedSymbol + line.getOriginalSentence();
			
			Matcher matcher = pattern.matcher(originalSentence);
			while(matcher.find())
			{
				String extractedWords = matcher.group();
				char lastCharacter = extractedWords.charAt(extractedWords.length() - 1);
				
				if(lastCharacter == '\"' || lastCharacter == '\'' || lastCharacter == '’')
				{
					fragmentedSymbol = "";
				}
				else
				{
					fragmentedSymbol = Character.toString(originalSentence.charAt(matcher.start()));
				}
				totalScore += getNumberOfWords(extractedWords);
			}
			
			List<Double> tempList = line.getScoreList();
			tempList.add(totalScore / totalWordInLine);
			line.setScoreList(tempList);
		}
	}

	private int getNumberOfWords(String extractedWords)
	{
		List<String> wordList = new ArrayList<>();
		String[] words = extractedWords.split("[\\s।?!\n%,ঃ\"\'‘’]+");
		
		for(String word : words)
		{
			if(!word.trim().equals(""))
			{
				wordList.add(word.trim());
			}
		}
		
		return wordList.size();
	}
}
