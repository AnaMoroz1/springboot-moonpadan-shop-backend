package lt.ca.javau10.moonpadan.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ca.javau10.moonpadan.entities.Product;

public interface ProductRepository extends JpaRepository < Product, Long> {
	 Optional<Product> findByName(String name);
}
