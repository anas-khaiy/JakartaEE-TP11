package com.example.demo.config;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // On vérifie si l'admin existe déjà
            if (userRepository.findByUsername("admin").isEmpty()) {

                // 1. Création des rôles
                Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));
                Role userRole = roleRepository.save(new Role(null, "ROLE_USER"));

                // 2. Création de l'utilisateur
                User admin = new User();
                admin.setUsername("admin");
                // 🔥 IMPORTANT : On crypte le mot de passe "1234"
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setActive(true);
                admin.setRoles(Arrays.asList(adminRole, userRole));

                userRepository.save(admin);
                System.out.println("--- INITIALISATION : Admin créé (user: admin / pass: 1234) ---");
            }
        };
    }
}