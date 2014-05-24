import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Database.Contain;

/**
 * 
 */

/**
 * @author cvanger
 *
 */
public class TestContain {
	
	private Contain contain;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		contain = new Contain(1, 2, 3);
	}

	/**
	 * Test method for {@link Database.Contain#getProductId()}.
	 */
	@Test
	public void testContainAll() {
		assertEquals(1, contain.getProductId());
		assertEquals(2, contain.getWarehouseId());
		assertEquals(3, contain.getPiece());
	}

}
