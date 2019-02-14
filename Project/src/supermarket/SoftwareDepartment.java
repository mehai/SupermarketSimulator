package supermarket;

public class SoftwareDepartment extends Department{

	public SoftwareDepartment(String name, int ID) {
		super(name, ID);
	}

	@Override
	void accept(ShoppingCart cart) {
		cart.visit(this);
	}
	
	public float getLowestPrice() {
		float lowestPrice = items.get(0).getPrice();
		for(int i = 1; i < items.size(); i++) {
			if(items.get(i).getPrice() < lowestPrice) {
				lowestPrice = items.get(i).getPrice();
			}
		}
		return lowestPrice;
	}
}
