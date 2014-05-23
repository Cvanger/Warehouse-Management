package Database;

/**
 * Class representing a Warehouse which can hold products.
 */
public class Warehouse {

	/**
	 * Id of the warehouse.
	 */
	private int id;

	/**
	 * Name of the warehouse.
	 */
	private String name;

	/**
	 * Returns the name of this {@link Warehouse}.
	 * 
	 * @return name the name of this {@link Warehouse}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this {@link Warehouse}.
	 * 
	 * @param name the name of this {@link Warehouse}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the id of this {@link Warehouse}.
	 * 
	 * @return id the id of this {@link Warehouse}
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Sets the id of this {@link Warehouse}.
	 * 
	 * @param id the id of this {@link Warehouse}
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Default constructor for creating a {@link Warehouse} class.
	 */
	public Warehouse() {
	}

	/**
	 * Constructor for creating a {@link Warehouse} class.
	 * 
	 * @param name the name of the warehouse
	 */
	public Warehouse(String name) {
		this.name = name;
	}

	/**
	 * Constructor for creating a {@link Warehouse} class.
	 * 
	 * @param id the id of the warehouse
	 * @param name the name of the warehouse
	 */
	public Warehouse(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Returns a hash code from this {@link Warehouse}. 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Returns {@code true} if this {@link Warehouse} is equals with the {@code Object} received, otherwise returns {@code false}.
	 * 
	 * @param obj {@link Object} to check
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Warehouse))
			return false;
		Warehouse other = (Warehouse) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}