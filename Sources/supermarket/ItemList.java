package supermarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class ItemList{
	
	Comparator<Item> comparator;
	Node<Item> head, tail;
	int size;
	
	public ItemList() {
		head = tail = null;
		size = 0;
	}
	
	static class Node<E>{
		private E item;
		private Node<E> next;
		private Node<E> prev;
		
		Node(E item){
			this.item = item;
			next = null;
			prev = null;
		}

		public E getItem() {
			return item;
		}

		public void setItem(E item) {
			this.item = item;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public void setPrev(Node<E> prev) {
			this.prev = prev;
		}
	}
	
	class ItemIterator implements ListIterator<Item>{

		private Node<Item> next;
		private Node<Item> previous;
		private int index;
		private Boolean lastCallNext;
		
		public ItemIterator(int index) {
			this.index = index;
			previous = null;
			next = head;
			for(int i = 0; i < index; i++) {
				previous = next;
				next = next.next;
			}
			lastCallNext = null;
		}
		
		@Override
		public boolean hasNext() {
			if(next != null) {
				return true;
			}
			return false;
		}

		@Override
		public Item next() throws NoSuchElementException{
			if(next == null)
				throw new NoSuchElementException();
			previous = next;
			next = next.next;
			index++;
			lastCallNext = true;
			return previous.item;
		}

		@Override
		public boolean hasPrevious() {
			if(previous != null) {
				return true;
			}
			return false;
		}

		@Override
		public Item previous() throws NoSuchElementException{
			if(previous == null)
				throw new NoSuchElementException();
			next = previous;
			previous = previous.prev;
			index--;
			lastCallNext = false;
			return next.item;
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index-1;
		}

		@Override
		//implemented following the instructions in the documentation
		public void remove() throws IllegalStateException{
			if(lastCallNext == null){
				throw new IllegalStateException();
			}else if(lastCallNext == true) {
				Node<Item> toBeDeleted = previous;
				if(toBeDeleted.prev != null) {
					toBeDeleted.prev.next = toBeDeleted.next;					
				}else {
					head = toBeDeleted.next;
				}
				if(toBeDeleted.next != null) {
					toBeDeleted.next.prev = toBeDeleted.prev;					
				}else {
					tail = toBeDeleted.prev;
				}
				previous = toBeDeleted.prev;
				index--;
			}else {
				Node<Item> toBeDeleted = next;
				if(toBeDeleted.prev != null) {
					toBeDeleted.prev.next = toBeDeleted.next;					
				}else {
					head = toBeDeleted.next;
				}
				if(toBeDeleted.next != null) {
					toBeDeleted.next.prev = toBeDeleted.prev;					
				}else {
					tail = toBeDeleted.prev;
				}
				next = toBeDeleted.next;
			}
			size--;
			lastCallNext = null;
		}

		@Override
		//implemented following the instructions in the documentation
		public void set(Item e) throws IllegalStateException{
			if(lastCallNext == null){
				throw new IllegalStateException();
			}else if(lastCallNext == true) {
				Node<Item> toBeSet = previous;
				toBeSet.item = e;
			}else {
				Node<Item> toBeSet = next;
				toBeSet.item = e;
			}
		}

		@Override
		//implemented following the instructions in the documentation
		public void add(Item e) {
			Node<Item> newNode = new Node<Item>(e);
			newNode.prev = previous;
			newNode.next = next;
			if(previous != null) {
				previous.next = newNode;				
			}else {
				head = newNode;
			}
			if(next != null) {
				next.prev = newNode;				
			}else {
				tail = newNode;
			}
			index++;
			size++;
			lastCallNext = null;
			previous = newNode;
		}
		
	}
	
	abstract public boolean add(Item element);
	abstract public boolean addAll(Collection<? extends Item> c);
	
	public Item getItem(int index) {
		if(index < 0 || index >= size)
			throw new IllegalArgumentException();
		Node<Item> current = head;
		while(current != null && index > 0) {
			current = current.next;
			index--;
		}
		return current != null ? current.item : null;
	}
	
	public Node<Item> getNode(int index) throws IllegalArgumentException{
		if(index < 0 || index >= size)
			throw new IllegalArgumentException();
		Node<Item> current = head;
		while(current != null && index > 0) {
			current = current.next;
			index--;
		}
		return current;
	}
	
	public int indexOf(Item item) {
		int index = 0;
		for(Node<Item> current = head; current != null; current = current.next) {
			if(current.item == item) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public int indexOf(Node<Item> node) {
		int index = 0;
		for(Node<Item> current = head; current != null; current = current.next) {
			if(current == node) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public boolean contains(Node<Item> node) {
		for(Node<Item> current = head; current != null; current = current.next) {
			if(current == node) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Item item) {
		for(Node<Item> current = head; current != null; current = current.next) {
			if(current.item == item) {
				return true;
			}
		}
		return false;
	}
	
	public Item remove(int index) throws IllegalArgumentException{
		if(index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}
		//find node
		Node<Item> toBeDeleted = getNode(index);
		if(toBeDeleted == null)
			return null;
		//delete the actual node from the list
		if(toBeDeleted.prev != null) {
			toBeDeleted.prev.next = toBeDeleted.next;
		}else {
			head = toBeDeleted.next;
		}
		if(toBeDeleted.next != null) {
			toBeDeleted.next.prev = toBeDeleted.prev;
		}else {
			tail = toBeDeleted.prev;
		}
		//decrement size and return
		size--;
		return toBeDeleted.item;
	}
	
	public boolean remove(Item item) {
		//find index of node
		int index = indexOf(item);
		if(index < 0) {
			return false;
		}
		remove(index);
		return true;
	}
	
	public boolean removeAll(Collection<? extends Item> collection) {
		boolean modified = false;
		for(Item item: collection) {
			if(remove(item)) {
				modified = true;
			}
		}
		return modified;
	}
	
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}
	
	public ListIterator<Item> listIterator(int index) throws IllegalArgumentException{
		if(index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		return new ItemIterator(index);
	}
	
	public ListIterator<Item> listIterator(){
		return new ItemIterator(0);
	}
	
	public Double getTotalPrice() {
		ListIterator<Item> iterator = listIterator();
		Double result = 0.0;
		while(iterator.hasNext()) {
			result += iterator.next().getPrice();
		}
		return result;
	}
	
	public List<Item> getList() {
		List<Item> list = new ArrayList<>();
		ListIterator<Item> iterator = listIterator();
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}
}
