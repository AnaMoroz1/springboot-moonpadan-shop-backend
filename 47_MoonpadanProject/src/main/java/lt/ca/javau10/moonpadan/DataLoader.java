package lt.ca.javau10.moonpadan;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lt.ca.javau10.moonpadan.entities.Attribute;
import lt.ca.javau10.moonpadan.entities.Product;
import lt.ca.javau10.moonpadan.repositories.ProductRepository;


@Component
public class DataLoader implements CommandLineRunner {
	

    private ProductRepository productRepository;
	
	public DataLoader (ProductRepository productRepository) {
		this.productRepository = productRepository;
		
	}

	    @Override
	    public void run(String... args) throws Exception {

	    	if(productRepository.count() > 0 )
	    		return;
	    	
	        Product product1 = new Product("Moonpadan Eyelashes", "Eyelashes", "Moonpadan series", 15.99);
	        product1.setAttributes(Arrays.asList(
	            new Attribute("length", "mix"),
	            new Attribute("thick", "0.07"),
	            new Attribute("color", "black"),
	            new Attribute("curlType", "C,D")
	        ));

	        Product product2 = new Product("Aurum Eyelashes", "Eyelashes", "Aurum series", 13.99);
	        product2.setAttributes(Arrays.asList(
	            new Attribute("length", "mix"),
	            new Attribute("thick", "0.15"),
	            new Attribute("color", "black"),
	            new Attribute("curlType", "C,D")
	        ));

	        Product product3 = new Product("Eyelash Brushes", "Brushes", "For eyelash combing", 3.99);
	        Product product4 = new Product("Eyelash Glue", "Glue", "For eyelash extensions", 32.99);
	        Product product5 = new Product("Tweezers", "Tools", "For eyelash extensions", 8.99);

	        addProductIfNotExists(product1);
	        addProductIfNotExists(product2);
	        addProductIfNotExists(product3);
	        addProductIfNotExists(product4);
	        addProductIfNotExists(product5);
	    }


	    // Pridėti produktą tik tuo atveju, jei jo dar nėra
	    private void addProductIfNotExists(Product product) {
	        Optional<Product> existingProduct = productRepository.findByName(product.getName());
	        if (existingProduct.isEmpty()) {
	            productRepository.save(product);
	            System.out.println("Product '" + product.getName() + "' was added to the database.");
	        } else {
	            System.out.println("Product '" + product.getName() + "' already exists in the database.");
	        
	    }
	}
}