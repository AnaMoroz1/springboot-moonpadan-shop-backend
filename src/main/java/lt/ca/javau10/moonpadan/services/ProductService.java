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

    // Gauti visus produktus
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    

    // Gauti produktą pagal ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Gauti produktą pagal pavadinimą (paieškos parametrą)
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    // Pridėti naują produktą
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
 // Pridėti naują produktą
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Atnaujinti produktą
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

    // Ištrinti produktą pagal ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


	public Optional<Product> findByName(String name) {
			return productRepository.findByName(name);
	}
}