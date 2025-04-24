package com.example.swapSafe.seeder;

import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@Configuration
@RequiredArgsConstructor
public class AdminSeeder {

    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner seedAdmin(UserRepository repo){
        return args -> {
            String email = "admin@swapSafe.local";
            if(repo.existsByEmail(email)) return;
            User admin = User.builder()
                    .name("Bob Joe")
                    .email(email)
                    .password(encoder.encode("admin124"))
                    .roles(Set.of(User.Role.ADMIN))
                    .build();
            repo.save(admin);
            System.out.println("Seeded Admin....\nName: "+admin.getName()+"\nEmail: "+admin.getEmail()+"\nRole: "+admin.getRoles());
        };
    }
}
