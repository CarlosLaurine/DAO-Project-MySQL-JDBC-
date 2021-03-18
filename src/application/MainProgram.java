package application;

import model.DAO.DaoFactory;
import model.DAO.SellerDAO;
import model.entities.Seller;

public class MainProgram {

	public static void main(String[] args) {
		
		/*Instancing sellerDAO  through DaoFactory Method createSellerDAO
		  instead of direct instancing*/
		
		/*OBS: This also makes easy for future Changes at the Interface Implementation 
		  Concrete Classes, since it reduces one Point of Change at the Code*/
		
		/*===========================================
		  =========TEST #1: Seller findById =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "=========TEST #1: Seller findById =========\r\n"
				+ "===========================================\r\n");
		SellerDAO sellerDao = DaoFactory.createSellerDAO();
		
		//Testing findById Method
		Seller sel = sellerDao.findById(3);
		
		System.out.println(sel);

	}

}
