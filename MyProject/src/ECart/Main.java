package ECart;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Product[] products = {
            new Product(1, "Hoodies", 899),
            new Product(2, "Trousers", 1999),
            new Product(3, "Sneakers", 3999),
            new Product(4, "Cap", 1599)
        };

        Cart cart = new Cart();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- E-Commerce Menu ---");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. Remove from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    for (Product p : products) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Enter Product ID to add: ");
                    int addId = sc.nextInt();
                    if (addId > 0 && addId <= products.length) {
                        cart.addProduct(products[addId - 1]);
                    } else {
                        System.out.println("Invalid ID.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Product ID to remove: ");
                    int remId = sc.nextInt();
                    cart.removeProduct(remId);
                    break;
                case 4:
                    cart.viewCart();
                    break;
                case 5:
                    cart.viewCart();
                    cart.calculateTotal();
                    System.out.println("Thanks for shopping!");
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        sc.close();
    }
}

