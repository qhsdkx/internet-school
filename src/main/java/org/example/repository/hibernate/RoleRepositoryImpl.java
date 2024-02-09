package org.example.repository.hibernate;

import org.example.exception.HibernateException;
import org.example.exception.RepositoryException;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_QUERY = "FROM roles";

    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("Cannot find role by id");
        }
    }

    @Override
    public List<Role> findAll(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Role.class).list();
        } catch (HibernateException e) {
            throw new RepositoryException("Cannot find all roles");
        }
    }

    @Override
    public Role save(Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.save(role);
                transaction.commit();
            } catch (HibernateException e){
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot save role");
            }
        }
        return role;
    }

    @Override
    public void update (Role role){
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.update(role);
                transaction.commit();
            } catch (HibernateException e){
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot update role");
            }
        }
    }

    @Override
    public void delete(Long id){
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Role role = session.load(Role.class, id);
                session.delete(role);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete role");
            }
        }
    }
}
