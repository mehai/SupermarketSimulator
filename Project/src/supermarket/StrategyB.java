package supermarket;

public class StrategyB implements Strategy{

	@Override
	public Item execute(WishList wishList) {
		//return the first item
		return wishList.getItem(0);
	}

}
