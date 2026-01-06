package com.fooddelivery.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fooddelivery.demo.model.Role;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initSuperAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByEmail("superadmin@gmail.com").isEmpty()) {

                User superAdmin = new User();
                superAdmin.setName("Super Admin");
                superAdmin.setEmail("superadmin@gmail.com");
                superAdmin.setPassword(passwordEncoder.encode("superadmin123"));
                superAdmin.setRole(Role.SUPER_ADMIN);

                userRepository.save(superAdmin);

                System.out.println("SUPER ADMIN CREATED");
            }
        };
    }
}