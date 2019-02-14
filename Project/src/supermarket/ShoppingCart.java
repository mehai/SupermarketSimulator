package supermarket;

import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;

public class ShoppingCart extends ItemList implements Visitor{
	
	private Double budget;
	
	public ShoppingCart(Double budget) {
		super();
		this.budget = budget;
		comparator = new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				if(o1.getPrice() > o2.getPrice()) {
					return 1;
				}else if(o1.getPrice() < o2.getPrice()) {
					return -1;
				}else {
					return o1.getName().compareTo(o2.getName());
				}
			}
		};
	}
	
	@Override
	public void visit(BookDepartment bookDepartment) {
		Node<Item> current = head;
		while(current != null) {
			//check if the item is from the bookDepartment
			int itemID = current.getItem().getID();
			for(int i = 0; i < bookDepartment.items.size(); i++) {
				if(bookDepartment.items.get(i).getID() == itemID) {
					//set new price and update budget - 10% discount
					float price = current.getItem().getPrice();
					float newPrice = (float)(price * 0.9);
					current.getItem().setPrice(newPrice);
					budget += price - newPrice;
					break;
				}
			}
			//check the next element in the ShoppingCart
			current = current.getNext();
		}
		//sort list
		sort();
	}

	@Override
	public void visit(MusicDepartment musicDepartment) {
		Node<Item> current = head;
		Double total = 0.0;
		while(current != null) {
			//check if the item is from the musicDepartment
			int itemID = current.getItem().getID();
			for(int i = 0; i < musicDepartment.items.size(); i++) {
				if(musicDepartment.items.get(i).getID() == itemID) {
					//add to the total value of music products
					total += current.getItem().getPrice();
					break;
				}
			}
			//check the next element in the ShoppingCart
			current = current.getNext();
		}
		//update budget
		budget += total * 0.1;
	}

	@Override
	public void visit(SoftwareDepartment softwareDepartment) {
		//if condition was met
		if(budget < softwareDepartment.getLowestPrice()) {
			Node<Item> current = head;
			while(current != null) {
				//check if the item is from the softwareDepartment
				int itemID = current.getItem().getID();
				for(int i = 0; i < softwareDepartment.items.size(); i++) {
					if(softwareDepartment.items.get(i).getID() == itemID) {
						//set new price and update budget - 20% discount
						float price = current.getItem().getPrice();
						float newPrice = (float)(price * 0.8);
						current.getItem().setPrice(newPrice);
						budget += price - newPrice;
						break;
					}
				}
				//check the next element in the ShoppingCart
				current = current.getNext();
			}
			//sort list
			sort();
		}
	}

	@Override
	public void visit(VideoDepartment videoDepartment) {
		Node<Item> current = head;
		Double total = 0.0;
		while(current != null) {
			//check if the item is from the videoDepartment
			int itemID = current.getItem().getID();
			for(int i = 0; i < videoDepartment.items.size(); i++) {
				if(videoDepartment.items.get(i).getID() == itemID) {
					//add to the total value of video products
					total += current.getItem().getPrice();
					break;
				}
			}
			//check the next element in the ShoppingCart
			current = current.getNext();
		}
		//if the condition for the 15% discount was met
		if(total > videoDepartment.getHighestPrice()) {
			current = head;
			while(current != null) {
				//check if the item is from the videoDepartment
				int itemID = current.getItem().getID();
				for(int i = 0; i < videoDepartment.items.size(); i++) {
					if(videoDepartment.items.get(i).getID() == itemID) {
						//set new price and update budget - 15% discount
						float price = current.getItem().getPrice();
						float newPrice = (float)(price * 0.85);
						current.getItem().setPrice(newPrice);
						budget += price - newPrice;
						break;
					}
				}
				//check the next element in the ShoppingCart
				current = current.getNext();
			}
			//sort list
			sort();
			//apply the 15% discount on computed total to get the new total
			total *= 0.85;
		}
		//update budget
		budget += total * 0.05;
	}

	//an implementation for bubble sort for this list
	public void sort() {
		boolean done = false;
		while(!done) {
			done = true;
			Node<Item> pointer = head;
			for(; pointer != null && pointer.getNext() != null;) {
				if(comparator.compare(pointer.getItem(), pointer.getNext().getItem()) > 0) {
					done = false;
					//swap
					Node<Item> prev = pointer.getPrev();
					Node<Item> next = pointer.getNext().getNext();
					pointer.getNext().setPrev(prev);
					pointer.getNext().setNext(pointer);
					pointer.setPrev(pointer.getNext());
					pointer.setNext(next);
					if(prev != null) {
						prev.setNext(pointer.getPrev());
					}else {
						head = pointer.getPrev();
					}
					if(next != null) {
						next.setPrev(pointer);
					}else {
						tail = pointer;
					}
				}else {
					pointer = pointer.getNext();
				}
			}
		}
	}
	
	@Override
	public boolean add(Item element) {
		//budget condition
		if(budget < element.getPrice()) {
			return false;
		}
		//iterate through the list
		ListIterator<Item> iterator = listIterator();
		while(iterator.hasNext()) {
			Item item = iterator.next();
			//find the correct position for insertion
			if(comparator.compare(element, item) < 0) {
				//go to the correct position and add element
				iterator.previous();
				iterator.add(element);
				budget -= element.getPrice();
				return true;
			}
		}
		//if the item was not added by now -> it's the last item
		iterator.add(element);
		budget -= element.getPrice();
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Item> c) {
		boolean modified = false;
		for(Item item : c) {
			if(add(item)) {
				modified = true;
			}
		}
		return modified;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}
	
	
}
