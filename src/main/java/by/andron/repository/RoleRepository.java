package by.andron.repository;

import by.andron.model.Role;

import java.util.List;

public interface RoleRepository {

    Role findById(Long id);

    List<Role> findAll();

    Role save(Role role);

    void update(Role role);

    void delete(Long id);

}
