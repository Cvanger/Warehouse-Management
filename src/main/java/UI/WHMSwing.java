package UI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Database.Contain;
import Database.JDBC;
import Database.Manufacturer;
import Database.Product;
import Database.Warehouse;
import Service.Service;

public class WHMSwing extends JFrame {

	private JPanel contentPane = new JPanel();

	private JPanel resourcePanel = new JPanel();
	private JComboBox resourcesWarehousesComboBox = new JComboBox();
	private JLabel resourcesChooseWarehouseLbl = new JLabel("Choose Warehouse:");
	private JLabel resourceSumPriceLbl = new JLabel("");
	private JTable resourceTable = new JTable();
	private JScrollPane resourcesScrollPane = new JScrollPane();

	private JPanel addPanel = new JPanel();
	private JPanel addProductPanel = new JPanel();
	private JPanel addWarehousePanel = new JPanel();
	private JPanel addManufacturerPanel = new JPanel();
	private JPanel searchPanel = new JPanel();
	private JPanel movePanel = new JPanel();

	private JLabel addPlblName = new JLabel("Name:");
	private JLabel addPlblPrice = new JLabel("Price:");
	private JLabel addPlblManufacturer = new JLabel("Manufacturer:");
	private JLabel addPlblWarehouse = new JLabel("Warehouse:");
	private JLabel addPlblPiece = new JLabel("Piece:");
	private JLabel addPlblProduct = new JLabel("Add");
	private JTextPane addPtextName = new JTextPane();
	private JTextPane addPtextPrice = new JTextPane();
	private JTextPane addPtextPiece = new JTextPane();
	private JComboBox comboBoxAddPWarehouses = new JComboBox();
	private JComboBox comboBoxAddPManufacturers = new JComboBox();
	private JButton addPbtn = new JButton("Add Product");

	private JLabel addWlblWarehouse = new JLabel("Add Warehouse");
	private JLabel addWlblName = new JLabel("Name:");
	private JTextPane addWtextName = new JTextPane();
	private JLabel addWLongName = new JLabel("Logitude:");
	private JTextPane addWLong = new JTextPane();
	private JLabel addWLatName = new JLabel("Latitude:");
	private JTextPane addWLat = new JTextPane();
	private JButton addWbtn = new JButton("Add Warehouse");

	private JLabel addMlblManufacturer = new JLabel("Add Manufacturer");
	private JLabel addMlblName = new JLabel("Name:");
	private JTextPane addMtextName = new JTextPane();
	private JButton addMbtn = new JButton("Add Manufacturer");

	private JLabel moveLblFrom = new JLabel("From");
	private JLabel moveLblTo = new JLabel("To");
	private JComboBox moveFromComboBox = new JComboBox();
	private JComboBox moveToComboBox = new JComboBox();
	private JTable moveTableFrom = new JTable();
	private JTable moveTableTo = new JTable();
	private JScrollPane moveScrollPanelFrom = new JScrollPane();
	private JScrollPane moveScrollPanelTo = new JScrollPane();

	private JLabel searchLbl = new JLabel("Search a Product");
	private JLabel searchNameLbl = new JLabel("Name:");
	private JLabel searchManufacturerLbl = new JLabel("Manufacturer:");
	private JTextPane searchNameText = new JTextPane();
	private JTextPane searchManufacturerText = new JTextPane();
	private JButton searchBtn = new JButton("Search");
	private JTable searchTable = new JTable();
	private JScrollPane searchScrollPane = new JScrollPane();
	private JCheckBox searchCheckBox = new JCheckBox("Order by distance", true);
	private JComboBox searchWhereAreYou = new JComboBox();
	private JLabel searchWhereAreYouLbl = new JLabel("Where are you?");
	private boolean rightFrom = true;
	private boolean rightTo = true;

	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem mntmAddProduct = new JMenuItem("Add");
	private JMenuItem mntmClose = new JMenuItem("Close");
	private JMenuItem mntmMove = new JMenuItem("Move");
	private JMenuItem mntmResources = new JMenuItem("Resources");
	private JMenuItem mntmHelp = new JMenuItem("Help");

	private Logger logger = LoggerFactory.getLogger(WHMSwing.class);
	private JDBC jdbc = new JDBC();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				WHMSwing frame = new WHMSwing();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WHMSwing() {
		setTitle("Warehouse Management");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				jdbc.open();

				showResourcesPanel();
				reloadResourcesTable();
				logger.info("The main window opened.");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				jdbc.close();
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);

		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnFile.add(mntmAddProduct);

		mntmResources.setMnemonic(KeyEvent.VK_R);
		mntmResources.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		mntmResources.setToolTipText("List resources order by warehouse");
		mntmResources.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Switch to resources panel.");
				showResourcesPanel();
				reloadResourcesTable();
			}
		});

		JMenuItem mntmSearch = new JMenuItem("Search");

		mntmSearch.setMnemonic(KeyEvent.VK_S);
		mntmSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		mntmSearch.setToolTipText("Search for a product in warehouses");
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Switch to search panel.");
				showSearchPanel();
			}
		});
		mnFile.add(mntmSearch);
		mnFile.add(mntmResources);

		mntmMove.setMnemonic(KeyEvent.VK_M);
		mntmMove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		mntmMove.setToolTipText("Move product from one warehouse to another");
		mntmMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Switch to move panel.");
				showMovePanel();
			}
		});
		mnFile.add(mntmMove);
		mnFile.add(mntmClose);

		mntmClose.setMnemonic(KeyEvent.VK_Q);
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		mntmClose.setToolTipText("Exit application");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				logger.info("Closing the application.");
				jdbc.close();
				System.exit(0);
			}
		});

		mntmAddProduct.setMnemonic(KeyEvent.VK_D);
		mntmAddProduct.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.CTRL_MASK));
		mntmAddProduct.setToolTipText("Add product, warehouse, manufacturer");
		mntmAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Switch to add panel.");
				showAddPanel();
			}
		});
		menuBar.add(mntmHelp);

		mntmHelp.setMnemonic(KeyEvent.VK_H);
		mntmHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		mntmHelp.setToolTipText("Help");
		mntmHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(resourcePanel);
		contentPane.add(addPanel);
		contentPane.add(movePanel);
		contentPane.add(searchPanel);

		resourcePanel.setBounds(10, 10, 780, 530);
		resourcePanel.setLayout(null);

		resourcesWarehousesComboBox.setBounds(183, 19, 300, 24);
		resourcePanel.add(resourcesWarehousesComboBox);

		resourcesWarehousesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Reload the resources table.");
				reloadResourcesTable();
			}
		});

		resourceSumPriceLbl.setBounds(12, 62, 400, 25);
		resourcePanel.add(resourceSumPriceLbl);

		addPanel.add(addProductPanel);
		addPanel.add(addWarehousePanel);
		addPanel.add(addManufacturerPanel);
		addPanel.setLayout(null);
		addProductPanel.setLayout(null);
		addWarehousePanel.setLayout(null);
		addManufacturerPanel.setLayout(null);

		addPanel.setBounds(0, 0, 800, 600);

		addProductPanel.setBounds(10, 10, 380, 500);
		addProductPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));

		addPlblProduct.setFont(new Font("Dialog", Font.BOLD, 16));

		addPlblProduct.setBounds(120, 10, 150, 30);
		addProductPanel.add(addPlblProduct);

		addPlblName.setBounds(30, 60, 50, 20);
		addProductPanel.add(addPlblName);
		addPtextName.setBounds(150, 60, 200, 20);
		addProductPanel.add(addPtextName);

		addPlblPrice.setBounds(30, 100, 50, 20);
		addProductPanel.add(addPlblPrice);
		addPtextPrice.setBounds(150, 100, 200, 20);
		addProductPanel.add(addPtextPrice);

		addPlblManufacturer.setBounds(30, 140, 110, 20);
		addProductPanel.add(addPlblManufacturer);
		comboBoxAddPManufacturers.setBounds(150, 140, 200, 20);
		addProductPanel.add(comboBoxAddPManufacturers);

		addPlblWarehouse.setBounds(30, 180, 110, 20);
		addProductPanel.add(addPlblWarehouse);
		comboBoxAddPWarehouses.setBounds(150, 180, 200, 20);
		addProductPanel.add(comboBoxAddPWarehouses);

		addPlblPiece.setBounds(30, 220, 110, 20);
		addProductPanel.add(addPlblPiece);
		addPtextPiece.setBounds(150, 220, 200, 20);
		addProductPanel.add(addPtextPiece);

		addPbtn.setBounds(150, 270, 200, 30);
		addProductPanel.add(addPbtn);
		addPbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Add a new product.");
				addProduct();
			}
		});

		addWarehousePanel.setBounds(405, 10, 380, 225);
		addWarehousePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null));
		addWlblWarehouse.setFont(new Font("Dialog", Font.BOLD, 16));

		addWlblWarehouse.setBounds(120, 10, 150, 30);
		addWarehousePanel.add(addWlblWarehouse);

		addWlblName.setBounds(30, 60, 50, 20);
		addWarehousePanel.add(addWlblName);

		addWLongName.setBounds(30, 90, 100, 25);
		addWarehousePanel.add(addWLongName);

		addWLatName.setBounds(30, 120, 100, 25);
		addWarehousePanel.add(addWLatName);

		addWLong.setBounds(120, 90, 200, 20);
		addWarehousePanel.add(addWLong);

		addWLat.setBounds(120, 120, 200, 20);
		addWarehousePanel.add(addWLat);

		addWtextName.setBounds(120, 60, 200, 20);
		addWarehousePanel.add(addWtextName);

		addWbtn.setBounds(120, 150, 200, 30);
		addWarehousePanel.add(addWbtn);
		addWbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Add a new warehouse.");
				addWarehouse();
			}
		});

		addManufacturerPanel.setBounds(405, 250, 380, 260);
		addManufacturerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null));
		addMlblManufacturer.setFont(new Font("Dialog", Font.BOLD, 16));

		addMlblManufacturer.setBounds(120, 10, 180, 30);
		addManufacturerPanel.add(addMlblManufacturer);

		addMlblName.setBounds(30, 60, 50, 20);
		addManufacturerPanel.add(addMlblName);

		addMtextName.setBounds(120, 60, 200, 20);
		addManufacturerPanel.add(addMtextName);

		addMbtn.setBounds(120, 110, 200, 30);
		addManufacturerPanel.add(addMbtn);
		addMbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Add a new manufacturer.");
				addManufacturer();
			}
		});

		movePanel.setBounds(10, 10, 780, 535);
		movePanel.setLayout(null);

		moveLblFrom.setBounds(20, 20, 200, 25);
		moveLblFrom.setFont(new Font("Dialog", Font.BOLD, 20));
		movePanel.add(moveLblFrom);

		moveLblTo.setBounds(520, 20, 200, 25);
		moveLblTo.setFont(new Font("Dialog", Font.BOLD, 20));
		movePanel.add(moveLblTo);

		moveFromComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rightFrom)
					reloadMoveFromTable();
			}
		});

		moveFromComboBox.setBounds(20, 60, 200, 25);
		movePanel.add(moveFromComboBox);

		moveToComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rightTo)
					reloadMoveToTable();
			}
		});

		moveToComboBox.setBounds(520, 60, 200, 25);
		movePanel.add(moveToComboBox);

		searchPanel.setBounds(10, 10, 780, 520);
		searchPanel
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		searchPanel.setLayout(null);

		searchLbl.setBounds(120, 20, 200, 25);
		searchLbl.setFont(new Font("Dialog", Font.BOLD, 16));
		searchPanel.add(searchLbl);

		searchNameLbl.setBounds(20, 60, 120, 25);
		searchPanel.add(searchNameLbl);
		searchNameText.setBounds(150, 60, 200, 25);
		searchPanel.add(searchNameText);

		searchManufacturerLbl.setBounds(20, 100, 120, 25);
		searchPanel.add(searchManufacturerLbl);
		searchManufacturerText.setBounds(150, 100, 200, 25);
		searchPanel.add(searchManufacturerText);
		
		searchWhereAreYou.setBounds(150, 130, 120, 25);
		searchPanel.add(searchWhereAreYou);
		searchWhereAreYouLbl.setBounds(20, 130, 200, 25);
		searchPanel.add(searchWhereAreYouLbl);

		searchCheckBox.setBounds(400, 100, 250, 20);
		searchPanel.add(searchCheckBox);
		searchCheckBox.setEnabled(true);

		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Search for a product.");
				searchProduct();
			}
		});

		searchBtn.setBounds(400, 50, 200, 25);
		searchPanel.add(searchBtn);
	}

	/**
	 * Shows the add panel, where you can add products, warehouses or
	 * manufacturers to the database.
	 */
	private void showAddPanel() {
		resourcePanel.setVisible(false);
		movePanel.setVisible(false);
		searchPanel.setVisible(false);
		addPanel.setVisible(true);

		comboBoxAddPWarehouses.removeAllItems();
		comboBoxAddPManufacturers.removeAllItems();

		List<Manufacturer> manufacturers = jdbc.getManufacturers();
		for (Manufacturer w : manufacturers)
			comboBoxAddPManufacturers.addItem(w.getName());

		List<Warehouse> warehouses = jdbc.getWarehouses();

		for (Warehouse w : warehouses)
			comboBoxAddPWarehouses.addItem(w.getName());
	}

	/**
	 * Adds a product to the database.
	 */
	private void addProduct() {

		jdbc.AddProductToWarehouse(
				new Product(addPtextName.getText(), jdbc.getManufacturerByName(
						(String) comboBoxAddPManufacturers.getSelectedItem())
						.getId(), Integer.parseInt((addPtextPrice.getText()))),
				jdbc.getWarehouseByName((String) comboBoxAddPWarehouses
						.getSelectedItem()), Integer.parseInt(addPtextPiece
						.getText()));

		showAddPanel();
		addPtextName.setText("");
		addPtextPrice.setText("");
		addPtextPiece.setText("");
	}

	/**
	 * Adds a manufacturer to the database.
	 */
	private void addManufacturer() {
		jdbc.addManufacturer(new Manufacturer(2, addMtextName.getText()));
		addMtextName.setText("");
		showAddPanel();
	}

	/**
	 * Adds a warehouse to the database.
	 */
	private void addWarehouse() {
		jdbc.addWarehouse(new Warehouse((String) addWtextName.getText(), Double
				.parseDouble(addWLong.getText()), Double.parseDouble(addWLat
				.getText())));
		addWtextName.setText("");
		showAddPanel();
	}

	/**
	 * Shows the search panel, where you can search for a product using it's
	 * name.
	 */
	private void showSearchPanel() {
		addPanel.setVisible(false);
		resourcePanel.setVisible(false);
		movePanel.setVisible(false);
		searchPanel.setVisible(true);
		
		List<Warehouse> warehouse = jdbc.getWarehouses();
		for( Warehouse w : warehouse)
			searchWhereAreYou.addItem(w.getName());
	}

	/**
	 * Searches for a product specified by it's name.
	 */
	private void searchProduct() {
		List<Contain> results = new ArrayList<Contain>();
		if (!searchNameText.getText().equalsIgnoreCase("")) {
			if (!searchManufacturerText.getText().equalsIgnoreCase(""))
				results = jdbc.getContainsFromSearch(
						jdbc.getManufacturerByName(searchManufacturerText
								.getText()), jdbc
								.getProductByName(searchNameText.getText()));
			else
				results = jdbc.getContainsFromProductSearch(jdbc
						.getProductByName(searchNameText.getText()));

		} else if (!searchManufacturerText.getText().equalsIgnoreCase(""))
			results = jdbc.getContainsFromManufacturerSearch(jdbc
					.getManufacturerByName(searchManufacturerText.getText()));

		Object[][] rowData = new Object[results.size()][6];
		Object[] columnNames = new String[] { "Warehouse Name", "Name",
				"Manufacturer", "Price", "Piece", "Distance" };

		for (int i = 0; i < results.size(); ++i) {
			Product p = jdbc.getProductById(results.get(i).getProductId());
			Warehouse warehouse = jdbc.getWarehouseById(results.get(i)
					.getWarehouseId());
			rowData[i][0] = warehouse.getName();
			// System.out.println(warehouse.getLongitude());
			rowData[i][1] = p.getName();
			rowData[i][2] = jdbc.getManufacturerById(p.getManufacturerID())
					.getName();
			rowData[i][3] = p.getPrice();
			rowData[i][4] = results.get(i).getPiece();
			rowData[i][5] = Service.Distance(jdbc.getWarehouseByName((String)searchWhereAreYou.getSelectedItem()),
					warehouse);

		}

		searchTable = new JTable(rowData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		searchPanel.remove(searchScrollPane);
		searchScrollPane = new JScrollPane(searchTable);
		searchScrollPane.setBounds(10, 150, 760, results.size() * 17 + 18);
		searchPanel.add(searchScrollPane);

		revalidate();
		repaint();
	}

	/**
	 * Shows move panel, where you can move any piece of the products from a
	 * specified warehouse to another.
	 */
	private void showMovePanel() {
		resourcePanel.setVisible(false);
		addPanel.setVisible(false);
		searchPanel.setVisible(false);
		movePanel.setVisible(true);

		moveFromComboBox.removeAllItems();
		moveToComboBox.removeAllItems();

		List<Warehouse> warehouses = jdbc.getWarehouses();

		rightFrom = false;
		for (Warehouse w : warehouses)
			moveFromComboBox.addItem(w.getName());
		rightFrom = true;

		reloadMoveFromTable();
	}

	/**
	 * Moves a product.
	 * 
	 * @param target
	 *            target from the event called
	 */
	private void moveProduct(JTable target) {
		String text = JOptionPane
				.showInputDialog("How many products do you want to transfer?");
		int db;

		if (text != null) {
			db = Integer.parseInt(text);

			if (0 < db
					&& db <= Integer.parseInt(moveTableFrom.getModel()
							.getValueAt(target.getSelectedRow(), 3).toString())) {

				jdbc.moveProduct(jdbc.getProductByName(moveTableFrom.getModel()
						.getValueAt(target.getSelectedRow(), 0).toString()),
						jdbc.getWarehouseByName(moveFromComboBox
								.getSelectedItem().toString()), jdbc
								.getWarehouseByName(moveToComboBox
										.getSelectedItem().toString()), db);

				reloadMoveFromTable();
			}

			else if (db > Integer.parseInt(moveTableFrom.getModel()
					.getValueAt(target.getSelectedRow(), 3).toString()))
				JOptionPane.showMessageDialog(null, "Too high value!");

			else if (db < 1)
				JOptionPane.showMessageDialog(null, "Too low value!");
		}
	}

	private void reloadMoveFromTable() {

		logger.info("reload the movefrom table");
		List<Product> products = jdbc
				.getProductsFromWarehouse((String) moveFromComboBox
						.getSelectedItem());

		Object[][] rowData = new Object[products.size()][4];
		Object[] columnNames = new String[] { "Name", "Manufacturer", "Price",
				"Piece" };

		for (int i = 0; i < products.size(); ++i) {
			rowData[i][0] = products.get(i).getName();
			rowData[i][1] = jdbc.getManufacturerById(
					products.get(i).getManufacturerID()).getName();
			rowData[i][2] = products.get(i).getPrice();
			rowData[i][3] = jdbc.getPieceFromWarehouseWithProduct(jdbc
					.getWarehouseByName((String) moveFromComboBox
							.getSelectedItem()), products.get(i));
		}

		moveTableFrom = new JTable(rowData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		moveTableFrom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					moveProduct((JTable) e.getSource());
				}
			}
		});

		moveTableFrom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'm')
					moveProduct((JTable) e.getSource());
			}
		});

		movePanel.remove(moveScrollPanelFrom);
		moveScrollPanelFrom = new JScrollPane(moveTableFrom);
		moveScrollPanelFrom.setBounds(12, 92, 350, 430);
		movePanel.add(moveScrollPanelFrom);

		rightTo = false;
		moveToComboBox.removeAllItems();
		List<Warehouse> warehouses = jdbc.getWarehouses();
		for (Warehouse w : warehouses)
			if (!w.equals(new Warehouse((String) moveFromComboBox
					.getSelectedItem())))
				moveToComboBox.addItem(w.getName());

		rightTo = true;
		reloadMoveToTable();
	}

	private void reloadMoveToTable() {

		logger.info("reload the moveto table");
		List<Product> products = jdbc
				.getProductsFromWarehouse((String) moveToComboBox
						.getSelectedItem());

		Object[][] rowData = new Object[products.size()][4];
		Object[] columnNames = new String[] { "Name", "Manufacturer", "Price",
				"Piece" };

		rowData = new Object[products.size()][4];

		for (int i = 0; i < products.size(); ++i) {
			rowData[i][0] = products.get(i).getName();
			rowData[i][1] = jdbc.getManufacturerById(
					products.get(i).getManufacturerID()).getName();
			rowData[i][2] = products.get(i).getPrice();
			rowData[i][3] = jdbc.getPieceFromWarehouseWithProduct(jdbc
					.getWarehouseByName((String) moveToComboBox
							.getSelectedItem()), products.get(i));
		}

		moveTableTo = new JTable(rowData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		movePanel.remove(moveScrollPanelTo);
		moveScrollPanelTo = new JScrollPane(moveTableTo);
		moveScrollPanelTo.setBounds(412, 92, 350, 430);
		movePanel.add(moveScrollPanelTo);
	}

	/**
	 * Shows the resources panel.
	 */
	private void showResourcesPanel() {
		addPanel.setVisible(false);
		movePanel.setVisible(false);
		searchPanel.setVisible(false);
		resourcePanel.setVisible(true);
		resourcesWarehousesComboBox.removeAllItems();

		List<Warehouse> warehouses = jdbc.getWarehouses();

		for (Warehouse w : warehouses)
			resourcesWarehousesComboBox.addItem(w.getName());
	}

	/**
	 * Reloads the resources table.
	 */
	private void reloadResourcesTable() {

		if (resourcesWarehousesComboBox.getItemCount() != 0)
			resourceSumPriceLbl
					.setText("The total value of the warehouse: "
							+ jdbc.getSumPriceFromWarehouse(jdbc
									.getWarehouseByName((String) resourcesWarehousesComboBox
											.getSelectedItem())));
		else
			resourceSumPriceLbl.setText("No product in the warehouse");

		List<Product> products = jdbc
				.getProductsFromWarehouse((String) resourcesWarehousesComboBox
						.getSelectedItem());

		Object[][] rowData = new Object[products.size()][4];
		Object[] columnNames = new String[] { "Name", "Manufacturer", "Price",
				"Piece" };

		for (int i = 0; i < products.size(); ++i) {
			rowData[i][0] = products.get(i).getName();
			rowData[i][1] = jdbc.getManufacturerById(
					products.get(i).getManufacturerID()).getName();
			rowData[i][2] = products.get(i).getPrice();
			rowData[i][3] = jdbc.getPieceFromWarehouseWithProduct(jdbc
					.getWarehouseByName((String) resourcesWarehousesComboBox
							.getSelectedItem()), products.get(i));
		}

		resourceTable = new JTable(rowData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		resourcePanel.remove(resourcesScrollPane);
		resourcesScrollPane = new JScrollPane(resourceTable);
		resourcesScrollPane.setBounds(12, 92, 750, 430);
		resourcePanel.add(resourcesScrollPane);

		revalidate();
		repaint();
	}
}