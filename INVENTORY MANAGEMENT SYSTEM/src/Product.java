class Product {

    int id;
    String name;
    int quantity;
    double price;

    Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    void display() {
        System.out.println(id + " | " + name + " | " + quantity + " | " + price);
    }

    String toFileString() {
        return id + "," + name + "," + quantity + "," + price;
    }
}