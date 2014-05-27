package Service;

import Database.Warehouse;

public class Service {

	public static double Distance(Warehouse w1, Warehouse w2) {
		return Math.sqrt(Math.pow(w1.getLatitude() - w2.getLatitude(), 2)
				+ Math.pow(w1.getLongitude() - w2.getLongitude(), 2));
	}
}
