package org.example.repository.hibernate;

import org.example.exception.HibernateException;
import org.example.exception.RepositoryException;
import org.example.model.Course;
import org.example.model.CourseResult;
import org.example.repository.CourseResultRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CourseResultRepositoryImpl implements CourseResultRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_QUERY = "FROM course_results";

    public CourseResultRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CourseResult findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CourseResult.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("Cannot find course result by id");
        }
    }

    @Override
    public List<CourseResult> findAll(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, CourseResult.class).list();
        } catch (HibernateException e) {
            throw new RepositoryException("Cannot find all course results");
        }
    }

    @Override
    public CourseResult save(CourseResult courseResult) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.save(courseResult);
                transaction.commit();
            } catch (HibernateException e){
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot save course result");
            }
        }
        return courseResult;
    }

    @Override
    public void update (CourseResult courseResult){
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.update(courseResult);
                transaction.commit();
            } catch (HibernateException e){
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot update course result");
            }
        }
    }

    @Override
    public void delete(Long id){
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                CourseResult courseResult = session.load(CourseResult.class, id);
                session.delete(courseResult);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete course result");
            }
        }
    }
}
