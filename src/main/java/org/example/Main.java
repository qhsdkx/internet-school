package org.example;

import org.example.model.User;
import org.example.repository.CourseResultRepository;
import org.example.repository.hibernate.CourseRepositoryImpl;
import org.example.repository.hibernate.CourseResultRepositoryImpl;
import org.example.repository.hibernate.RoleRepositoryImpl;
import org.example.repository.hibernate.UserRepositoryImpl;
import org.example.service.CourseResultService;
import org.example.service.CourseService;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.example.util.HibernateSessionFactory;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(sessionFactory);
        UserService userService = new UserService(userRepository);

        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl(sessionFactory);
        RoleService roleService = new RoleService(roleRepository);

        CourseRepositoryImpl courseRepository = new CourseRepositoryImpl(sessionFactory);
        CourseService courseService = new CourseService(courseRepository);

        CourseResultRepositoryImpl courseResultRepository = new CourseResultRepositoryImpl(sessionFactory);
        CourseResultService courseResultService = new CourseResultService(courseResultRepository);
        User byId = userService.findById(3L);
        System.out.println(byId.getRoles());
    }
}