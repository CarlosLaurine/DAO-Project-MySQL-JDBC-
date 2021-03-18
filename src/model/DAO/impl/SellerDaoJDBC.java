package model.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DBException;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

//Creating concrete Class SellerDaoJDBC to Implement the Interface
public class SellerDaoJDBC implements SellerDAO{
	
	//SellerDaoJDBC Will be Dependent of a Connection Object con
	private Connection con;
	
	//Forced Dependency Injection through Constructor
	public SellerDaoJDBC (Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Seller sel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller sel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seller findById(Integer id) {
		//Setting Objects for JDBC API Classes PreparedStatement and ResultSet to execute the Query
	
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			//Preparing Query Selection Command with ? parameter for later replacement
			pst = con.prepareStatement("Select seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = departmentId WHERE seller.Id = ?");
			
			//Replacing Statement Parameters
			pst.setInt(1, id);
			//Storing Query Execution Results in a Result Set table
			rs = pst.executeQuery();
			
			//Testing if ResultSet is empty or not before starting the ReadById Logic
			if(rs.next()) {
	
			//Going through ResultSet Data to fulfill Department and Seller Objects while relating them through Dependence       
			
			Department dep = new Department();	
			dep.setId(rs.getInt("DepartmentId"));
			dep.setName(rs.getString("DepName"));
			
			Seller sel = new Seller();
			sel.setId(rs.getInt("Id"));
			sel.setName(rs.getString("Name"));
			sel.setEmail(rs.getString("Email"));
			sel.setBaseSalary(rs.getDouble("BaseSalary"));
			sel.setBirthDate(rs.getDate("BirthDate"));
			
			/*Now, it is noteworthy to state that the Department setting at the Seller Object sel
			  will have as parameter the Department object dep itself, once Seller stablishes
			  a Dependency-between-Objects relation with the already fully set Department Object
			*/
			sel.setDepartment(dep);
			return sel;
			}
			else {
				
				return null;
				
			}
				
		}
		//Handling specific exceptions
		catch(SQLException e) {
			/*Throwing our custom DBException and passing IOException message as a parameter
			  in DBException constructor. Since it extends RuntimeException, this technique 
			  will allow us to get rid of undesired compilation alerts*/
			throw new DBException (e.getMessage());
		}
		//Using finally block to ensure all external resources to JVM will be closed
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		
		/*Leaving the Connection open in order to ensure all different Methods will be able to be called
		  without interfering on one another. Thus the Connection Closure duty goes
		  to the MainProgram*/
		
	}

}
