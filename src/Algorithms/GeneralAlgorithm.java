package Algorithms;

import Preprocessing.Corpus;

public abstract class GeneralAlgorithm implements InterfaceAlgorithm
{
	protected Corpus corpus;
	
	public GeneralAlgorithm(Corpus corpus) 
	{
		this.corpus = corpus;
	}

	public abstract void calculateSimilarity();
}
