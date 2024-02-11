package org.example.service;

import org.example.model.Role;
import org.example.repository.RoleRepository;

import java.util.List;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role findById(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void update(Role role) {
        roleRepository.update(role);
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }

}
