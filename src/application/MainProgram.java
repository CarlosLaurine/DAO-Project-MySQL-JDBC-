package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.DAO.DaoFactory;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class MainProgram {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
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
		  =====TEST #2: Seller findByDepartment =====
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
		
		/*===========================================
		  =========TEST #3: Seller findAll =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "==========TEST #3: Seller findAll==========\r\n"
				+ "===========================================\r\n");
		sellerList = sellerDao.findAll();
		//Listing sellerList Elements
		for (Seller seller : sellerList) {
			System.out.println(seller);
		}
		
		/*===========================================
		  =========TEST #4: Seller insert =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "==========TEST #4: Seller  insert==========\r\n"
				+ "===========================================\r\n");
		/*Setting new Seller Object sel2's Id parameter as null, since it will only be
		  fulfilled after the insertion method inserts it at the DataBase and sets the
		  returned GeneratedKey from SQL as its Id Attribute
		*/
		
		Seller sel2 = new Seller (null, "Tobey", "tmaguire@hotmail.com", new Date(), 2574.00, dep);
		sellerDao.insert(sel2);
		
		/*Testing if Insertion process was completed by printing the GeneratedKey
		  returned by the Query Insertion Command and set as Seller Object sel2's 
		  Id Attribute*/
		
		System.out.println("Insertion Completed! Inserted Seller's new Id = " + sel2.getId());
		
		/*===========================================
		  =========TEST #5: Seller update =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "==========TEST #5: Seller  update==========\r\n"
				+ "===========================================\r\n");
		//Loading a Seller Object sel with an existent seller at the DataBase
		sel = sellerDao.findById(1);
		//Setting a new name (Updating) Seller Object sel
		sel.setName("Ruffus Pringles");
		//Calling DAOJDBC Update Method to transmit the update to the DataBase table
		sellerDao.update(sel);
		//Signaling that the update process ran trough full completion without exceptions
		System.out.println("Update Process Completed!");

		/*===========================================
		  =========TEST #6: Seller delete =========
		  ===========================================*/
		System.out.println("===========================================\r\n"
				+ "==========TEST #6: Seller  delete==========\r\n"
				+ "===========================================\r\n");
		//Entering the intended Id for deletion process
		System.out.println("Enter Id for the target Seller to be deleted from DataBase");
		int id = sc.nextInt();
		//Deleting corresponding seller
		sellerDao.deleteById(id);
		//Signaling that the deletion process ran trough full completion without exceptions
		System.out.println("Delete Process Completed!");
		sc.close();

	}

}
