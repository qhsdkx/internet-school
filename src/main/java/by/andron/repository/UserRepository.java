package by.andron.repository;

import by.andron.dto.UserCreationDto;
import by.andron.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u left join fetch u.roles r left join fetch r.authorities left join fetch u.courses where u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    User findUserByLogin(String login);

}
