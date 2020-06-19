import java.io.*;
public class TextFileWriter{
	public void writeToFile(String fileName,String s){		
		try{			
			//Create a print writer on file
			FileWriter fw = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(fw);			
			out.println(s);						
			out.close();			
		}catch(IOException e){
			e.printStackTrace();
		}//end catch(IOException e)

	}//end writeToFile()
}//end TextFileWriter
