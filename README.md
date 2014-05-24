Warehouse-Management
====================

About
-----

The application can halp you to manage different kind of product in warehouses. You can define the quantity of these products contained in the warehouses.
Each products has a price. The total value of the produtc calculated automatically under the resources menu, where you can choose which warehouses do you want review.

Entities in the database
------------------------

 1. Product
	* Id
	* Name
	* Manufacturer id
 2. Manufacturer
	* Id
	* name
 3. Warehouse
	* Id
	* name
 4. Contain
	* Product id
	* Warehouse id
	* Piece
