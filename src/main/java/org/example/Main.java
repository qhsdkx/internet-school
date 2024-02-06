package org.example;

import org.example.repository.CourseRepository;
import org.example.repository.CourseResultRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.postgresql.ds.PGSimpleDataSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static final String DB_NAME = "postgres";
    public static final String URL = "jdbc:postgresql:" + DB_NAME;
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "postgres";

    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(URL);
        pgSimpleDataSource.setUser(USER_NAME);
        pgSimpleDataSource.setPassword(PASSWORD);

        UserRepository userRepository = new UserRepository(pgSimpleDataSource);
        CourseRepository courseRepository = new CourseRepository(pgSimpleDataSource);
        RoleRepository roleRepository = new RoleRepository(pgSimpleDataSource);;
        CourseResultRepository courseResultRepository = new CourseResultRepository(pgSimpleDataSource);

        System.out.println(userRepository.findAll());
        System.out.println(roleRepository.findAll());
        System.out.println(courseRepository.findAll());
        System.out.println(courseResultRepository.findAll());

        userRepository.delete(1L);
        courseRepository.delete(1L);
        courseResultRepository.delete(1L);

        System.out.println(userRepository.findAll());
        System.out.println(roleRepository.findAll());
        System.out.println(courseRepository.findAll());
        System.out.println(courseResultRepository.findAll());

    }
}