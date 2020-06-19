public class Azimuth{
	
	public static double parseAzimuth(String s){
		int counter=0;
		double azimuth=0;
		double degrees=0;
	 	double minutes=0;
	 	double seconds=0;
	 	int hyphenCounter=0;
	 	String temp="";

		//System.out.println(s);
		while(counter<s.length()){
			while(counter<s.length()-1 && s.charAt(counter)!=45){
				temp += s.substring(counter,++counter);	
			}			
			counter++;
			//System.out.println("temp:" + temp);
			switch (hyphenCounter){
			case 0:				
				degrees = Double.parseDouble(temp);
				//System.out.println("DEG:" + degrees);
				break;		
			case 1:
				minutes = Double.parseDouble(temp);
				minutes /= 60; 
				//System.out.println("MIN:" + minutes);
				break;
			case 2:
				seconds = Double.parseDouble(temp);
				seconds /= 3600;
				//System.out.println("SEC:" + seconds);
				break;
			}
			hyphenCounter++;
			temp="";	
		}		
		azimuth = degrees + minutes + seconds;
		//System.out.println("returned azimuth:"+azimuth);
		return azimuth;
	}
}
