import java.util.*;
import java.io.*;

class InventoryManager {

    Scanner scanner = new Scanner(System.in);
    ArrayList<Product> products = new ArrayList<>();

    String fileName = "products.txt";

    InventoryManager() {
        loadProducts();
    }

    void loadProducts() {

        try {

            File file = new File(fileName);

            if (!file.exists()) {
                return;
            }

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int quantity = Integer.parseInt(data[2]);
                double price = Double.parseDouble(data[3]);

                products.add(new Product(id, name, quantity, price));
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading products.");
        }
    }

    void saveProducts() {

        try {

            PrintWriter writer = new PrintWriter(new FileWriter(fileName));

            for (Product product : products) {
                writer.println(product.toFileString());
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving products.");
        }
    }

    void add() {

        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        products.add(new Product(id, name, quantity, price));

        saveProducts();

        System.out.println("Product added successfully.");
    }

    void view() {

        if (products.isEmpty()) {
            System.out.println("No products stored.");
            return;
        }

        System.out.println("ID | Name | Quantity | Price");

        for (Product product : products) {
            product.display();
        }
    }

    void search() {

        scanner.nextLine();
        System.out.print("Enter product name: ");
        String search = scanner.nextLine();

        boolean found = false;

        for (Product product : products) {

            if (product.name.equalsIgnoreCase(search)) {
                product.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found.");
        }
    }

    void update() {

        scanner.nextLine();
        System.out.print("Enter product name to update: ");
        String name = scanner.nextLine();

        boolean found = false;

        for (Product product : products) {

            if (product.name.equalsIgnoreCase(name)) {

                System.out.print("Enter new quantity: ");
                product.quantity = scanner.nextInt();

                System.out.print("Enter new price: ");
                product.price = scanner.nextDouble();

                saveProducts();

                System.out.println("Product updated.");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found.");
        }
    }

    void remove() {

        scanner.nextLine();
        System.out.print("Enter product name to delete: ");
        String name = scanner.nextLine();

        Iterator<Product> iterator = products.iterator();

        boolean removed = false;

        while (iterator.hasNext()) {

            Product product = iterator.next();

            if (product.name.equalsIgnoreCase(name)) {

                iterator.remove();
                removed = true;
            }
        }

        if (removed) {
            saveProducts();
            System.out.println("Product deleted.");
        } else {
            System.out.println("Product not found.");
        }
    }
}