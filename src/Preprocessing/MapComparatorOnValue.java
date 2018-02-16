package Preprocessing;

import java.util.Comparator;
import java.util.Map;

public class MapComparatorOnValue implements Comparator<String>
{
	private Map<String, Integer> map;
	
	public MapComparatorOnValue(Map<String, Integer> map)
	{
		this.map = map;
	}

	public int compare(String key1, String key2) 
	{
		if(map.get(key1) > map.get(key2))
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
}
