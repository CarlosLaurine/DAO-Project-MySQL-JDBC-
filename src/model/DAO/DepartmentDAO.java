package model.DAO;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	//Implementing CRUD Methods
	void insert(Department dep);
	void update(Department dep);
	void deleteById(Integer id);
	List<Department> findAll();
	Department findById(Integer id);
	
	

}
