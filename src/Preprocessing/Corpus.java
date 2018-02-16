package Preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Corpus 
{
	private String article;
	private List<Line> lineList = new ArrayList<>();
	private List <String> stopWordList = new ArrayList<>();
	private Map <String, Integer> wordsInArticleMap = new TreeMap<>();
	private Map<Line, Map<String, Integer>> wordsInEachLineMap = new TreeMap<>();
	private Map <String, Double> wordsWithTFISFMap = new TreeMap<>();
	
	public Corpus(String article)
	{
		this.article = article;
		populateStopWordList();
	}
	
	private void populateStopWordList()
	{
		 try
		 {
			 BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(new File("").getAbsolutePath() + "/src/Resources/Files/stopWordList.txt")));
			 String line = bufferedReader.readLine();

			 while(line != null)
			 {
				 stopWordList.add(line.trim());
				 line = bufferedReader.readLine();
			 }
			 bufferedReader.close();
		 }
		 catch(Exception e)
		 {
			 System.out.println("Stop word list file does not exist. Set correct path");
			 e.printStackTrace();
		 }
	}
	
	public void populateLineList() 
	{
		Pattern pattern = Pattern.compile("[।?!]+");
		Matcher matcher = pattern.matcher(article);
		List<Character> listOfEndSymbols = new ArrayList<>();
		
		while (matcher.find())
		{
			listOfEndSymbols.add(article.charAt(matcher.start()));
		}
		
		int sequenceId = 1;
		int decreaseBy = 1;
		for(String line : article.split("[।?!]+"))
		{
			if(sequenceId == 1)
			{
				String[] splittedLines = line.split("\n");
				if(splittedLines.length > 1)
				{
					Line headline = new Line(sequenceId, splittedLines[0] + "\n");
					headline.setHeadLine(true);
					lineList.add(headline);
					sequenceId ++;
					decreaseBy = 2;
					lineList.add(new Line(sequenceId, splittedLines[1] + listOfEndSymbols.get(sequenceId - decreaseBy).toString()));
				}
				else
				{
					Line headline = new Line(sequenceId, line + listOfEndSymbols.get(sequenceId - decreaseBy).toString());
					headline.setHeadLine(true);
					lineList.add(headline);
				}
			}
			else
			{
				String endSymbol = (sequenceId - decreaseBy < listOfEndSymbols.size()) ? listOfEndSymbols.get(sequenceId - decreaseBy).toString() : "";
				lineList.add(new Line(sequenceId, line + endSymbol));
			}
			sequenceId ++;
		}
	}
	
	public void buildCorpus()
	{
		try
		{
			RuleFileParser parser = new RuleFileParser(new File("").getAbsolutePath() + "/src/Resources/Files/common.rules");
			for(Line line : lineList)
			{
				Map <String, Integer> wordsInThisLineMap = new TreeMap<>();
				List<String> preProcessedwordList = new ArrayList<>();
				for(String word : line.getNonProcessedwordList()) 
				{
					if(!isStopListWord(word))
			        {
						word = parser.stemOfWord(word).trim();
						if(!wordsInThisLineMap.containsKey(word))
						{
							wordsInThisLineMap.put(word, 1);
						}
						else
						{
							wordsInThisLineMap.put(word, wordsInThisLineMap.get(word) + 1);
						}
						
						if(!wordsInArticleMap.containsKey(word))
						{
							wordsInArticleMap.put(word, 1);
						}
						else
						{
							wordsInArticleMap.put(word, wordsInArticleMap.get(word) + 1);
						}
						
						preProcessedwordList.add(word);
			        }
				}
				line.setPreProcessedwordList(preProcessedwordList);
				wordsInEachLineMap.put(line, wordsInThisLineMap);
			}
		}
		catch(Exception e) 
		{
			System.out.println("Common Rule file does not exist. Set correct path");
			e.printStackTrace();
		}
		
	}
	
	boolean isStopListWord(String word)
    {
        if(stopWordList.contains(word))
        {
            return true;
        }
        return false;
    }
	
	public void generateTFISF()
	{
		for(String word : wordsInArticleMap.keySet())
		{
			wordsWithTFISFMap.put(word, getTF(word) * getISF(word));
		}
	}
	
	public double getTFISF(String word)
	{
		double value = 0;
		if(wordsWithTFISFMap.containsKey(word))
		{
			value = wordsWithTFISFMap.get(word);
		}
		
		return value;
	}
	
	private int getTF(String word)
	{
		int termFrequency = 0;
		if(wordsInArticleMap.containsKey(word))
		{
			termFrequency = wordsInArticleMap.get(word);
		}
		
		return termFrequency;
	}
	
	private double getISF(String word)
	{
		int totalNumberOfSentences = lineList.size();
		int totalNumberOfSentencesWithWord = 0;
		
		for(Line line : lineList)
		{
			Map<String, Integer> wordMapOfLine = wordsInEachLineMap.get(line);
			if(wordMapOfLine.containsKey(word))
			{
				totalNumberOfSentencesWithWord ++;
			}
		}
		return Math.log10((double)totalNumberOfSentences / totalNumberOfSentencesWithWord);
	}
	
	public List<String> getKeyWordList()
	{
		List<String> keyWordList = new ArrayList<>();
		int numberOfKeyWords = (int) Math.round(wordsInArticleMap.size() * .10);
		Map<String, Integer> sortedWordMap = new TreeMap<>(new MapComparatorOnValue(wordsInArticleMap));
		sortedWordMap.putAll(wordsInArticleMap);
		
		int counter = 0;
		for(String word : sortedWordMap.keySet())
		{
			if(counter == numberOfKeyWords)
			{
				break;
			}
			
			keyWordList.add(word);
			counter ++;
		}
		
		return keyWordList;
	}
	
	public int getTotalNumberOfURLandEmail()
	{
		int totalNumberOfURLandEmail = 0;
		
		String regex = "((http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?)|(\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article);
		
		while(matcher.find())
		{
			totalNumberOfURLandEmail ++;
		}
		
		return totalNumberOfURLandEmail;
	}

	public String getArticle() 
	{
		return article;
	}

	public void setArticle(String article) 
	{
		this.article = article;
	}

	public List<Line> getLineList() 
	{
		return lineList;
	}

	public void setLineList(List<Line> lineList) 
	{
		this.lineList = lineList;
	}

	public List<String> getStopWordList() {
		return stopWordList;
	}

	public void setStopWordList(List<String> stopWordList) 
	{
		this.stopWordList = stopWordList;
	}

	public Map<String, Integer> getWordsInArticleMap() 
	{
		return wordsInArticleMap;
	}

	public void setWordsInArticleMap(Map<String, Integer> wordsInArticleMap) 
	{
		this.wordsInArticleMap = wordsInArticleMap;
	}

	public Map<Line, Map<String, Integer>> getWordsInEachLineMap() 
	{
		return wordsInEachLineMap;
	}

	public void setWordsInEachLineMap(Map<Line, Map<String, Integer>> wordsInEachLineMap) 
	{
		this.wordsInEachLineMap = wordsInEachLineMap;
	}
}
