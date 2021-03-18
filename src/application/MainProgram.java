package application;

import java.util.List;

import model.DAO.DaoFactory;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class MainProgram {

	public static void main(String[] args) {
		
		/*Instancing sellerDAO  through DaoFactory Method createSellerDAO
		  instead of direct instancing*/
		
		/*OBS: This also makes easy for future Changes at the Interface Implementation 
		  Concrete Classes, since it reduces one Point of Change at the Code*/
		
		SellerDAO sellerDao = DaoFactory.createSellerDAO();
		
		/*===========================================
		  =========TEST #1: Seller findById =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "=========TEST #1: Seller findById =========\r\n"
				+ "===========================================\r\n");
		
		//Testing findById Method
		Seller sel = sellerDao.findById(3);
		
		System.out.println(sel);

		
		System.out.println("");
		
		
		/*===========================================
		  =========TEST #1: Seller findById =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "=====TEST #2: Seller findByDepartment =====\r\n"
				+ "===========================================\r\n");
		Department dep = new Department (2, null);
		List<Seller> sellerList = sellerDao.findByDepartment(dep);
		//Listing sellerList Elements
		for (Seller seller : sellerList) {
			System.out.println(seller);
		}
		

	}

}
