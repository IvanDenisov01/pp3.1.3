package com.example.pp313.service;

import com.example.pp313.model.Role;
import com.example.pp313.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public List<Role> findAllRole() {
        return null;
    }

    @Override
    @PostConstruct
    public void addDefaultRole() {
        roleRepo.save(new Role("ROLE_USER"));
        roleRepo.save(new Role("ROLE_ADMIN"));
    }

    @Override
    public Set<Role> findByIdRoles(List<Long> roles) {
        return new HashSet<>(roleRepo.findAllById(roles));
    }
}
