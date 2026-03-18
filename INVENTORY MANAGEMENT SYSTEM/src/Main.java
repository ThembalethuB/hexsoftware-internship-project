import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        InventoryManager inventory = new InventoryManager();

        int choice;

        do {

            System.out.println("\n====== INVENTORY MANAGEMENT SYSTEM ======\n");

            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    inventory.add();
                    break;

                case 2:
                    inventory.view();
                    break;

                case 3:
                    inventory.search();
                    break;

                case 4:
                    inventory.update();
                    break;

                case 5:
                    inventory.remove();
                    break;

                case 6:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);
    }
}