package by.andron.repository.impl;

import by.andron.model.User;
import lombok.RequiredArgsConstructor;
import by.andron.exception.RepositoryException;
import by.andron.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM User";

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RepositoryException("Cannot find user by id");
        }
    }

    @Override
    public List<User> findAll(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(FIND_ALL_QUERY, User.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.list();
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all users");
        }
    }

    @Override
    public User save(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot save user");
            }
        }
        return user;
    }

    @Override
    public void update(Long id, User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                User oldUser = session.get(User.class, id);
                changeUsers(oldUser, user);
                session.merge(oldUser);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot update user");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.remove(user);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete user");
            }
        }
    }

    private void changeUsers(User oldUser, User user) {
        oldUser.setId(user.getId());
        oldUser.setName(user.getName());
        oldUser.setSurname(user.getSurname());
        oldUser.setLogin(user.getLogin());
        oldUser.setBirthDate(user.getBirthDate());
        oldUser.setPassword(user.getPassword());
        oldUser.setCourseResults(user.getCourseResults());
        oldUser.setCourses(user.getCourses());
        oldUser.setRoles(user.getRoles());
        oldUser.setTeacherCourses(user.getTeacherCourses());
    }

}
