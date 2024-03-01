package by.andron.repository;

import by.andron.model.User;

import java.util.List;

public interface UserRepository {

    User findById(Long id);

    List<User> findAll(int page, int size);

    User save(User user);

    void update(Long id, User user);

    void delete(Long id);

}
