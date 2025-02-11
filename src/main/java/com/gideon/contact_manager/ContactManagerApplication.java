package com.gideon.contact_manager;

import com.gideon.contact_manager.domain.model.Role;
import com.gideon.contact_manager.domain.model.User;
import com.gideon.contact_manager.domain.repository.UserRepository;
import com.gideon.contact_manager.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class ContactManagerApplication implements CommandLineRunner {

	@Autowired
	private JpaUserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ContactManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var user =  userRepository.findByRole(Role.ADMIN);
		if(user.isPresent()) {
			User adminUser = new User();
			adminUser.setRole(Role.ADMIN);
			adminUser.setFirstName("admin");
			adminUser.setLastName("admin");
			adminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(adminUser);
		}

	}
}
