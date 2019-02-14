package supermarket;

import java.util.Date;

public class StrategyC implements Strategy{

	@Override
	public Item execute(WishList wishList) {
		//find the minimum price item
		Item minItem = wishList.head.getItem();
		Date mostRecent = wishList.getDate(0);
		for(int i = 1 ; i < wishList.size; i++) {
			if(wishList.getDate(i).compareTo(mostRecent) > 0) {
				mostRecent = wishList.getDate(i);
				minItem = wishList.getItem(i);
			}
		}
		return minItem;
	}

}
