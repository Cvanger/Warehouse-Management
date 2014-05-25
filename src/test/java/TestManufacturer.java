import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import Database.Manufacturer;

/**
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
