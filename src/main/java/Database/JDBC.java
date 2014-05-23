package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBC {

	private Connection conn = null;

	public JDBC() {
		/*try {
			// log
			//DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			System.out.println("register driver");
			// log
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			// log
		}*/
	}

	public void open() {
		try {
			System.out.println("kapcsolat nyitás...");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
					"h_D8J55L", "ScKr64MaLa");
			// log

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		System.out.println("kapcsolat zárás...");
		try {
			conn.close();
		} catch (SQLException e) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sum;
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

			PreparedStatement pst4 = conn
					.prepareStatement("insert into CONTAIN(PRODUCT_ID, WAREHOUSE_ID, PIECE) "
							+ "values((select ID FROM PRODUCT where NAME = ? and PRICE = ? and MANUFACTURER_ID = ?),"
							+ " ?,?)");
			pst4.setString(1, product.getName());
			pst4.setInt(2, product.getPrice());
			pst4.setInt(3, product.getManufacturerID());
			pst4.setInt(4, warehouse.getId());
			pst4.setInt(5, piece);
			pst4.executeUpdate();

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

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contains;
	}

	public void addWarehouse(Warehouse warehouse) {

		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into WAREHOUSE values(warehouse_id.NEXTVAL, ?)");
			pst.setString(1, warehouse.getName());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addManufacturer(String manufacturer) {

		try {
			PreparedStatement pst = conn
					.prepareStatement("insert into MANUFACTURER values(manufacturer_id.NEXTVAL, ?)");
			pst.setString(1, manufacturer);
			pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return manufacturers;
	}

	public Manufacturer getManufacturerNameById(int man_id) {

		Manufacturer manufacturer = new Manufacturer();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id, name from MANUFACTURER where ID = ?");
			pst.setInt(1, man_id);
			ResultSet rs = pst.executeQuery();

			rs.next();
			manufacturer = new Manufacturer(rs.getInt("id"),
					rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return manufacturer;
	}

	public Warehouse getWarehouseNameById(int war_id) {

		Warehouse warehouse = new Warehouse();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select name from WAREHOUSE where ID = ?");
			pst.setInt(1, war_id);

			ResultSet rs = pst.executeQuery();
			rs.next();

			warehouse = new Warehouse(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warehouse;
	}

	public Product getProductByName(String productName) {

		Product product = new Product();
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id, name, manufacturer_id, price from PRODUCT where NAME = ?");
			pst.setString(1, productName);

			ResultSet rs = pst.executeQuery();
			rs.next();
			product = new Product(rs.getInt("id"), rs.getString("name"),
					rs.getInt("manufacturer_id"), rs.getInt("price"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
	}

	public int getManufacturerIdByName(String name) {

		int id = 0;
		try {
			PreparedStatement pst = conn
					.prepareStatement("select id from MANUFACTURER where name = ?");
			pst.setString(1, name);

			ResultSet rs = pst.executeQuery();
			rs.next();
			id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public int getPieceFromWarehouseWithProduct(Warehouse warehouse,
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return piece;
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warehouse;
	}

	public void deleteContain(Warehouse warehouse, Product product) {
		try {
			PreparedStatement pst = conn
					.prepareStatement("delete from CONTAIN where warehouse_id = ? and product_id = ?");
			pst.setInt(1, warehouse.getId());
			pst.setInt(2, product.getId());
			pst.executeUpdate();
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void moveProduct(Product product, Warehouse from, Warehouse to,
			int piece) {
		int pieceFrom = getPieceFromWarehouseWithProduct(from, product);
		System.out.println("from: " + pieceFrom);
		int pieceTo = getPieceFromWarehouseWithProduct(to, product);
		System.out.println("to: " + pieceTo);

		if (piece == pieceFrom) {
			System.out.println("az eredeti helyrol elfogyott");
			deleteContain(from, product);
			if (pieceTo > 0) {
				System.out.println("az uj helyen volt már");
				changeProductPieceInWarehouse(to, product, piece);
			} else {
				System.out.println("az uj helyen nem volt még");
				addContain(to, product, piece);
			}

		} else {
			System.out.println("maradt még az eredeti helyen is");
			changeProductPieceInWarehouse(from, product, -piece);
			if (pieceTo > 0) {
				System.out.println("az uj helyen volt már");
				changeProductPieceInWarehouse(to, product, piece);
			} else {
				System.out.println("az uj helyen nem volt még");
				addContain(to, product, piece);
			}
		}
	}
}