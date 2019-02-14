package supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class EventHandler {
	
	//***********************************************
	//AUXILIARY METHODS
	//***********************************************
	
	//find the item in the product lists and return a new instance of it
	public Item getItem(int itemID) {
		//get the departments list
		for(Department dep: Store.getInstance().getDepartments()) {
			//look in the items list of this department
			for(Item item: dep.getItems()) {
				//if it has the same id, return a duplicate
				if(item.getID() == itemID) {
					return new Item(item.getName(), itemID, item.getPrice());
				}
			}
		}
		return null;
	}
	
	//find the item in the depID department's list of items and return it
	public Item getProduct(int depID, int itemID) {
		//get department
		Department dep = Store.getInstance().getDepartment(depID);
		//look at each item in the department
		for(Item item : dep.getItems()) {
			if(item.getID() == itemID) {
				return item;
			}
		}
		return null;
	}
	
	//find the department in the departments list and return it
	public Department getDepartment(int depID) {
		//look at each department
		for(Department dep: Store.getInstance().getDepartments()) {
			if(dep.getID() == depID) {
				return dep;
			}
		}
		return null;
	}
	
	public Department getDeprtmentFromItem(int itemID) {
		//get the departments list
		for(Department dep: Store.getInstance().getDepartments()) {
			//look in the items list of this department
			for(Item item: dep.getItems()) {
				//if it has the same id, return its department
				if(item.getID() == itemID) {
					return dep;
				}
			}
		}
		return null;
	}
	
	//find the customer in the customers list and return it
	public Customer getCustomer(String customerName) {
		//look at each customer in the list
		for(Customer customer: Store.getInstance().getCustomers()) {
			if(customer.getName().equals(customerName)) {
				return customer;
			}
		}
		return null;
	}
	
	//***********************************************
	//ACTUAL EVENTS
	//***********************************************
	
	public Item addItem(int itemID, String list, String customerName) throws Exception{
		Item newItem = getItem(itemID);
		Department dep = getDeprtmentFromItem(itemID);
		Customer customer = getCustomer(customerName);
		//check cart / wishlist
		if(list.equals("ShoppingCart")) {
			customer.getCart().add(newItem);
			//add customer to department customers list
			dep.enter(customer);
		}else if(list.equals("WishList")) {
			customer.getWishlist().add(newItem);
			//add customer to department observers list
			dep.addObserver(customer);
		}else {
			throw new Exception("Unidentified list!");
		}
		return newItem;
	}
	
	public void delItem(int itemID, String list, String customerName) throws Exception{
		Customer customer = getCustomer(customerName);
		Department dep = getDeprtmentFromItem(itemID);
		//check cart / wishlist
		if(list.equals("ShoppingCart")) {
			//find item and remove it
			ListIterator<Item> iterator = customer.getCart().listIterator();
			while(iterator.hasNext()) {
				Item item = iterator.next();
				if(item.getID() == itemID) {
					iterator.remove();
					break;
				}
			}
			//check if there are other items from this dep
			boolean otherItems = false;
			iterator = customer.getCart().listIterator();
			while(iterator.hasNext()) {
				Item item = iterator.next();
				//if there is, break
				if(getProduct(dep.getID(), item.getID()) != null){
					otherItems = true;
					break;
				}
			}
			//if there are no other items, remove customer from deps list
			if(!otherItems) {
				dep.exit(customer);
			}
		}else if(list.equals("WishList")) {
			int index = 0;
			//find the item and remove it
			ListIterator<Item> iterator = customer.getWishlist().listIterator();
			while(iterator.hasNext()) {
				Item item = iterator.next();
				if(item.getID() == itemID) {
					index = iterator.previousIndex();
					iterator.remove();
					break;
				}
			}
			//remove the date of its insertion
			customer.getWishlist().getDates().remove(index);
			//check if there are other items from this dep
			boolean otherItems = false;
			iterator = customer.getWishlist().listIterator();
			while(iterator.hasNext()) {
				Item item = iterator.next();
				//if there is, break
				if(getProduct(dep.getID(), item.getID()) != null){
					otherItems = true;
					break;
				}
			}
			//if there are no other items, remove customer from deps list
			if(!otherItems) {
				dep.removeObserver(customer);
			}
		}else {
			throw new Exception("Unidentified list!");
		}
	}
	
	public Item addProduct(int depID, int itemID, float price, String name) {
		Department dep = getDepartment(depID);
		Item item = new Item(name, itemID, price);
		dep.addItem(item);
		return item;
	}
	
	public void modifyProduct(int depID, int itemID, float price) {
		Department dep = getDepartment(depID);
		dep.modifyProduct(itemID, price);
	}
	
	public void delProduct(int itemID) {
		Department dep = getDeprtmentFromItem(itemID);
		dep.deleteProduct(itemID);
		//update observers for the department
		for(Customer observer: dep.futureCustomers) {
			boolean toBeRemovedObserver = true;
			//iterate through the wishlist
			ListIterator<Item> iterator = observer.getWishlist().listIterator();
			while(iterator.hasNext()) {
				Item item = iterator.next();
				//if there is a product from this department
				if(getProduct(dep.getID(), item.getID()) != null) {
					toBeRemovedObserver = false;
					break;
				}
			}
			//if there are no more products from this department
			if(toBeRemovedObserver) {
				dep.futureCustomers.remove(observer);
			}
		}
	}
	
	//TODO
	public  Item getItem(String customerName) throws Exception{
		Customer customer = getCustomer(customerName);
		//get item
		Item item = customer.getWishlist().executeStrategy(); 
		//delete it from WishList
		delItem(item.getID(), "WishList", customerName);
		//add it to ShoppingCart
		customer.getCart().add(item);
		getDeprtmentFromItem(item.getID()).enter(customer);
		return item;
	}
	
	public List<Item> getItems(String list, String customerName) throws Exception{
		Customer customer = getCustomer(customerName);
		//check cart / wishlist
		if(list.equals("ShoppingCart")) {
			return customer.getCart().getList();
		}else if(list.equals("WishList")) {
			return customer.getWishlist().getList();
		}else {
			throw new Exception("Unidentified list!");
		}
	}
	
	public double getTotal(String list, String customerName) throws Exception{
		Customer customer = getCustomer(customerName);
		//check cart / wishlist
		if(list.equals("ShoppingCart")) {
			Double total = customer.getCart().getTotalPrice();
			//round it up before returning
			return new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		}else if(list.equals("WishList")) {
			Double total = customer.getWishlist().getTotalPrice();
			//round it up before returning
			return new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		}else {
			throw new Exception("Unidentified list!");
		}
	}
	
	public void accept(int depID, String customerName) {
		Department dep = getDepartment(depID);
		Customer customer = getCustomer(customerName);
		dep.accept(customer.getCart());
	}
	
	public ArrayList<Customer> getObservers(int depID) {
		Department dep = getDepartment(depID);
		return dep.getObservers();
	}
	
	public ArrayList<Notification> getNotifications(String customerName){
		Customer customer = getCustomer(customerName);
		return customer.getNotifications();
	}
}
