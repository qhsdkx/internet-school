package by.andron.repository.impl;

import by.andron.model.Course;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import by.andron.exception.RepositoryException;
import by.andron.repository.CourseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM Course";

    @Override
    public Course findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, id);
        } catch (Exception e) {
            throw new RepositoryException("Cannot find course by id");
        }
    }

    @Override
    public List<Course> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Course.class).list();
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all courses");
        }
    }

    @Override
    public Course save(Course course) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(course);
                transaction.commit();
            } catch (Exception e) {
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
                session.merge(course);
                transaction.commit();
            } catch (Exception e) {
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
                Course course = session.get(Course.class, id);
                session.remove(course);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete course");
            }
        }
    }

}
