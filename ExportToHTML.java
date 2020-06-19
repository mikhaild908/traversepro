public class ExportToHTML{
	public static String createHTML(String s){
		String returnThis;
		String temp="";
		int counter=0;
		int inputCounter=0;		
		
		returnThis = "<html><head><title>TraversePro 2004</title></head><body>TraversePro 2004 <br><br><table BORDER=\"1\" WIDTH=\"100%\">";
		returnThis += "<tr><td ALIGN=\"CENTER\">Init Northing</td><td ALIGN=\"CENTER\">Init Easting</td><td ALIGN=\"CENTER\">Length</td><td ALIGN=\"CENTER\">En</td><td ALIGN=\"CENTER\">Ee</td><td ALIGN=\"CENTER\">LEC</td><td ALIGN=\"CENTER\">RE</td></tr><tr>";

		for (int i=0;i<7;i++){	
			temp +="<td ALIGN=\"CENTER\">";
			while(s.charAt(counter)!=44 && counter<s.length()-1){								
				temp +=  s.substring(counter,++counter);				
			}
			temp +="</td>";
			counter+=1;
		}
		returnThis += temp + "</tr></table>";
		temp = "";

		returnThis +="<br><table BORDER=\"1\" WIDTH=\"100%\"><table BORDER=\"1\" WIDTH=\"100%\"><tr><td ALIGN=\"CENTER\">Sta Occ</td><td ALIGN=\"CENTER\">Sta Obs</td><td ALIGN=\"CENTER\">Distance</td><td ALIGN=\"CENTER\">Azimuth</td><td ALIGN=\"CENTER\">Latitude</td><td ALIGN=\"CENTER\">Departure</td><td ALIGN=\"CENTER\">Northing</td><td ALIGN=\"CENTER\">Easting</td></tr>";		
		
		while(counter<s.length()){			
			if (inputCounter==0){
				temp += "<tr>";
			}
			temp +="<td ALIGN=\"RIGHT\">";
			while(s.charAt(counter)!=44 && counter<s.length()-1){				
				temp +=  s.substring(counter,++counter);				
			}
			temp +="</td>";
			if (inputCounter==7){
				temp += "</tr>";
				inputCounter=-1;
			}
			inputCounter++;						
			counter++;
		}
		returnThis +=temp;		
		returnThis += "</table></body></html>";		
		return returnThis;
	}
}
