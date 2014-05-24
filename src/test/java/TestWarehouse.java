import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Database.Product;
import Database.Warehouse;

/**
 * 
 */

public class TestWarehouse {


	private Warehouse warehouse;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		warehouse = new Warehouse();
	}

	/**
	 * Test method for {@link Database.Warehouse#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		assertNotEquals(null, warehouse);
		warehouse = new Warehouse("test");
		assertEquals("test", warehouse.getName());
		
		warehouse = new Warehouse(1, "test2");
		assertEquals(1, warehouse.getId());
		assertEquals("test2", warehouse.getName());
		
		warehouse.setName("test3");
		warehouse.setId(2);
		assertEquals("test3", warehouse.getName());
		assertEquals(2, warehouse.getId());
		
		warehouse.hashCode();
		Product p = null;
		assertEquals(true, warehouse.equals(warehouse));
		assertEquals(false, warehouse.equals(p));
		p = new Product();
		assertEquals(false, warehouse.equals(p));
		
		warehouse = new Warehouse();
		Warehouse w2 = new Warehouse("asd");
		assertEquals(false, warehouse.equals(w2));
		warehouse.setName("name");
		assertEquals(false, warehouse.equals(w2));
		
		w2.setName("name");
		assertEquals(true, warehouse.equals(w2));
		
	}

}
