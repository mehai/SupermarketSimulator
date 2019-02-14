package supermarket;

import java.util.ArrayList;

public class Store {
	
	private static Store store = null;
	private String name;
	private ArrayList<Department> departments;
	private ArrayList<Customer> customers;
	
	private Store() {
		this.name = "StoreDefaultName";
		departments = new ArrayList<>();
		customers = new ArrayList<>();
	}
	
	public static Store getInstance() {
		if(store == null) {
			store = new Store();
		}
		return store;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	void enter(Customer customer) {
		customers.add(customer);
	}
	
	void exit(Customer customer) {
		customers.remove(customer);
	}
	
	public ShoppingCart getShoppingCart(Double budget) {
		return new ShoppingCart(budget);
	}
	
	public ArrayList<Customer> getCustomers(){
		return customers;
	}
	
	public ArrayList<Department> getDepartments(){
		return departments;
	}
	
	void addDepartment(Department department) {
		departments.add(department);
	}
	
	Department getDepartment(Integer id) {
		for(Department department: departments) {
			if(department.getID() == id) {
				return department;
			}
		}
		return null;
	}
	
}
