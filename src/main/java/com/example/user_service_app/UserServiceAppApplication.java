package com.example.user_service_app;

import com.example.user_service_app.domain.Role;
import com.example.user_service_app.domain.User;
import com.example.user_service_app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class UserServiceAppApplication {

    public static void main (String[] args) {
        SpringApplication.run(UserServiceAppApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "Vl Pr", "Vl", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Peter K", "Peter", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Ray L", "Ray", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Ivan S", "Ivan", "1234", new ArrayList<>()));

            userService.addRoleToUser("Vl", "ROLE_USER");
            userService.addRoleToUser("Vl", "ROLE_MANAGER");
            userService.addRoleToUser("Vl", "ROLE_ADMIN");
            userService.addRoleToUser("Peter", "ROLE_MANAGER");
            userService.addRoleToUser("Ray", "ROLE_ADMIN");
            userService.addRoleToUser("Ivan", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("Ivan", "ROLE_USER");
            userService.addRoleToUser("Ivan", "ROLE_ADMIN");
        };
    }

}
