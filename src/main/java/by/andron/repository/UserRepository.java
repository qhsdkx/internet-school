package by.andron.repository;

import by.andron.model.User;

import java.util.List;

public interface UserRepository {

    User findById(Long id);

    List<User> findAll();

    User save(User user);

    void update(User user);

    void delete(Long id);

}
