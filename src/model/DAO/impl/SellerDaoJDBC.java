package model.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

//Creating concrete Class SellerDaoJDBC to Implement the Interface
public class SellerDaoJDBC implements SellerDAO {

	// SellerDaoJDBC Will be Dependent of a Connection Object con
	private Connection con;

	// Forced Dependency Injection through Constructor
	public SellerDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Seller sel) {
		
		// Setting Object for JDBC API Class PreparedStatement execute the Query
		PreparedStatement pst = null;
		
		try {
			/*Setting PreparedStatement Object pst with Insertion command 
			  with parameters passed as values to be replaced right after*/
			
			/*Also overriding prepareStatement method's contructor to include
			a new parameter - the Generated Key from inserted seller*/ 
			
			//OBS1: No case sensitivity at the command string
			pst = con.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			/*Replacing ? parameters with the respective attributes from the Seller Object sel 
			  for the new insertion
			*/

			pst.setString(1, sel.getName());
			pst.setString(2, sel.getEmail());
			/*To work with Data at SQL, java.sql.Date must be imported
			  instead of the usual java.util.Date*/
			pst.setDate(3, new java.sql.Date(sel.getBirthDate().getTime()));
			pst.setDouble(4, sel.getBaseSalary());
			/*To get the Department Id associated with the Seller, first it is necessary to access
			  the Department object related to it as a Dependency Attribute, and then get its Id */
			pst.setInt(5, sel.getDepartment().getId());

			/*Executing command with all updates with an integer of net number of 
	          lines as return, and saving this information in an int variable 
	          rowsChanged
	        */
			int rowsChanged = pst.executeUpdate();

			if (rowsChanged > 0) {
				/*Getting all generated keys from Statement object and storing 
				  them at a Result Set object*/
				ResultSet rs = pst.getGeneratedKeys();
				//Going through ResultSet generated aux table to get the Key
				if (rs.next()) {
					int id = rs.getInt(1);
					/*Setting the key as the Seller Object id, 
					  thus fulfilling all of its attributes */
					
					sel.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				/*Throwing a custom Exception DBExceptionn in case the insertion happens, 
				  but the number of rows changed remain zero since it represents
				  a significant unexpected error and must be identified*/
				throw new DBException("An Unexpected Error occurred! Somehow, no Rows were Changed!");
			}
		}
		//Handling specific exceptions
		catch (SQLException e) {
			/*
			 * Throwing our custom DBException and passing IOException message as a
			 * parameter in DBException superclass constructor. Since it extends RuntimeException, 
			 * this technique will allow us to get rid of undesired compilation alerts
			 * and also show the respective problem message in case it is thrown during
			 * code Runtime
			 */
			throw new DBException(e.getMessage());
		}
		//Using finally block to ensure all external resources to JVM will be closed
		finally {
			DB.closeStatement(pst);
		}

	}

	@Override
	public void update(Seller sel) {
		// Setting Object for JDBC API Class PreparedStatement execute the Query
				PreparedStatement pst = null;
				
				try {
					/*Setting PreparedStatement Object pst with Insertion command 
					  with parameters passed as values to be replaced right after*/
					
					/*Also overriding prepareStatement method's contructor to include
					a new parameter - the Generated Key from inserted seller*/ 
					
					//OBS1: No case sensitivity at the command string
					pst = con.prepareStatement( "UPDATE seller SET Name=?, Email=?, BirthDate=?, BaseSalary=?, DepartmentId=? WHERE Id = ?");
					
					/*Replacing ? parameters with the respective attributes from the Seller Object sel 
					  for the new insertion
					*/

					pst.setString(1, sel.getName());
					pst.setString(2, sel.getEmail());
					/*To work with Data at SQL, java.sql.Date must be imported
					  instead of the usual java.util.Date*/
					pst.setDate(3, new java.sql.Date(sel.getBirthDate().getTime()));
					pst.setDouble(4, sel.getBaseSalary());
					/*To get the Department Id associated with the Seller, first it is necessary to access
					  the Department object related to it as a Dependency Attribute, and then get its Id */
					pst.setInt(5, sel.getDepartment().getId());
					/*Since it is an Update, the Seller Object sel already has a Generated Key Id,
					  and it will also be set here to replace the last ? parameter at the Query
					  Update Command thus identifying the Seller that will be modified at the table*/
					pst.setInt(6, sel.getId());

					//Executing Update
				    pst.executeUpdate();

				}
				//Handling specific exceptions
				catch (SQLException e) {
					/*
					 * Throwing our custom DBException and passing IOException message as a
					 * parameter in DBException superclass constructor. Since it extends RuntimeException, 
					 * this technique will allow us to get rid of undesired compilation alerts
					 * and also show the respective problem message in case it is thrown during
					 * code Runtime
					 */
					throw new DBException(e.getMessage());
				}
				//Using finally block to ensure all external resources to JVM will be closed
				finally {
					DB.closeStatement(pst);
				}


	}

	@Override
	public void deleteById(Integer id) {
		// Setting Object for JDBC API Class PreparedStatement execute the Query
		PreparedStatement st = null;
		try {
			
			// Preparing Query Selection Delete Command with ? parameter for later replacement
			st = con.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			/*Replacing ? parameter with the chosen Id for the deletion process 
			  identification of the target row at SQL table
			 */ 
			st.setInt(1, id);

			/*Executing command with all updates with an integer of net number of 
	          lines as return, and saving this information in an int variable 
	          rowsChanged
	        */
			int rowsChanged = st.executeUpdate();
			
			if(rowsChanged == 0) {
				
				/*Throwing a custom Exception DBExceptionn in case the insertion happens, 
				  but the number of rows changed remain zero since it represents
				  a significant error quite possibly originated from the user's input*/
				
				throw new DBException("An Error occurred! No Rows were Changed. Please, check if your Input was a valid Id!");		
			}
		}
		// Handling specific exceptions
		catch (SQLException e) {
			/*
			 * Throwing our custom DBException and passing IOException message as a
			 * parameter in DBException constructor. Since it extends RuntimeException, this
			 * technique will allow us to get rid of undesired compilation alerts
			 */
			throw new DBException(e.getMessage());
		}
		// Using finally block to ensure all external resources to JVM will be closed
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Seller> findAll() {
		/* Setting Objects for JDBC API Classes PreparedStatement and ResultSet to
		   execute the Query*/

				PreparedStatement pst = null;
				ResultSet rs = null;

				try {
					// Preparing Query Selection Command without WHERE Restriction in order to FIND ALL
					pst = con.prepareStatement(
							"SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id ORDER BY Name");

					// Storing Query Execution Results in a Result Set table
					rs = pst.executeQuery();

					// Declaring Seller List to store ResultSet's Multiple Values
					List<Seller> sellersList = new ArrayList<>();
					/* Declaring Map to ensure no Department objects will be Instanced 
					   more than once
					   */
					Map<Integer, Department> map = new HashMap<>();
					/* While loop to go through all ResultSet Object rs columns in case there are
					   Multiple Values
					*/
					while (rs.next()) {
						// Using Map to filter similar Department IDs to the one at while loop
						Department dep1 = map.get(rs.getInt("DepartmentId"));
						/* Testing if Department Object dep's DepartmentID already was added to the
						   map/list*/
						if (dep1 == null) {
							/* If rs' DepartmentId is unique up to this moment, then a new Department Object
							   will be instanced and added to the Map
							*/
							dep1 = instantiateDepartment(rs);
							map.put(rs.getInt("DepartmentId"), dep1);
						}

						/* Going through ResultSet Data to fulfill Seller Objects while relating them
						   through Dependence
						*/

						Seller sel = instantiateSeller(rs, dep1);
						// Adding Seller Object sel to the Seller List's Node
						sellersList.add(sel);
					}
					return sellersList;

				}
				// Handling specific exceptions
				catch (SQLException e) {
					/*
					 * Throwing our custom DBException and passing IOException message as a
					 * parameter in DBException constructor. Since it extends RuntimeException, this
					 * technique will allow us to get rid of undesired compilation alerts
					 */
					throw new DBException(e.getMessage());
				}
				
				// Using finally block to ensure all external resources to JVM will be closed
				finally {
					DB.closeStatement(pst);
					DB.closeResultSet(rs);
				}
	}

	@Override
	public Seller findById(Integer id) {
		// Setting Objects for JDBC API Classes PreparedStatement and ResultSet to
		// execute the Query

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			// Preparing Query Selection Command with ? parameter for later replacement
			pst = con.prepareStatement(
					"Select seller.*, department.Name AS DepName FROM seller INNER JOIN department ON seller.DepartmentId = departmentId WHERE seller.Id = ?");

			// Replacing Statement Parameters
			pst.setInt(1, id);
			// Storing Query Execution Results in a Result Set table
			rs = pst.executeQuery();

			// Testing if ResultSet is empty or not before starting the ReadById Logic
			if (rs.next()) {

				// Going through ResultSet Data to fulfill Department and Seller Objects while
				// relating them through Dependence

				Department dep = instantiateDepartment(rs);

				Seller sel = instantiateSeller(rs, dep);

				return sel;

			} else {

				return null;

			}

		}
		// Handling specific exceptions
		catch (SQLException e) {
			/*
			 * Throwing our custom DBException and passing IOException message as a
			 * parameter in DBException constructor. Since it extends RuntimeException, this
			 * technique will allow us to get rid of undesired compilation alerts
			 */
			throw new DBException(e.getMessage());
		}
		// Using finally block to ensure all external resources to JVM will be closed
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}

		/*
		 * Leaving the Connection open in order to ensure all different Methods will be
		 * able to be called without interfering on one another. Thus the Connection
		 * Closure duty goes to the MainProgram
		 */

	}

	/*
	 * Creating a Specific Method to Instantiate a Department Object through a
	 * Result Set in order to provide Reuse for its Logic in other CRUD Methods'
	 * bodies
	 */

	private Department instantiateDepartment(ResultSet rs) throws SQLException {

		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));

		return dep;
	}

	/*
	 * Creating a Specific Method to Instantiate a Seller Object through a Result
	 * Set in order to provide Reuse for its Logic in other CRUD Methods' bodies
	 */

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setBirthDate(rs.getDate("BirthDate"));

		/*
		 * Now, it is noteworthy to state that the Department setting at the Seller
		 * Object sel will have as parameter the Department object dep itself, once
		 * Seller establishes a Dependency-between-Objects relation with the already
		 * fully set Department Object
		 */
		sel.setDepartment(dep);

		return sel;
	}

	@Override
	public List<Seller> findByDepartment(Department dep) {
		// Setting Objects for JDBC API Classes PreparedStatement and ResultSet to
		// execute the Query

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			// Preparing Query Selection Command with ? parameter for later replacement
			pst = con.prepareStatement(
					"SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE DepartmentId = ? ORDER BY Name");

			// Replacing Statement Parameters
			pst.setInt(1, dep.getId());
			// Storing Query Execution Results in a Result Set table
			rs = pst.executeQuery();

			// Declaring Seller List to store ResultSet's Multiple Values
			List<Seller> sellersList = new ArrayList<>();
			// Declaring Map to ensure no Department objects will be Instanced more than
			// once
			Map<Integer, Department> map = new HashMap<>();
			// While loop to go through all ResultSet Object rs columns in case there are
			// Multiple Values
			while (rs.next()) {
				// Using Map to filter similar Department IDs to the one at while loop
				Department dep1 = map.get(rs.getInt("DepartmentId"));
				// Testing if Department Object dep's DepartmentID already was added to the
				// map/list
				if (dep1 == null) {
					// If rs' DepartmentId is unique up to this moment, then a new Department Object
					// will be instanced and added to the Map
					dep1 = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep1);
				}

				// Going through ResultSet Data to fulfill Seller Objects while relating them
				// through Dependence

				Seller sel = instantiateSeller(rs, dep1);
				// Adding Seller Object sel to the Seller List's Node
				sellersList.add(sel);
			}
			return sellersList;

		}
		// Handling specific exceptions
		catch (SQLException e) {
			/*
			 * Throwing our custom DBException and passing IOException message as a
			 * parameter in DBException constructor. Since it extends RuntimeException, this
			 * technique will allow us to get rid of undesired compilation alerts
			 */
			throw new DBException(e.getMessage());
		}
		
		// Using finally block to ensure all external resources to JVM will be closed
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
}
