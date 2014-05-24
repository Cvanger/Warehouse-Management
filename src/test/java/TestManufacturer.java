import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Database.Manufacturer;

/**
 * 
 */

/**
 * @author cvanger
 *
 */
public class TestManufacturer {

	private Manufacturer manufacturer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		manufacturer = new Manufacturer();
	}

	/**
	 * Test method for {@link Database.Manufacturer#Manufacturer()}.
	 */
	@Test
	public void testManufacturerAll() {
		assertNotEquals(null, manufacturer);
		
		manufacturer = new Manufacturer(1, "test");
		assertEquals(1, manufacturer.getId());
		assertEquals("test", manufacturer.getName());
		
		manufacturer.setName("test2");
		assertEquals("test2", manufacturer.getName());
	}

}
