package Preprocessing;

import java.util.Comparator;

public class LineComparatorOnScore implements Comparator<Line> 
{
	public int compare(Line line1, Line line2) 
	{
		if(line1.getNetScore() > line2.getNetScore())
		{
			return -1;
		}
		else
		{
			return  1;
		}
	}
}
