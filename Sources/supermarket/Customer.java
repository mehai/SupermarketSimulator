package supermarket;

import java.util.ArrayList;
import java.util.ListIterator;

public class Customer {

	private String name;
	private ShoppingCart cart;
	private WishList wishlist;
	private ArrayList<Notification> notifications;
	
	public Customer(String name, double budget, char s) {
		this.name = name;
		cart = Store.getInstance().getShoppingCart(budget);
		wishlist = new WishList(s);
		notifications = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public ShoppingCart getCart() {
		return cart;
	}
	
	public void setCart(double budget) {
		cart = Store.getInstance().getShoppingCart(budget);
	}

	public WishList getWishlist() {
		return wishlist;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}
	
	public String toString() {
		return name;
	}

	public void update(Notification notification) {
		notifications.add(notification);
		//update states
		switch(notification.getType()) {
			case MODIFY:
				this.modifyPrice(notification.getDepID(), notification.getProdID());
				break;
			
			case REMOVE:
				this.removeProduct(notification.getProdID());
				break;
				
			case ADD:
				break;
		}
	}
	
	public void modifyPrice(int depID, int prodID) {
		//find the new price
		float newPrice = (float)0.0;
		Department department = Store.getInstance().getDepartment(depID);
		ArrayList<Item> items = department.getItems();
		for(Item item: items) {
			if(item.getID() == prodID) {
				newPrice = item.getPrice();
				break;
			}
		}
		//find the item in the cart and if it exists modify the price
		ListIterator<Item> iterator = cart.listIterator();
		while(iterator.hasNext()) {
			Item item = iterator.next();
			if(item.getID() == prodID) {
				float oldPrice = item.getPrice();
				item.setPrice(newPrice);
				//update budget
				double budget = cart.getBudget();
				cart.setBudget(budget - newPrice + oldPrice);
				break;
			}
		}
		//sort the list after the modification of price
		getCart().sort();
		//find the item in the wish list and if it exists modify the price
		iterator = wishlist.listIterator();
		while(iterator.hasNext()) {
			Item item = iterator.next();
			if(item.getID() == prodID) {
				item.setPrice(newPrice);
				break;
			}
		}
	}
	
	public void removeProduct(int prodID) {
		//find the item in the cart and if it exists remove it
		ListIterator<Item> iterator = cart.listIterator();
		while(iterator.hasNext()) {
			Item item = iterator.next();
			if(item.getID() == prodID) {
				float oldPrice = item.getPrice();
				iterator.remove();
				//update budget
				double budget = cart.getBudget();
				cart.setBudget(budget + oldPrice);
				break;
			}
		}
		//find the item in the wish list and if it exists remove it
		iterator = wishlist.listIterator();
		while(iterator.hasNext()) {
			Item item = iterator.next();
			if(item.getID() == prodID) {
				iterator.remove();
				break;
			}
		}
	}
	
}
