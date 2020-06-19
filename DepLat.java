//uses ComputeDepLat.class
public class DepLat{
	public static void main(String[] args){
		double azimuth = Double.parseDouble(args[0]);
		double distance = Double.parseDouble(args[1]);
		
		double dep=ComputeDepLat.computeDep(azimuth,distance);
		double lat=ComputeDepLat.computeLat(azimuth,distance);

		System.out.println("Dep: " + dep);
		System.out.println("Lat: " + lat);		
	}
}
