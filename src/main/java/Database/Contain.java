package Database;

/**
 * Class representing relationship between Product and Warehouse. Additionally this class contains the
 * piece element which means how many products in the warehouse.
 */
public class Contain {

	/**
	 * Id of the relationship.
	 */
	private int productId;

	/**
	 * Warehouse id of the relationship.
	 */
	private int warehouseId;

	/**
	 * Product piece in the relationship.
	 */
	private int piece;

	/**
	 * Returns the {@link Product} id of the relationship.
	 * 
	 * @return the productId of the relationship
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Returns the {@link Warehouse} id of the relationship.
	 * 
	 * @return the warehouseId of the relationship
	 */
	public int getWarehouseId() {
		return warehouseId;
	}

	/**
	 * Returns the Product's piece of the relationship.
	 * 
	 * @return the piece products
	 */
	public int getPiece() {
		return piece;
	}

	/**
	 * Constructor for creating a {@link Contain} object.
	 * 
	 * @param productId the id of the {@link Product}
	 * @param warehouseId the id of the {@link Warehouse}
	 * @param piece the piece of the {@link Product} in the relationship
	 */
	public Contain(int productId, int warehouseId, int piece) {
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.piece = piece;
	}
}