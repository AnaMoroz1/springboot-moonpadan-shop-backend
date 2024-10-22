package lt.ca.javau10.moonpadan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lt.ca.javau10.moonpadan.entities.ERole;
import lt.ca.javau10.moonpadan.entities.Role;
import lt.ca.javau10.moonpadan.repositories.RoleRepository;

@Component
public class DbSeeder implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(DbSeeder.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String...args) throws Exception {
		if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            logger.info("ROLE_ADMIN added.");
        }
        
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            logger.info("ROLE_USER added.");
        }
        if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
            logger.info("ROLE_MODERATOR added.");
        }

	}
	
}
