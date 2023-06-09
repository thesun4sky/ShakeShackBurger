import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ManagementContext {

	private List<Order> orderList = new ArrayList<>();

	public void displayMainMenu() {
		System.out.println("0. 메인 메뉴");
		System.out.println("1. 대기주문 목록");
		System.out.println("2. 완료주문 목록");
		System.out.println("3. 상품 생성");
		System.out.println("4. 상품 삭제");
	}

	public void addCartToOrder(int orderNumber, List<Item> cart) {
		List<Item> orderItemList = new ArrayList<>(cart);
		orderList.add(new Order(orderNumber, orderItemList));
	}

	public void displayWaitingOrders() {
		System.out.println("대기 주문 목록:");
		for (Order order : orderList) {
			if (!order.complete) {
				order.display();
			}
		}

		System.out.println();
		System.out.println("대기중인 주문을 처리하시겠습니까?");
		System.out.println("1. 확인        2. 취소");
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {
			processWaitingOrder();
		}
	}

	public void processWaitingOrder() {
		if (orderList.isEmpty()) {
			System.out.println("대기 주문이 없습니다.");
			return;
		}

		System.out.print("완료할 주문 번호: ");
		Scanner scanner = new Scanner(System.in);
		int orderNumber = scanner.nextInt();
		scanner.nextLine();

		Order orderToComplete = null;
		for (Order order : orderList) {
			if (order.getOrderNumber() == orderNumber) {
				order.setComplete(true);
				orderToComplete = order;
				break;
			}
		}

		if (orderToComplete != null) {
			System.out.println("처리이 완료되었습니다.");
		} else {
			System.out.println("주문 번호를 찾을 수 없습니다.");
		}
		System.out.println();
	}

	public void displayCompletedOrders() {
		System.out.println("완료 주문 목록:");
		for (Order order : orderList) {
			if (order.complete) {
				order.display();
			}
		}
		System.out.println();
		System.out.println("(3초후 메뉴판으로 돌아갑니다.)");
		try {
			Thread.sleep(3000); // 3초 대기
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Item createMenuItem() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("이름: ");
		String name = scanner.nextLine();

		System.out.print("설명: ");
		String description = scanner.nextLine();

		System.out.print("가격: ");
		double price = scanner.nextDouble();

		System.out.println("상품이 생성되었습니다.");
		System.out.println();

		return new Item(name, price, description);
	}

	public void deleteMenuItems(Map<String, List<Item>> menuItems, int itemId) {
		menuItems.forEach((key, value) -> {
			int removeIndex = -1;
			for(int i=0; i<value.size(); i++) {
				if (value.get(i).id == itemId) {
					removeIndex = i;
				}
			}
			if (removeIndex > -1) {
				value.remove(removeIndex);
			}
		});
		System.out.println("상품이 삭제되었습니다.");
	}
}
