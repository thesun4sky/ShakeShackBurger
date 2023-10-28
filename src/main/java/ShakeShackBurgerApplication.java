import java.util.List;
import java.util.Scanner;

public class ShakeShackBurgerApplication {
	// 메뉴 구성
	// 메인메뉴(홈) - 메뉴 - 상품메뉴

	private static MenuContext menuContext; // 메뉴 정보를 가지고 있는 컨텍스트 (컨텍스트? 문맥 = 현재 상태를 저장하는 공간)

	public static void main(String[] args) {
		menuContext = new MenuContext();
		displayMainMenu();
	}

	/**
	 * 메인메뉴 출력
	 */
	private static void displayMainMenu() {
		System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
		System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n");

		System.out.println("[ SHAKESHACK MENU ]");
		List<Menu> mainMenus = menuContext.getMenus("Main");	// 메뉴 컨텍스트에서 메인메뉴 조회
		int nextNum = printMenu(mainMenus, 1);					// 메인메뉴 출력

		System.out.println("[ ORDER MENU ]");
		List<Menu> orderMenus = menuContext.getMenus("Order");	// 메뉴 컨텍스트에서 주문메뉴 조회
		printMenu(orderMenus, nextNum);								// 주문메뉴 출력

		handleMainMenuInput(); // 메인메뉴 입력처리
	}

	/**
	 * 메뉴 목록 출력
	 * @param menus : 출력할 메뉴 리스트
	 * @param num : 출력중인 전체 순번
	 * @return 출력 후 전체 순번
	 */
	private static int printMenu(List<Menu> menus, int num) {
		for (int i=0; i<menus.size(); i++, num++) {		// menus 목록에 있는 메뉴 출력 (전체 순번값인 num 값도 ++)
			System.out.println(num + ". " + menus.get(i).name + "   | " + menus.get(i).description); // ex. 0.메뉴이름 | 메뉴설명
		}
		return num;
	}

	/**
	 * 메인메뉴 입력처리
	 */
	private static void handleMainMenuInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		int mainMenuSize = menuContext.getMenus("Main").size();		// 메인메뉴 사이즈 조회
		int orderMenuSize = menuContext.getMenus("Order").size();	// 주문메뉴 사이즈 조회

		if (0 < input && input <= mainMenuSize) {		// 입력 번호가 메인메뉴 사이즈 안이면 = 메인메뉴
			displayMenu(menuContext.getMenus("Main").get(input - 1));	// 메인메뉴 출력
		} else if (mainMenuSize < input && input <= mainMenuSize + orderMenuSize) {	// 입력 번호가 메인메뉴+주문메뉴 사이트 안이면 = 주문메뉴
			if (input == mainMenuSize + 1) {
				displayOrderMenu();							// 주문메뉴1. 주문진행 메뉴 출력
			} else if (input == mainMenuSize + 2) {
				handleCancelMenuInput();					// 주문메뉴2. 주문취소 메뉴 출력
			}
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleMainMenuInput();
		}
	}

	/**
	 * 메뉴에 있는 상품메뉴 목록 출력
	 * @param menu 출력할 메뉴
	 */
	private static void displayMenu(Menu menu) {
		System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
		System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.\n");

		System.out.println("[ " + menu.name + " MENU ]");
		List<Item> items = menuContext.getMenuItems(menu.name); // 메뉴에 있는 상품메뉴 목록 조회
		printMenuItems(items);			// 상품메뉴 목록 출력

		handleMenuItemInput(items);		// 상품메뉴 입력 처리
	}

	/**
	 * 상품메뉴 목록 출력
	 * @param items 출력할 상품메뉴 목록
	 */
	private static void printMenuItems(List<Item> items) {
		for (int i=0; i<items.size(); i++) {
			int num = i + 1;
			System.out.println(num + ". " + items.get(i).name + "   | " + items.get(i).price + " | " + items.get(i).description); // | 구분자로 출력
		}
	}

	/**
	 * 상품메뉴 입력 처리
	 * @param items 입력처리 할 상품메뉴 목록
	 */
	private static void handleMenuItemInput(List<Item> items) {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input >= 1 && input <= items.size()) { 	// 입력값 유효성 검증
			Item selectedItem = items.get(input-1);	// 선택한 상품메뉴 조회
			displayConfirmation(selectedItem);		// 선택한 상품메뉴 확인 문구 출력
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleMenuItemInput(items);	// 상품메뉴 입력 처리 재수행
		}
	}

	/**
	 * 선택한 상품메뉴 확인 문구 출력
	 * @param menuItem 선택한 상품메뉴
	 */
	private static void displayConfirmation(Item menuItem) {
		System.out.println(menuItem.name + "   | " + menuItem.price + " | " + menuItem.description);
		System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
		System.out.println("1. 확인        2. 취소");

		handleConfirmationInput(menuItem);	// 확인여부 입력 처리
	}

	/**
	 * 확인여부 입력 처리
	 * @param menuItem 확인한 상품메뉴
	 */
	private static void handleConfirmationInput(Item menuItem) {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {								// 1. 확인 입력시
			menuContext.addToCart(menuItem);			// 선택한 상품을 컨텍스트의 장바구니에 추가
			System.out.println("장바구니에 추가되었습니다.");
			displayMainMenu();							// 메인메뉴 출력하며 처음으로 돌아가기
		} else if (input == 2) {						// 2. 취소 입력시
			displayMainMenu();							// 바로 메인메뉴 출력하며 처음으로 돌아가기
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleConfirmationInput(menuItem);			// 잘못된 입력시 다시 확인여부 입력 처리 재수행
		}
	}

	/**
	 * 주문메뉴1. 주문진행 메뉴 출력
	 */
	private static void displayOrderMenu() {
		System.out.println("아래와 같이 주문 하시겠습니까?\n");
		menuContext.displayCart();			// 컨텍스트에서 장바구니 목록 출력

		System.out.println("[ Total ]");
		System.out.println("W " + menuContext.getTotalPrice() + "\n");	// 컨텍스트에서 전체 가격 조회하여 출력

		System.out.println("1. 주문      2. 메뉴판");

		handleOrderMenuInput();				// 주문진행 입력 처리
	}

	/**
	 * 주문메뉴1. 주문진행 입력 처리
	 */
	private static void handleOrderMenuInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {
			displayOrderComplete();	// 1. 주문 입력시 주문완료 처리
		} else if (input == 2) {
			displayMainMenu();		// 2. 메뉴판 입력시 메인메뉴 출력하며 돌아가기
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleOrderMenuInput();	// 잘못된 입력시 주문진행 입력처리 재수행
		}
	}

	/**
	 * 주문메뉴1. 주문 입력시 주문완료 처리
	 */
	private static void displayOrderComplete() {
		int orderNumber = menuContext.generateOrderNumber();		// 컨텍스트에서 신규 주문번호 조회
		System.out.println("주문이 완료되었습니다!\n");
		System.out.println("대기번호는 [ " + orderNumber + " ] 번 입니다.");

		resetCartAndDisplayMainMenu();		// 장바구니 초기화 후 메인메뉴 출력
	}

	/**
	 * 장바구니 초기화 후 메인메뉴 출력
	 */
	private static void resetCartAndDisplayMainMenu() {
		menuContext.resetCart();		// 컨텍스트에서 장바구니 초기화
		System.out.println("(3초후 메뉴판으로 돌아갑니다.)");
		try {
			Thread.sleep(3000); // 3초 대기
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		displayMainMenu();		// 메인메뉴 출력하며 돌아가기
	}

	/**
	 * 주문메뉴2. 주문취소 메뉴 출력
	 */
	private static void handleCancelMenuInput() {
		System.out.println("주문을 취소하시겠습니까?");
		System.out.println("1. 확인        2. 취소");

		handleCancelConfirmationInput();	// 주문취소 확인 입력값 처리
	}

	/**
	 * 주문메뉴2. 주문취소 확인 입력값 처리
	 */
	private static void handleCancelConfirmationInput() {
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		if (input == 1) {
			menuContext.resetCart();	// 장바구니 초기화
			System.out.println("주문이 취소되었습니다.");
			displayMainMenu();			// 메인메뉴 출력하며 돌아가기
		} else if (input == 2) {
			displayMainMenu();			// 메인메뉴 출력하며 돌아가기
		} else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			handleCancelConfirmationInput();	// 주문취소 확인 입력값 처리 재수행
		}
	}
}

