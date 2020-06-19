public class ComputeDepLat{
		
	public static double computeDep(double azimuth,double distance){
		return ((Math.sin(azimuth*(Math.PI/180)))*-1*distance);
	}
	
	public static double computeLat(double azimuth,double distance){
		return ((Math.cos(azimuth*(Math.PI/180)))*-1*distance);
	}

	//double dep=(Math.sin(azimuth*Math.PI/180)*-1*distance);
	//double lat=(Math.cos(azimuth*Math.PI/180)*-1*distance);

	//System.out.println("Dep: " + dep);
	//System.out.println("Lat: " + lat);	
}
