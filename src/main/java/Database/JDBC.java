package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBC {

	private Connection conn = null;
	private Logger logger = LoggerFactory.getLogger(JDBC.class);

	public void open() {
		try {
			logger.info("try to open a connetcion with the server");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
					"h_D8J55L", "ScKr64MaLa");
			logger.info("connection opened succesfully");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("connection could be opened cause of:\n"
					+ e.getMessage());
		}
	}

	public void close() {
		logger.info("connection closing");
		try {
			conn.close();
		} catch (SQLException e) {
			logger.info("there is a problem while tried to close the connection");
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void addlognlat(){
		try {
			PreparedStatement pst = conn
					.prepareStatement("ALTER TABLE WAREHOUSE MODIFY LONGITUDE double");
			pst.executeQuery();
			pst.close();
			
			/*pst = conn
					.prepareStatement("ALTER TABLE WAREHOUSE ADD LATITUDE double");
			pst.executeQuery();*/

			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public Integer getSumPriceFromWarehouse(Warehouse warehouse) {

		int sum = 0;
		try {
			PreparedStatement pst = conn
					.prepareStatement("select sum(p.price * c.piece) as sum from product p inner join "
							+ "contain c on c.PRODUCT_ID=p.ID inner join "
							+ "warehouse w on c.WAREHOUSE_ID=w.ID where warehouse_id = ?");
			pst.setInt(1, warehouse.getId());

			ResultSet rs = pst.executeQuery();
			rs.next();
			sum = rs.getInt("sum");

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sum;
	}

	public List<Contain> getContainsFromProductSearch(Product product) {

		List<Contain> contains = new ArrayList<Contain>();
		try {
			PreparedStatement pst = conn
					.prepareStatement("SELECT PRODUCT_ID, WAREHOUSE_ID, PIECE FROM CONTAIN WHERE PRODUCT_ID = ?");
			pst.setInt(1, product.getId());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contains.add(new Contain(rs.getInt("PRODUCT_ID"), rs
						.getInt("WAREHOUSE_ID"), rs.getInt("PIECE")));
			}

			rs.close();
			pst.close();
		} catch (SQLException e) {
			logger.error("there was a problem in the getContainsFromProductSearch function");
			e.printStackTrace();
		}

		return contains;
	}

	public List<Contain> getContainsFromManufacturerSearch(
			Manufacturer manufacturer) {
		List<Contain> contains = new ArrayList<Contain>();
		try {
			PreparedStatement pst = conn
					.prepareStatement("SELECT c.PRODUCT_ID as p_id, c.WAREHOUSE_ID as w_id, c.PIECE as piece FROM CONTAIN c inner join PRODUCT p on c.PRODUCT_ID = p.ID WHERE p.manufacturer_id = ?");
			pst.setInt(1, manufacturer.getId());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contains.add(new Contain(rs.getInt("p_id"), rs.getInt("w_id"),
						rs.getInt("piece")));
			}

			rs.close();
			pst.close();
		} catch (SQLException e) {
			logger.error("there was a problem in the getContainsFromManufacturerSearch function");
			e.printStackTrace();
		}
		return contains;
	}

	public List<Contain> getContainsFromSearch(Manufacturer manufacturer,
			Product product) {
		List<Contain> contains = new ArrayList<Contain>();
		try {
			PreparedStatement pst = conn
					.prepareStatement("SELECT c.PRODUCT_ID as p_id, c.WAREHOUSE_ID as w_id, c.PIECE as piece FROM CONTAIN c inner join PRODUCT p on c.PRODUCT_ID = p.ID inner join MANUFACTURER m on p.MANUFACTURER_ID = m.ID WHERE p.ID = ? and m.NAME = ?");
			pst.setInt(1, product.getId());
			pst.setString(2, manufacturer.getName());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contains.add(new Contain(rs.getInt("p_id"), rs.getInt("w_id"),
						rs.getInt("piece")));
			}

			rs.close();
			pst.close();
		} catch (SQLException e) {
			logger.error("there was a problem in the getContainsFromManufacturerSearch function");
			e.printStackTrace();
		}
		return contains;
	}

	public List<Product> getProductsFromWarehouse(String warehouse) {

		List<Product> products = new ArrayList<Product>();
		try {
			PreparedStatement pst = conn
					.prepareStatement("SELECT p.ID as id, p.Name as name, p.price as price, p.manufacturer_id as man_id FROM Product p inner join Contain c on p.id=c.product_id "
							+ "inner join WAREHOUSE w on c.WAREHOUSE_ID = w.ID where w.Name = ?");
			pst.setString(1, warehouse);

			ResultSet rs = pst.executeQuery();

			while (rs.next())
				products.add(new Product(Integer.parseInt(rs.getString("id")),
						rs.getString("name"), rs.getInt("man_id"), rs
								.getInt("price")));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public List<Warehouse> getWarehouses() {

		List<Warehouse> warehouses = new ArrayList<Warehouse>();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement
					.executeQuery("select id, name from WAREHOUSE");

			if (rs != null)
				while (rs.next())
					warehouses.add(new Warehouse(rs.getInt("id"), rs
							.getString("name")));

			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warehouses;
	}

	public List<Manufacturer> getManufacturers() {

		List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement
					.executeQuery("select id, name from MANUFACTURER");

			while (rs.next())
				manufacturers.add(new Manufacturer(rs.getInt("id"), rs
						.getString("name")));

			statement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return manufacturers;
	}

	public Manufacturer getManufacturerById(int man_id) {

		Manufacturer manufacturer = new Manufacturer();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id, name from MANUFACTURER where ID = ?");
			pst.setInt(1, man_id);
			ResultSet rs = pst.executeQuery();

			rs.next();
			manufacturer = new Manufacturer(rs.getInt("id"),
					rs.getString("name"));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return manufacturer;
	}

	public Warehouse getWarehouseById(int war_id) {

		Warehouse warehouse = new Warehouse();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select name from WAREHOUSE where ID = ?");
			pst.setInt(1, war_id);

			ResultSet rs = pst.executeQuery();
			rs.next();
			warehouse = new Warehouse(rs.getString("name"));
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warehouse;
	}

	public Warehouse getWarehouseByName(String name) {

		Warehouse warehouse = new Warehouse();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select ID, NAME from WAREHOUSE where NAME = ?");
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();

			rs.next();
			warehouse = new Warehouse(rs.getInt("ID"), rs.getString("NAME"));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warehouse;
	}

	public Manufacturer getManufacturerByName(String name) {
		Manufacturer manufacturer = new Manufacturer();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id, name from MANUFACTURER where name = ?");
			pst.setString(1, name);

			ResultSet rs = pst.executeQuery();
			while (rs.next())
				manufacturer = new Manufacturer(rs.getInt("id"),
						rs.getString("name"));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return manufacturer;
	}

	public Product getProductByName(String productName) {
		Product product = new Product();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id, name, manufacturer_id, price from PRODUCT where NAME = ?");
			pst.setString(1, productName);

			ResultSet rs = pst.executeQuery();
			while (rs.next())
				product = new Product(rs.getInt("id"), rs.getString("name"),
						rs.getInt("manufacturer_id"), rs.getInt("price"));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public Product getProductById(int id) {
		Product product = new Product();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id, name, manufacturer_id, price from PRODUCT where ID = ?");
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			while (rs.next())
				product = new Product(rs.getInt("id"), rs.getString("name"),
						rs.getInt("manufacturer_id"), rs.getInt("price"));

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public Integer getPieceFromWarehouseWithProduct(Warehouse warehouse,
			Product product) {

		int piece = 0;

		try {
			PreparedStatement pst = conn
					.prepareStatement("select c.piece as piece from CONTAIN c "
							+ "inner join PRODUCT p on c.PRODUCT_ID = p.ID "
							+ "inner join WAREHOUSE w on c.WAREHOUSE_ID = w.ID where p.ID = ? and w.id = ?");
			pst.setInt(1, product.getId());
			pst.setInt(2, warehouse.getId());

			ResultSet rs = pst.executeQuery();
			if (rs.next())
				piece = rs.getInt("piece");
			else
				piece = 0;

			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return piece;
	}

	public void deleteContain(Warehouse warehouse, Product product) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from CONTAIN where warehouse_id = ? and product_id = ?");
			pst.setInt(1, warehouse.getId());
			pst.setInt(2, product.getId());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changeProductPieceInWarehouse(Warehouse warehouse,
			Product product, int piece) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("update contain set piece = "
							+ "(select piece from contain where warehouse_id = ? and product_id = ?) + ? "
							+ "where warehouse_id = ? and product_id = ?");
			pst.setInt(1, warehouse.getId());
			pst.setInt(2, product.getId());
			pst.setInt(3, piece);
			pst.setInt(4, warehouse.getId());
			pst.setInt(5, product.getId());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void moveProduct(Product product, Warehouse from, Warehouse to,
			int piece) {
		int pieceFrom = getPieceFromWarehouseWithProduct(from, product);
		int pieceTo = getPieceFromWarehouseWithProduct(to, product);

		if (piece == pieceFrom) {
			deleteContain(from, product);
			if (pieceTo > 0) {
				changeProductPieceInWarehouse(to, product, piece);
			} else {

				addContain(to, product, piece);
			}

		} else {
			changeProductPieceInWarehouse(from, product, -piece);
			if (pieceTo > 0) {
				changeProductPieceInWarehouse(to, product, piece);
			} else {
				addContain(to, product, piece);
			}
		}
	}

	public void AddProductToWarehouse(Product product, Warehouse warehouse,
			int piece) {

		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into Product(ID, NAME, PRICE, "
							+ "MANUFACTURER_ID) values (product_id.NEXTVAL, ?, ?, ?)");

			pst.setString(1, product.getName());
			pst.setInt(2, product.getPrice());
			pst.setInt(3, product.getManufacturerID());

			pst.executeUpdate();

			PreparedStatement pst2 = conn
					.prepareStatement("insert into CONTAIN(PRODUCT_ID, WAREHOUSE_ID, PIECE) "
							+ "values((select ID FROM PRODUCT where NAME = ? and PRICE = ? "
							+ "and MANUFACTURER_ID = ?), ?,?)");
			pst2.setString(1, product.getName());
			pst2.setInt(2, product.getPrice());
			pst2.setInt(3, product.getManufacturerID());
			pst2.setInt(4, warehouse.getId());
			pst2.setInt(5, piece);
			pst2.executeUpdate();

			pst.close();
			pst2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addContain(Warehouse warehouse, Product product, int piece) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into contain values(?, ?, ?)");
			pst.setInt(1, product.getId());
			pst.setInt(2, warehouse.getId());
			pst.setInt(3, piece);

			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addWarehouse(Warehouse warehouse) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into WAREHOUSE values(warehouse_id.NEXTVAL, ?)");
			pst.setString(1, warehouse.getName());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addManufacturer(Manufacturer manufacturer) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into MANUFACTURER values(manufacturer_id.NEXTVAL, ?)");
			pst.setString(1, manufacturer.getName());
			pst.executeQuery();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteProduct(String name) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from PRODUCT where name = ?");
			pst.setString(1, name);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteWarehouse(String name) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from WAREHOUSE where name = ?");
			pst.setString(1, name);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteManufacturer(String name) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from MANUFACTURER where name = ?");
			pst.setString(1, name);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteContain(String pname, String wname) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from MANUFACTURER where PRODUCT_ID = (select id from product where name = ?) and WAREHOUSE_ID = (select id from warehouse where name = ?)");
			pst.setString(1, pname);
			pst.setString(2, wname);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}