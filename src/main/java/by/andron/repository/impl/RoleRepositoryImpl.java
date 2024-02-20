package by.andron.repository.impl;

import by.andron.model.Role;
import lombok.RequiredArgsConstructor;
import by.andron.exception.RepositoryException;
import by.andron.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM Role";

    @Override
    public Role findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class, id);
        } catch (Exception e) {
            throw new RepositoryException("Cannot find role by id");
        }
    }

    @Override
    public List<Role> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Role.class).list();
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all roles");
        }
    }

    @Override
    public Role save(Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(role);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot save role");
            }
        }
        return role;
    }

    @Override
    public void update(Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.merge(role);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot update role");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Role role = session.get(Role.class, id);
                session.remove(role);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete role");
            }
        }
    }

}
