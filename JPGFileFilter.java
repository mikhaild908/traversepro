import java.io.File;
import javax.swing.filechooser.*;

public class JPGFileFilter extends FileFilter{	
	
	public boolean accept(File f){
		String fileName = f.getName();			
		int i = fileName.lastIndexOf('.');

		if(i>0 && i<fileName.length()-1 && fileName.substring(i+1).toLowerCase().equals("jpg")){
			return true;
		}
		else if(f.isDirectory())
			return true;
		else{
			return false;
		}
	}

	public String getDescription(){
		return "JPG Files (*.jpg)";
	}
}
