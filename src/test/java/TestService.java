import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Database.JDBC;
import Database.Warehouse;

public class TestService {

	private Warehouse w1;
	private Warehouse w2;
	private JDBC jdbc = new JDBC();

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testDistance() {
		Warehouse w1 = new Warehouse("w1", 0.0, 0.0);
		Warehouse w2 = new Warehouse("w2", 3.0, 4.0);
		assertEquals(5.0, Service.Service.Distance(w1, w2), 0.0001);

		w2.setLongitude(12.0);
		w2.setLatitude(5.0);
		assertEquals(13.0, Service.Service.Distance(w1, w2), 0.0001);

		w2.setLongitude(24.0);
		w2.setLatitude(7.0);
		assertEquals(25.0, Service.Service.Distance(w1, w2), 0.0001);
		
		w2.setLongitude(15.0);
		w2.setLatitude(8.0);
		assertEquals(17.0, Service.Service.Distance(w1, w2), 0.0001);
		
		w2.setLongitude(40.0);
		w2.setLatitude(9.0);
		assertEquals(41.0, Service.Service.Distance(w1, w2), 0.0001);
			
	}
}
