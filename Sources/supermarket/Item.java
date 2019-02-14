package supermarket;

public class Item {
	private String name;
	private int ID;
	private float price;
	
	public Item(String name, int ID, float price) {
		this.name = name;
		this.ID = ID;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public String toString() {
		return name + ";" + ID + ";" + String.format("%.02f", price);
	}
}
