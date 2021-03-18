package model.DAO;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO {
	
	//Implementing CRUD Methods
		void insert(Seller sel);
		void update(Seller sel);
		void deleteById(Integer id);
		List<Seller> findAll();
		
		Seller findById(Integer id);
		
		List<Seller> findByDepartment (Department dep);
		

}
