package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import Preprocessing.Corpus;
import Preprocessing.Line;
import Preprocessing.LineComparatorOnPosition;
import Preprocessing.LineComparatorOnScore;

public class AlgorithmSimulator 
{
	private Corpus corpus;

	public AlgorithmSimulator(Corpus corpus) 
	{
		this.corpus = corpus;
	}
	
	private void f1()
	{
		AggregateSimilarity aggregateSimilarity = new AggregateSimilarity(corpus);
		aggregateSimilarity.calculateSimilarity();
	}
	
	private void f2()
	{
		BushyPath bushyPath = new BushyPath(corpus);
		bushyPath.calculateSimilarity();
	}
	
	private void f3()
	{
		SentencePosition sentencePosition = new SentencePosition(corpus);
		sentencePosition.calculateSimilarity();
	}
	
	private void f4()
	{
		SentenceResemblanceToTitle sentenceResemblanceToTitle = new SentenceResemblanceToTitle(corpus);
		sentenceResemblanceToTitle.calculateSimilarity();
	}
	
	private void f5()
	{
		TermFrequencyInverseSentenceFrequency termFrequencyInverseSentenceFrequency = new TermFrequencyInverseSentenceFrequency(corpus);
		termFrequencyInverseSentenceFrequency.calculateSimilarity();
	}
	
	private void f6()
	{
		CueWords cueWords = new CueWords(corpus);
		cueWords.calculateSimilarity();
	}
	
	private void f7()
	{
		KeywordsInSentence keywordsInSentence = new KeywordsInSentence(corpus);
		keywordsInSentence.calculateSimilarity();
	}
	
	private void f8()
	{
		String regex = "(শনিবার|রবিবার|রোববার|সোমবার|মঙ্গলবার|বুধবার|বৃহস্পতিবার|শুক্রবার)|(([০-৯]|০[১-৯]|[১২][০-৯]|৩[০১])(লা |রা |ঠা |ই |শে |[- /.])([০-৯]|০[১-৯]|১[০-২]|জানুয়ারি|ফেব্রুয়ারি|মার্চ|এপ্রিল|মে|জুন|জুলাই|আগস্ট|সেপ্টেম্বর|অ|ক্টোবর|নভেম্বরডিসেম্বর)[- /.]([০-৯][০-৯][০-৯][০-৯]|[০-৯][০-৯])?)";
		DateFormat dateFormat = new DateFormat(corpus, regex);
		dateFormat.calculateSimilarity();
	}
	
	private void f9()
	{
		String regex = "(([০-৯]+)([.]([০-৯]+))?)|([.]([০-৯]+))";
		NumericData numericData = new NumericData(corpus, regex);
		numericData.calculateSimilarity();
	}
	
	private void f10()
	{
		String regex = "(\"([^\"])*(\")?)|(\'([^\'])*(\')?)|(‘([^’])*(’)?)";
		PresenceOfInvertedComma presenceOfInvertedComma = new PresenceOfInvertedComma(corpus, regex);
		presenceOfInvertedComma.calculateSimilarity();
	}
	
	private void f11()
	{
		String regex = "[$%؋৳£€ƒ₮₨₫₼₭¢₦៛¥₡₩₱£₴﷼฿₪]";
		PresenceOfSpecialSymbol presenceOfSpecialSymbol = new PresenceOfSpecialSymbol(corpus, regex);
		presenceOfSpecialSymbol.calculateSimilarity();
	}
	
	private void f12()
	{
		String regex = "((http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?)|(\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b)";
		PresenceOfURLOrEmail presenceOfURLOrEmail = new PresenceOfURLOrEmail(corpus, regex);
		presenceOfURLOrEmail.calculateSimilarity();
	}
	
	private void executeAllF()
	{
		f1();
		f2();
		f3();
		f4();
		f5();
		f6();
		f7();
		f8();
		f9();
		f10();
		f11();
		f12();
	}
	
	public String generateSummary()
	{	
		executeAllF();
		String summary = corpus.getLineList().get(0).getOriginalSentence();
		List<Line> lineList = sortLineListBasedOnScore();
		int totalNumberOfLines = lineList.size();
		int numberOfLinesChosen = (int) Math.round(totalNumberOfLines * .40);
	
		List<Line> selectedLineList = populateSelectedLineList(lineList, numberOfLinesChosen);
		selectedLineList = checkCosineSimilarity(selectedLineList, lineList, numberOfLinesChosen);
		selectedLineList = sortLineListBasedOnPosition(selectedLineList);
		
		for(Line line : selectedLineList)
		{
			summary += line.getOriginalSentence();
		}
		return summary;
	}

	private List<Line> sortLineListBasedOnScore()
	{
		List<Line> lineList = corpus.getLineList();
		for(Line line : lineList)
		{
			line.generateNetScore();
		}
		lineList.remove(0);
		
		LineComparatorOnScore lineComparatorOnScore = new LineComparatorOnScore();
		Collections.sort(lineList, lineComparatorOnScore);
		return lineList;
	}
	
	private List<Line> populateSelectedLineList(List<Line> lineList, int numberOfLinesChosen)
	{
		List<Line> selectedLineList = new ArrayList<>();
		int counter = 0;
		
		while(counter < numberOfLinesChosen)
		{
			selectedLineList.add(lineList.get(counter));
			counter ++;
		}
		return selectedLineList;
	}
	
	private List<Line> checkCosineSimilarity(List<Line> selectedLineList, List<Line> lineList, int numberOfLinesChosen) 
	{
		Map<Line, Map<String, Integer>> wordsInEachLineMap = corpus.getWordsInEachLineMap();
		AggregateSimilarity aggregateSimilarity = new AggregateSimilarity(corpus);
		
		int newI = -1;
		int newJ = -1;
		double maximumScore = 0.60;
		
		for(int i = 0; i < selectedLineList.size(); i++)
		{
			Line line1 = selectedLineList.get(i);
			Map<String, Integer> wordMapOfLine1 = wordsInEachLineMap.get(line1);
			
			for(int j = i + 1; j < selectedLineList.size(); j++)
			{
				Line line2 = selectedLineList.get(j);
				Map<String, Integer> wordMapOfLine2 = wordsInEachLineMap.get(line2);
				double currentScore = aggregateSimilarity.calculateCosineSimilarity(line1, line2, wordMapOfLine1, wordMapOfLine2);
				
				if(currentScore >= maximumScore)
				{
					maximumScore = currentScore;
					newI = i;
					newJ = j;
				}
			}
		}
		
		if(newI != -1 && newJ != -1)
		{
			Line line1 = selectedLineList.get(newI);
			Line line2 = selectedLineList.get(newJ);
			
			if(line1.getPreProcessedwordList().size() >= line2.getPreProcessedwordList().size())
			{
				selectedLineList.remove(newJ);
			}
			else
			{
				selectedLineList.remove(newI);
			}
			selectedLineList.add(lineList.get(numberOfLinesChosen));
		}
		return selectedLineList;
	}
	
	private List<Line> sortLineListBasedOnPosition(List<Line> selectedLineList) 
	{
		LineComparatorOnPosition lineComparatorOnPosition = new LineComparatorOnPosition();
		Collections.sort(selectedLineList, lineComparatorOnPosition);
		return selectedLineList;
	}

	public Corpus getCorpus() 
	{
		return corpus;
	}

	public void setCorpus(Corpus corpus) 
	{
		this.corpus = corpus;
	}
}