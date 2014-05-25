import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import Database.Product;

/**
 * 
 */
public class TestProduct {

	private Product product;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		product = new Product();
	}

	/**
	 * Test method for {@link Database.Product#getName()}.
	 */
	@Test
	public void testGetName() {
		assertNotEquals(null, product);
		product = new Product("test", 1, 2);
		assertEquals("test", product.getName());
		assertEquals(1, product.getManufacturerID());
		assertEquals(2, product.getPrice());

		product = new Product(1, "test2", 2, 3);
		assertEquals(1, product.getId());
		assertEquals("test2", product.getName());
		assertEquals(2, product.getManufacturerID());
		assertEquals(3, product.getPrice());

		product.setName("test3");
		product.setManufacturerID(3);
		product.setPrice(4);
		assertEquals("test3", product.getName());
		assertEquals(3, product.getManufacturerID());
		assertEquals(4, product.getPrice());

	}

}
