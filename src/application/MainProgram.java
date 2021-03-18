package application;

import java.util.Date;

import model.DAO.DaoFactory;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class MainProgram {

	public static void main(String[] args) {
		
		Department object = new Department(1,"Books");
		
		Seller seller = new Seller(32, "Anthony", "tony@yahoo.com.br", new Date() , 2750.00 , object);
		
		/*Instancing sellerDAO  through DaoFactory Method createSellerDAO
		  instead of direct instancing*/
		/*OBS: This also makes easy for future Changes at the Interface Implementation 
		  Concrete Classes, since it reduces one Point of Change at the Code*/
		SellerDAO sellerDao = DaoFactory.createSellerDAO();
		
		System.out.println(object);
		System.out.println(seller);

	}

}
