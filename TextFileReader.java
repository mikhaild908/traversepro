import java.io.*;
public class TextFileReader{
	private String passThis;
	public String ReadFile(String fileName){		
		try{			
			File file = new File(fileName);
			String s;
			try{				
				BufferedReader in = new BufferedReader(new FileReader(file));				
				s=in.readLine();
				passThis = s;	
				
				while(s!=null){
					s = in.readLine();
					passThis += s;
				}
				
				in.close();//Close buffered reader. This also closes the FileReader				

			}catch(FileNotFoundException e1){
				passThis = "File not found: " + file;
			}catch(IOException e2){
				passThis = "IOException";
			}//end inner try
		}catch(ArrayIndexOutOfBoundsException e3){
			passThis = "ArrayIndexOutOfBoundsException";			
		}//end catch(ArrayIndexOutOfBoundsException e3)

		return passThis;
	}//end ReadFile()

}//end TextFileReader
