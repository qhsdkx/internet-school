package by.andron.repository;

import by.andron.model.Role;

import java.util.List;

public interface RoleRepository {

    Role findById(Long id);

    List<Role> findAll(int page, int size);

    Role save(Role role);

    void update(Long id, Role role);

    void delete(Long id);

}
