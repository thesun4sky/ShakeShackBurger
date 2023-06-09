class Menu {
	static int idSeq = 1;

	int id;
	String name;
	String description;

	Menu(String name, String description) {
		this.id = idSeq++;
		this.name = name;
		this.description = description;
	}
}
