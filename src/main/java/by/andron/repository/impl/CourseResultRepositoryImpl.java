package by.andron.repository.impl;

import lombok.RequiredArgsConstructor;
import by.andron.exception.RepositoryException;
import by.andron.model.CourseResult;
import by.andron.repository.CourseResultRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseResultRepositoryImpl implements CourseResultRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM CourseResult";

    @Override
    public CourseResult findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CourseResult.class, id);
        } catch (Exception e) {
            throw new RepositoryException("Cannot find course result by id");
        }
    }

    @Override
    public List<CourseResult> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, CourseResult.class).list();
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all course results");
        }
    }

    @Override
    public CourseResult save(CourseResult courseResult) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(courseResult);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot save course result");
            }
        }
        return courseResult;
    }

    @Override
    public void update(Long id, CourseResult courseResult) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                CourseResult oldCourseResult = session.get(CourseResult.class, id);
                changeCourseResults(oldCourseResult, courseResult);
                session.merge(oldCourseResult);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot update course result");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                CourseResult courseResult = session.get(CourseResult.class, id);
                session.remove(courseResult);
                transaction.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Cannot delete course result");
            }
        }
    }

    private void changeCourseResults(CourseResult oldCourseResult, CourseResult courseResult) {
        oldCourseResult.setId(courseResult.getId());
        oldCourseResult.setCourse(courseResult.getCourse());
        oldCourseResult.setUser(courseResult.getUser());
        oldCourseResult.setScore(courseResult.getScore());
        oldCourseResult.setFeedback(courseResult.getFeedback());
        oldCourseResult.setEndDate(courseResult.getEndDate());
    }

}
