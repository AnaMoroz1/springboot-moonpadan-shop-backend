package lt.ca.javau10.moonpadan.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String name;
	    private String category;
	    private String description;
	    private double price;
	    
	    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Attribute> attributes = new ArrayList<>();

	    public Product() {}

	    public Product(String name, String category, String description, double price) {
	        this.name = name;
	        this.category = category;
	        this.description = description;
	        this.price = price;
	        
	     
	    }

		public Product(Long productId, String string, double d) {
			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public List<Attribute> getAttributes() {
			return attributes;
		}

		public void setAttributes(List<Attribute> attributes) {
			this.attributes = attributes;
		}

}

