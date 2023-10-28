class Menu {
	static int idSeq = 1;

	int id;
	String name;		// 메뉴 이름
	String description;	// 메뉴 설명

	Menu(String name, String description) {
		this.id = idSeq++;
		this.name = name;
		this.description = description;
	}
}
