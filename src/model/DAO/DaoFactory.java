package model.DAO;

import model.DAO.impl.SellerDaoJDBC;

import db.DB;

//Creating DaoFactory Class with Static Methods to Instance the DAOs

public class DaoFactory {

	/*"Hiding" SellerDaoJDBC implementation with Static Method createSellerDAO,
	   leaving only the Interface Exposed in its Method calls*/
	
	/*OBS: This is also a way to perform a Dependence Injection without
	  making the implementation explicit */
	
	public static SellerDAO createSellerDAO() {
		
		return new SellerDaoJDBC(DB.getConnection());
	}
}
