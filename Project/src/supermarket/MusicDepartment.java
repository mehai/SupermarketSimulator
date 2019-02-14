package supermarket;

public class MusicDepartment extends Department{

	public MusicDepartment(String name, int ID) {
		super(name, ID);
	}
	
	@Override
	void accept(ShoppingCart cart) {
		cart.visit(this);
	}

}
