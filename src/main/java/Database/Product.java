package Database;

/**
 * Class representing a product which can be stored a {@link Warehouse}.
 */
public class Product {

	/**
	 * Id of the product.
	 */
	private int id;

	/**
	 * Name of the product.
	 */
	private String name;

	/**
	 * The {@code id} of the product's {@link Manufacturer}.
	 */
	private int manufacturerID;

	/**
	 * Price of the product.
	 */
	private int price;

	/**
	 * Returns the name of this product.
	 * 
	 * @return the name of this product.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this product.
	 * 
	 * @param name the name of this product
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the {@code id} of this product's {@link Manufacturer}.
	 * 
	 * @return the id of this product's manufacturer
	 */
	public int getManufacturerID() {
		return manufacturerID;
	}

	/**
	 * Sets the {@code id} of this product's manufacturer.
	 * 
	 * @param manufacturerID the {@code id} to set as this product's {@link Manufacturer}
	 */
	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	/**
	 * Returns the price of this product.
	 * 
	 * @return the price of this product
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets the price of this product.
	 * 
	 * @param price the price of this product
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Returns the id of this product.
	 * 
	 * @return the id of this product
	 */
	public int getId() {
		return id;
	}

	/**
	 * Default constructor for creating a {@link Product} class.
	 */
	public Product() {
	}

	/**
	 * Constructor for creating a {@link Product} class.
	 * 
	 * @param name the name of the product
	 * @param manufacturerID the id of the product's manufacturer
	 * @param price the price of the product
	 */
	public Product(String name, int manufacturerID, int price) {
		this.name = name;
		this.manufacturerID = manufacturerID;
		this.price = price;
	}
	
	/**
	 * Constructor for creating a {@link Product} class.
	 * 
	 * @param id the id of the product
	 * @param name the name of the product
	 * @param manufacturerID the id of the product's manufacturer
	 * @param price the price of the product
	 */
	public Product(int id, String name, int manufacturerID, int price) {
		this.id = id;
		this.name = name;
		this.manufacturerID = manufacturerID;
		this.price = price;
	}
}