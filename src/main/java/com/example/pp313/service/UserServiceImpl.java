package com.example.pp313.service;


import com.example.pp313.model.Role;
import com.example.pp313.model.User;
import com.example.pp313.repository.RoleRepository;
import com.example.pp313.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;



    @Override
    public User getUserById(long id) {
        User user = null;
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        userRepo.save(passwordCoder(user));
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    @PostConstruct
    public void addDefaultUser() {
        if (!roleRepo.existsById(1L)) {
            roleRepo.save(new Role( "ROLE_USER"));
        }
        if (!roleRepo.existsById(2L)) {
            roleRepo.save(new Role("ROLE_ADMIN"));
        }

        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleRepo.findById(1L).orElseThrow(() -> new RuntimeException("Role not found")));

        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleRepo.findById(1L).orElseThrow(() -> new RuntimeException("Role not found")));
        roles2.add(roleRepo.findById(2L).orElseThrow(() -> new RuntimeException("Role not found")));

        User user1 = new User("Kolya", "Kol", "user@mail.com", "user", "user", roles1);
        User user2 = new User("Vanya", "Van", "admin@mail.com", "admin", "admin", roles2);

        addUser(user1);
        addUser(user2);
    }


    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
