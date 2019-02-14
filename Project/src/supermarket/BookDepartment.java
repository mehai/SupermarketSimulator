package supermarket;

public class BookDepartment extends Department{

	public BookDepartment(String name, int ID) {
		super(name, ID);
	}

	@Override
	void accept(ShoppingCart cart) {
		cart.visit(this);
	}

}
