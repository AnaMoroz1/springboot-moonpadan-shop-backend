package lt.ca.javau10.moonpadan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ca.javau10.moonpadan.entities.Product;
import lt.ca.javau10.moonpadan.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

 // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    

 // Get the product by id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

 // Get product by name (search parameter)
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

 // Add new product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
 // Save the new product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
 // Delete product by ID
    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
 // Update the product
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setCategory(updatedProduct.getCategory());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setAttributes(updatedProduct.getAttributes());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

 
	public Optional<Product> findByName(String name) {
			return productRepository.findByName(name);
	}
	
}