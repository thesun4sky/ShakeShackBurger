import java.util.List;
import java.util.Scanner;

public class ShakeShackBurgerApplication {
	private static MenuContext menuContext;
	private static ManagementContext managementContext;

	public static void main(String[] args) {
		menuContext = new MenuContext();
		managementContext = new ManagementContext();
		displayMainMenu();
	}

	private static void displayMainMenu() {
		System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
		System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n");

		System.out.println("[ SHAKESHACK MENU ]");
		List<Menu> mainMenus = menuContext.getMenus("Main");
		printMenu(mainMenus);

		System.out.println("[ ORDER MENU ]");
		List<Menu> orderMenus = menuContext.getMenus("Order");
		printMenu(orderMenus);

		handleMainMenuInput();
	}

	private static void printMenu(List<Menu> menus) {
		for (int i=0; i<menus.size(); i++) {
			System.out.println(menus.get(i).id + ". " + menus.get(i).name + "   | " + menus.get(i).description);
		}
	}

	private static void handleMainMenuInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		int mainMenuSize = menuContext.getMenus("Main").size();
		int orderMenuSize = menuContext.getMenus("Order").size();

		if (input == 0) {
			displayManagementMenu();
		} else if (input <= mainMenuSize) {
			displayMenu(menuContext.getMenus("Main").get(input - 1));
		} else if (input <= mainMenuSize + orderMenuSize) {
			int orderInput = input - mainMenuSize;
			switch (orderInput) {
				case 1:
					displayOrderMenu();
					break;
				case 2:
					handleCancelMenuInput();
					break;
				case 3:
					handleListMenuInput();
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					handleMainMenuInput();
			}
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleMainMenuInput();
		}
	}

	private static void displayMenu(Menu menu) {
		System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
		System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.\n");

		System.out.println("[ " + menu.name + " MENU ]");
		List<Item> items = menuContext.getMenuItems(menu.name);
		printMenuItems(items);

		handleMenuItemInput(items);
	}

	private static void displayManagementMenu() {
		System.out.println("SHAKESHACK BURGER 관리 메뉴에 오신걸 환영합니다.");
		System.out.println("아래 목록해서 원하는 명령을 골라 입력해주세요.\n");

		managementContext.displayMainMenu();

		handleCommandInput();
	}

	private static void handleCommandInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 0) {
			displayMainMenu();
		} else if (input >= 1 && input <= 4) {
			switch (input) {
				case 1:
					managementContext.displayWaitingOrdersAndProcess();
					break;
				case 2:
					managementContext.displayCompletedOrders();
					break;
				case 3:
					String menuName = getMenuName();
					Item newItem = managementContext.createMenuItem();
					menuContext.addMenuItem(menuName, newItem);
					break;
				case 4:
					menuContext.displayAllItem();
					System.out.print("삭제할 상품 ID: ");
					int itemId = scanner.nextInt();
					managementContext.deleteMenuItems(menuContext.getMenuItemMap(), itemId);
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		}
		displayManagementMenu();
	}

	private static String getMenuName() {
		System.out.println("[ 메뉴 목록 ]");
		List<Menu> mainMenus = menuContext.getMenus("Main");
		printMenu(mainMenus);
		System.out.print("메뉴 ID: ");
		Scanner scanner = new Scanner(System.in);
		int menuId = scanner.nextInt();
		return menuContext.getMainMenuName(menuId);
	}

	private static void handleMenuItemInput(List<Item> items) {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input >= 1 && input <= items.size()) {
			Item selectedItem = items.get(input-1);
			displayConfirmation(selectedItem);
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleMenuItemInput(items);
		}
	}

	private static void printMenuItems(List<Item> items) {
		for (int i=0; i<items.size(); i++) {
			int num = i + 1;
			System.out.println(num + ". " + items.get(i).name + "   | " + items.get(i).price + " | " + items.get(i).description);
		}
	}

	private static void displayConfirmation(Item menuItem) {
		System.out.println(menuItem.name + "   | " + menuItem.price + " | " + menuItem.description);
		System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
		System.out.println("1. 확인        2. 취소");

		handleConfirmationInput(menuItem);
	}

	private static void handleConfirmationInput(Item menuItem) {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {
			menuContext.addToCart(menuItem);
			System.out.println("장바구니에 추가되었습니다.");
			displayMainMenu();
		} else if (input == 2) {
			displayMainMenu();
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleConfirmationInput(menuItem);
		}
	}

	private static void displayOrderMenu() {
		System.out.println("아래와 같이 주문 하시겠습니까?\n");
		menuContext.displayCart();

		System.out.println("[ Total ]");
		System.out.println("W " + menuContext.getTotalPrice() + "\n");

		System.out.println("1. 주문      2. 메뉴판");

		handleOrderMenuInput();
	}

	private static void handleOrderMenuInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {
			displayOrderComplete();
		} else if (input == 2) {
			displayMainMenu();
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleOrderMenuInput();
		}
	}

	private static void displayOrderComplete() {
		int orderNumber = menuContext.generateOrderNumber();
		List<Item> cart = menuContext.getCart();
		Double totalPrice = menuContext.getTotalPrice();

		System.out.println("주문이 완료되었습니다!\n");
		System.out.println("대기번호는 [ " + orderNumber + " ] 번 입니다.");

		managementContext.addCartToOrder(orderNumber, cart, totalPrice);

		resetCartAndDisplayMainMenu();
	}

	private static void resetCartAndDisplayMainMenu() {
		menuContext.resetCart();
		System.out.println("(3초후 메뉴판으로 돌아갑니다.)");
		try {
			Thread.sleep(3000); // 3초 대기
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		displayMainMenu();
	}

	private static void handleCancelMenuInput() {
		System.out.println("주문을 취소하시겠습니까?");
		System.out.println("1. 확인        2. 취소");

		handleCancelConfirmationInput();
	}

	private static void handleListMenuInput() {
		managementContext.displayWaitingOrders();
		managementContext.displayCompletedOrders();

		displayMainMenu();
	}
	private static void handleCancelConfirmationInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {
			menuContext.resetCart();
			System.out.println("주문이 취소되었습니다.");
			displayMainMenu();
		} else if (input == 2) {
			displayMainMenu();
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleCancelConfirmationInput();
		}
	}
}

