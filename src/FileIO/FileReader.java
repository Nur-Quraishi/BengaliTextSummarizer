package FileIO;

import java.io.BufferedReader;

public class FileReader 
{
	public String readFile(String path)
	{
		String fileContent = "";
		try 
		{
			BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(path));
			String line = bufferedReader.readLine();
			
			while(line != null)
			{
				fileContent += line + "\n";
				line = bufferedReader.readLine();
			}
			
			bufferedReader.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return fileContent;
	}
}
