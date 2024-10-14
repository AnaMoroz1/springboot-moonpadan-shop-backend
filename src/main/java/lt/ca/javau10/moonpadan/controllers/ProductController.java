package lt.ca.javau10.moonpadan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.moonpadan.entities.Product;
import lt.ca.javau10.moonpadan.services.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Gauti visų produktų sąrašą
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    //http://localhost:8080/api/products/search?name=Tweezers
    // 2. Gauti vieną produktą pagal pavadinimą
    @GetMapping("/search")
    public ResponseEntity<Product> getProductByName(@RequestParam String name) {
    	System.out.println("all good " + name);
        Optional<Product> product = productService.findByName(name);
        
        if (product.isPresent()) {
            System.out.println("Produktas rastas: " + product.get().getName());
        } else {
            System.out.println("Produktas su pavadinimu " + name + " nerastas.");
        }
        
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // 3. Gauti vieną produktą pagal ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // 4. Pridėti naują produktą
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // 5. Ištrinti produktą pagal ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Atnaujinti produktą pagal ID (Patch)
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }
}
