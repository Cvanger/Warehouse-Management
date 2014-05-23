package Database;

/**
 * Class representing a manufacturer.
 */
public class Manufacturer {

	/**
	 * Id of the manufacturer.
	 */
	private int id;
	
	/**
	 * Name of the manufacturer.
	 */
	private String name;
	
	/**
	 * Default constructor for creating a new {@link Manufacturer} object giving an id and a name.
	 */
	public Manufacturer() {
	}
	
	/**
	 * Constructor for creating a new {@link Manufacturer} object giving an id and a name.
	 * 
	 * @param id the id of the manufacturer
	 * @param name the name of the manufacturer
	 */
	public Manufacturer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Returns the name of this manufacturer.
	 * 
	 * @return the name the name of this manufacturer
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this manufacturer.
	 * 
	 * @param name the name of this manufacturer
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/**
	 * Returns the id of this manufacturer.
	 * 
	 * @return the id of this manufacturer
	 */
	public int getId() {
		return id;
	}
}