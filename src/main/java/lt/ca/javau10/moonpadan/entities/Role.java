package lt.ca.javau10.moonpadan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table (name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//will be written as text value, not numbers(index)
	@Enumerated(EnumType.STRING)
	private ERole name;
	
	public Role() {
		
	}

	public Role(ERole name) {
		this.name = name;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name.toString();
	}

	public void setName(ERole name) {
		this.name = name;
	}

	
	
	
}
