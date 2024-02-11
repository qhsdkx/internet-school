package org.example;


import org.example.repository.hibernate.RoleRepositoryImpl;
import org.example.repository.hibernate.UserRepositoryImpl;
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

        System.out.println(userService.findById(1L));
        System.out.println(userService.findById(3L));
        System.out.println('\n');
        System.out.println(userService.findAll());
    }
}