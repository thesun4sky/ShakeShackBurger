import java.time.LocalDateTime;
import java.util.List;

public class Order {
	int orderNumber;
	List<Item> cart;
	Double totalPrice;
	Boolean complete = false;
	LocalDateTime orderDate;
	LocalDateTime completeDate;

	public Order(int orderNumber, List<Item> cart, Double totalPrice) {
		this.orderNumber = orderNumber;
		this.cart = cart;
		this.totalPrice = totalPrice;
		this.orderDate = LocalDateTime.now();
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
		this.completeDate = LocalDateTime.now();
	}

	public void display() {
		System.out.println("주문번호 : " + this.orderNumber);
		System.out.println("주문시각 : " + this.orderDate);
		for (Item item: cart) {
			System.out.println(item.name + "   | " + item.price + " | " + item.description);
		}
		System.out.println("총 가격 : " + this.totalPrice);
		System.out.println();
	}
}
