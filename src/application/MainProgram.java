package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class MainProgram {

	public static void main(String[] args) {
		
		Department object = new Department(1,"Books");
		
		Seller seller = new Seller(32, "Anthony", "tony@yahoo.com.br", new Date() , 2750.00 , object);
	
		System.out.println(object);
		System.out.println(seller);

	}

}
