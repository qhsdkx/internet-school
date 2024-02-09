package org.example.repository.hibernate;

import org.example.exception.HibernateException;
import org.example.exception.RepositoryException;
import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CourseRepositoryImpl implements CourseRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_QUERY = "FROM Course";

    public CourseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Course findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("Cannot find course by id");
        }
    }

    @Override
    public List<Course> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Course.class).list();
        } catch (HibernateException e) {
            throw new RepositoryException("Cannot find all courses");
        }
    }

    @Override
    public Course save(Course course) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.save(course);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot save course");
            }
        }
        return course;
    }

    @Override
    public void update(Course course) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.update(course);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot update course");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Course course = session.load(Course.class, id);
                session.delete(course);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete course");
            }
        }
    }
}
