package UI;

import Database.JDBC;

public class proba {

	public static void main(String[] args) {

		JDBC jdbc = new JDBC();
		jdbc.open();
		
		jdbc.addlognlat();
		
		jdbc.close();

	}

}
