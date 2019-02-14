package supermarket;

import java.util.ArrayList;
import java.util.Date;

public abstract class Department {
	
	private String name;
	ArrayList<Item> items;
	//the ones who have items from this dep in shoppingCart
	ArrayList<Customer> formerCustomers;
	//observers - items from this dep in WishList
	ArrayList<Customer> futureCustomers;	
	private int ID;
	
	public Department(String name, int ID) {
		this.name = name;
		this.ID = ID;
		items = new ArrayList<>();
		formerCustomers = new ArrayList<>();
		futureCustomers = new ArrayList<>();
	}
	
	void enter(Customer customer) {
		//check if customer is already on the list
		for(int i = 0; i < formerCustomers.size(); i++) {
			if(formerCustomers.get(i).equals(customer)) {
				return;
			}
		}
		formerCustomers.add(customer);
	}
	
	void exit(Customer customer) {
		formerCustomers.remove(customer);
	}
	
	public ArrayList<Customer> getCustomers(){
		return formerCustomers;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	void addItem(Item item) {
		items.add(item);
		notifyAllObservers(
				new Notification(new Date(), NotificationType.ADD, this.ID, item.getID()));
	}
	
	void modifyProduct(int itemID, float price) {
		for(Item item: items) {
			if(item.getID() == itemID) {
				item.setPrice(price);
				notifyAllObservers(
						new Notification(new Date(), NotificationType.MODIFY, this.ID, itemID));
				break;
			}
		}
	}
	
	void deleteProduct(int itemID) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getID() == itemID) {
				items.remove(i);
				notifyAllObservers(
						new Notification(new Date(), NotificationType.REMOVE, this.ID, itemID));
				break;
			}
		}
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public ArrayList<Customer> getObservers() {
		return futureCustomers;
	}
	
	void addObserver(Customer customer) {
		//check if observer is already on the list
		for(int i = 0; i < futureCustomers.size(); i++) {
			if(futureCustomers.get(i).equals(customer)) {
				return;
			}
		}
		futureCustomers.add(customer);
	}
	
	void removeObserver(Customer customer) {
		futureCustomers.remove(customer);
	}
	
	void notifyAllObservers(Notification notification) {
		for(Customer observer: futureCustomers) {
			observer.update(notification);
		}
	}
	
	abstract void accept(ShoppingCart cart);
}
