package supermarket;

public class StrategyA implements Strategy{

	@Override
	public Item execute(WishList wishList) {
		//find the minimum price item
		Item minItem = wishList.head.getItem();
		float minPrice = wishList.head.getItem().getPrice();
		for(int i = 1 ; i < wishList.size; i++) {
			if(wishList.getItem(i).getPrice() < minPrice) {
				minPrice = wishList.getItem(i).getPrice();
				minItem = wishList.getItem(i);
			}
		}
		return minItem;
	}

}
