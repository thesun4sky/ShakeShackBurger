import java.util.List;

public class Order {
	int orderNumber;
	List<Item> cart;
	Boolean complete = false;

	public Order(int orderNumber, List<Item> cart) {
		this.orderNumber = orderNumber;
		this.cart = cart;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public List<Item> getCart() {
		return this.cart;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public void display() {
		System.out.println("주문번호 : " + this.orderNumber);
		for (Item item: cart) {
			System.out.println(item.name + "   | " + item.price + " | " + item.description);
		}
	}
}
