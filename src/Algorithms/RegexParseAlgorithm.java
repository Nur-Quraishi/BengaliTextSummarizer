package Algorithms;

import Preprocessing.Corpus;

public abstract class RegexParseAlgorithm implements InterfaceAlgorithm
{
	protected Corpus corpus;
	protected String regex;
	
	public RegexParseAlgorithm(Corpus corpus, String regex)
	{
		this.corpus = corpus;
		this.regex = regex;
	}
	
	public abstract void calculateSimilarity();
}
