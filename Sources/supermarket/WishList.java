package supermarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.ListIterator;

public class WishList extends ItemList{

	Strategy strategy;
	//an array to keep the dates of insertion in the wish list
	//useful for strategy C
	ArrayList<Date> dates;
	
	public WishList(char s) {
		super();
		dates = new ArrayList<>();
		//create Strategy object
		switch (s) {
		case 'A':
			strategy = new StrategyA();
			break;
		case 'B':
			strategy = new StrategyB();
			break;
		case 'C':
			strategy = new StrategyC();
			break;
		default:
			strategy = null;
			break;
		}
		//create comparator for alphabetical ordering
		comparator = new Comparator<Item>() {		
			@Override
			public int compare(Item o1, Item o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}
	
	public Item executeStrategy() {
		Item toBeMoved = strategy.execute(this);
		//return it to be moved to ShoppingCart
		return toBeMoved;
	}
	
	public Date getDate(int index) throws IllegalArgumentException{
		if(index < 0 || index >= dates.size())
			throw new IllegalArgumentException();
		return dates.get(index);
	}
	
	public ArrayList<Date> getDates() {
		return dates;
	}
	
	@Override
	public boolean add(Item element) {	
		int index = 0;
		//iterate through the list
		ListIterator<Item> iterator = listIterator();
		while(iterator.hasNext()) {
			Item item = iterator.next();
			//find the correct position for insertion
			if(comparator.compare(element, item) < 0) {
				//go to the correct position and add element
				iterator.previous();
				iterator.add(element);
				//add the date of the insertion
				index = iterator.previousIndex();
				dates.add(index, new Date());
				return true;
			}
		}
		//if the item was not added by now -> it's the last item
		iterator.add(element);
		//add the date of the insertion
		index = size - 1;
		dates.add(index, new Date());
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Item> c) {
		for(Item item : c) {
			add(item);
		}
		return true;
	}
}
