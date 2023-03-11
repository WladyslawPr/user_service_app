package com.example.user_service_app.service;

import com.example.user_service_app.domain.Role;
import com.example.user_service_app.domain.User;
import com.example.user_service_app.repository.RoleRepository;
import com.example.user_service_app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username);
      if (user == null) {
          log.error("User not found in the db");
          throw new UsernameNotFoundException("User not found in the db");
      } else {
          log.info("User found in the db: {}", username);
      }
      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      user.getRoles().forEach(role -> {
          authorities.add(new SimpleGrantedAuthority(role.getName()));
      });
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser (User user) {
        log.info("saving new user {} to the db", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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