public enum Product {
    WATER(1, 5, "Water"),
    COCA(2, 7, "Coca"),
    TWIX(3, 10, "Twix"),
    BUENO(4, 12, "Bueno");

    public String label;
    public int price;
    public int id;

    Product(int id, int price, String name) {
        this.label = name;
        this.price = price;
        this.id = id;
    }
}
