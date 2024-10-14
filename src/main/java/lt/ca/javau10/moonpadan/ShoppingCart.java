package lt.ca.javau10.moonpadan;

import java.util.ArrayList;
import java.util.List;

import lt.ca.javau10.moonpadan.entities.Product;

public class ShoppingCart {

    private List<CartItem> items = new ArrayList<>();

    public ShoppingCart() {}

    // Pridėti produktą į krepšelį
    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity); // atnaujinti kiekį
                return;
            }
        }
        items.add(new CartItem(product, quantity)); // pridėti naują prekę
    }

    // Pašalinti produktą iš krepšelio
    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }

    // Gauti visus elementus iš krepšelio
    public List<CartItem> getItems() {
        return items;
    }
    // sumažinti prekiu kieki krepšelyje
    public void decreaseProductQuantity(Product product) {
    	for (CartItem item : items) {
    		if (item.getProduct().getId().equals(product.getId())) {
    			int currentQuantity = item.getQuantity();
    			if (currentQuantity > 1) {
    				item.setQuantity( -1);
    			} else {
    				items.remove(item);
    			}
    			return;
    		}
    	}
    }

    // Išvalyti krepšelį
    public void clearCart() {
        items.clear();
    }

    // Skaičiuoti bendra kaina
    public double getTotalPrice() {
        return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }

}
