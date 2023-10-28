public class Item extends Menu {

	Double price;	// 상품 가격

	Item(String name, Double price, String description) {
		super(name, description);
		this.price = price;
	}
}
