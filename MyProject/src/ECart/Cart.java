package ECart;

import java.util.ArrayList;

public class Cart {
    ArrayList<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
        System.out.println(product.name + " added to cart.");
    }

    public void removeProduct(int id) {
        for (Product p : items) {
            if (p.id == id) {
                items.remove(p);
                System.out.println(p.name + " removed from cart.");
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Items in Cart:");
        for (Product p : items) {
            System.out.println(p);
        }
    }

    public void calculateTotal() {
        double total = 0;
        for (Product p : items) {
            total += p.price;
        }
        System.out.println("Total: ₹" + total);
    }
}

