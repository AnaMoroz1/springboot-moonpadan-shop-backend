package lt.ca.javau10.moonpadan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.moonpadan.CartItem;
import lt.ca.javau10.moonpadan.CartItemDto;
import lt.ca.javau10.moonpadan.ShoppingCart;
import lt.ca.javau10.moonpadan.entities.Product;
import lt.ca.javau10.moonpadan.services.ProductService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000") // React front-end URL
public class ShoppingCartController {

    private ShoppingCart shoppingCart = new ShoppingCart();

    @Autowired
    private ProductService productService; // Added `ProductService` injection

    // Add product to cart
    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody CartItemDto cartItemDto) {
        Optional<Product> productOptional = productService.getProductById(cartItemDto.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            shoppingCart.addProduct(product, cartItemDto.getQuantity()); // The correct object is used
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build(); // if product not found
        }
    }

    // Remove product from cart
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProduct(@RequestBody Product product) {
        shoppingCart.removeProduct(product);
        return ResponseEntity.noContent().build();
    }

    @PostMapping ("/decrease")
    public ResponseEntity<Void> decreaseProductQuanntyti(@RequestBody CartItemDto request){
    	Product product = getProductById(request.getProductId());
    	shoppingCart.decreaseProductQuantity(product);
    	return ResponseEntity.ok().build();
    }
    
    private Product getProductById(Long productId) {
		
		return new Product(productId, "Sample product",20.0);
	}

    // Get all items from cart
    @GetMapping
    public List<CartItem> getCartItems() {
        return shoppingCart.getItems();
    }

    // Get the total price
    @GetMapping("/total")
    public double getTotalPrice() {
        return shoppingCart.getTotalPrice();
    }
}
