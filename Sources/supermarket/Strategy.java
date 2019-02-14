package supermarket;

public interface Strategy {
	abstract Item execute(WishList wishList);
}
