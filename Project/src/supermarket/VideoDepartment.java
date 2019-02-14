package supermarket;

public class VideoDepartment extends Department{

	public VideoDepartment(String name, int ID) {
		super(name, ID);
	}

	@Override
	void accept(ShoppingCart cart) {
		cart.visit(this);
	}

	public float getHighestPrice() {
		float highestPrice = items.get(0).getPrice();
		for(int i = 1; i < items.size(); i++) {
			if(items.get(i).getPrice() > highestPrice) {
				highestPrice = items.get(i).getPrice();
			}
		}
		return highestPrice;
	}
	
}
