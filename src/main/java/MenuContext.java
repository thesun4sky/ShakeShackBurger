import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MenuContext {
	private Map<String, List<Menu>> menus;	// 메뉴
	private Map<String, List<Item>> menuItems;	// 상품메뉴
	private List<Item> cart;	// 장바구니
	private double totalPrice;	// 전체 가격
	private int orderNumber;	// 주문 번호

	public MenuContext() {
		menus = new HashMap<>();
		menuItems = new HashMap<>();
		cart = new ArrayList<>();
		totalPrice = 0.0;
		orderNumber = 0;

		initializeMenuItems();	// 메뉴 및 상품메뉴 초기화
	}

	private void initializeMenuItems() {
		List<Menu> mainMenus = new ArrayList<>();
		mainMenus.add(new Menu("Burgers", "앵거스 비프 통살을 다져만든 버거"));
		mainMenus.add(new Menu("Frozen Custard", "매장에서 신선하게 만드는 아이스크림"));
		mainMenus.add(new Menu("Drinks", "매장에서 직접 만드는 음료"));
		mainMenus.add(new Menu("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주"));

		List<Menu> orderMenus = new ArrayList<>();
		orderMenus.add(new Menu("Order", "장바구니를 확인 후 주문합니다."));
		orderMenus.add(new Menu("Cancel", "진행중인 주문을 취소합니다."));
		orderMenus.add(new Menu("Order List", "대기/완료된 주문목록을 조회합니다."));

		menus.put("Main", mainMenus);
		menus.put("Order", orderMenus);

		List<Item> burgersMenus = new ArrayList<>();
		burgersMenus.add(new Item("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
		burgersMenus.add(new Item("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
		burgersMenus.add(new Item("Shroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"));
		burgersMenus.add(new Item("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
		burgersMenus.add(new Item("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));

		List<Item> frozenCustardMenu = new ArrayList<>();
		frozenCustardMenu.add(new Item("Frozen Custard Menu Item 1", 1.4, "Frozen Custard Menu Item 1 설명"));
		frozenCustardMenu.add(new Item("Frozen Custard Menu Item 2", 1.0, "Frozen Custard Menu Item 2 설명"));
		frozenCustardMenu.add(new Item("Frozen Custard Menu Item 3", 1.6, "Frozen Custard Menu Item 3 설명"));
		frozenCustardMenu.add(new Item("Frozen Custard Menu Item 4", 2.1, "Frozen Custard Menu Item 4 설명"));

		List<Item> drinksMenu = new ArrayList<>();
		drinksMenu.add(new Item("Drinks Menu Item 1", 1.0, "Drinks Menu Item 1 설명"));
		drinksMenu.add(new Item("Drinks Menu Item 2", 1.0, "Drinks Menu Item 2 설명"));

		List<Item> beerMenu = new ArrayList<>();
		beerMenu.add(new Item("Beer Menu Item 1", 3.0, "Beer Menu Item 1 설명"));
		beerMenu.add(new Item("Beer Menu Item 2", 4.0, "Beer Menu Item 2 설명"));

		menuItems.put("Burgers", burgersMenus);
		menuItems.put("Frozen Custard", frozenCustardMenu);
		menuItems.put("Drinks", drinksMenu);
		menuItems.put("Beer", beerMenu);
	}

	/**
	 * 메뉴 조회
	 * @param key 조회할 메뉴 키값
	 * @return 조회된 메뉴 목록
	 */
	public List<Menu> getMenus(String key) {
		return menus.get(key);
	}

	/**
	 * 상품메뉴 조회
	 * @param key 조회할 상품메뉴 키값
	 * @return 조회된 상품메뉴 목록
	 */
	public List<Item> getMenuItems(String key) {
		return menuItems.get(key);
	}

	public Map<String, List<Item>> getMenuItemMap() {
		return menuItems;
	}

	public List<Item> getCart() {
		return cart;
	}

	public void addMenu(String key, String description) {
		menus.get("Main").add(new Menu(key, description));
		menuItems.put(key, new ArrayList<>());
	}

	public void addMenuItem(String key, Item newItem) {
		menuItems.get(key).add(newItem);
	}

	public String getMainMenuName(int id) {
		List<Menu> mainMenus = menus.get("Main");
		for (Menu mainMenu : mainMenus) {
			if (mainMenu.id == id) {
				return mainMenu.name;
			}
		}
		return "";
	}

	/**
	 * 장바구니에 상품메뉴 추가
	 * @param menuItem 장바구니에 추가할 상품메뉴
	 */
	public void addToCart(Item menuItem) {
		cart.add(menuItem);
		totalPrice += menuItem.price;
	}

	/**
	 * 장바구니 출력
	 */
	public void displayAllItem() {
		System.out.println("[ 전체 상품 목록 ]");
		menuItems.forEach((key, value) -> {
			System.out.println(" [ " + key + " Menu ]");
			for(Item item: value) {
				System.out.println(item.id + ". " + item.name + "   | " + item.price + " | " + item.description);
			}
		});
	}

	public void displayCart() {
		for (Item item : cart) {
			System.out.println(item.name + "   | " + item.price + " | " + item.description);
		}
	}

	/**
	 * 장바구니 전체가격 조회
	 * @return 장바구니 전체가격
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 신규 주문번호 조회
	 * @return 신규 주문번호
	 */
	public int generateOrderNumber() {
		orderNumber++;
		return orderNumber;
	}

	/**
	 * 장바구니 초기화
	 */
	public void resetCart() {
		cart.clear();
		totalPrice = 0.0;
	}
}