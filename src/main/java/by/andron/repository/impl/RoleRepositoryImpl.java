package by.andron.repository.impl;

import by.andron.model.Role;
import lombok.RequiredArgsConstructor;
import by.andron.exception.RepositoryException;
import by.andron.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
    public List<Role> findAll(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            Query<Role> query = session.createQuery(FIND_ALL_QUERY, Role.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.list();
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
    public void update(Long id, Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Role oldRole = session.get(Role.class, id);
                changeRoles(oldRole, role);
                session.merge(oldRole);
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


    private void changeRoles(Role oldRole, Role role) {
        oldRole.setId(role.getId());
        oldRole.setName(role.getName());
        oldRole.setUsers(role.getUsers());
    }

}
