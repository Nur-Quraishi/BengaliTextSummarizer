package Preprocessing;

import java.util.Comparator;

public class LineComparatorOnPosition implements Comparator<Line>
{
	public int compare(Line line1, Line line2) 
	{
		if(line1.getPositionId() < line2.getPositionId())
		{
			return -1;
		}
		else
		{
			return  1;
		}
	}
}
