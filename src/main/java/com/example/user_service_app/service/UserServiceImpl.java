package com.example.user_service_app.service;

import com.example.user_service_app.domain.Role;
import com.example.user_service_app.domain.User;
import com.example.user_service_app.repository.RoleRepository;
import com.example.user_service_app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser (User user) {
        log.info("saving new user {} to the db", user.getName());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole (Role role) {
        log.info("saving new role {} to the db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser (String username, String roleName) {
        log.info("adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public User getUser (String username) {
        log.info("fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers () {
        log.info("fetching all users {}");
        return userRepository.findAll();
    }
}
