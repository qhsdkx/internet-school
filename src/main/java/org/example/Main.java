package org.example;

import org.example.model.Course;
import org.example.model.CourseResult;
import org.example.model.Role;
import org.example.model.User;
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

        User userAndron = new User(1L, "Andron", "Uruey", "Java Top", "12345", LocalDate.parse("01.04.2003", formatter));
        User userVlad = new User(2L, "Vlad", "Ponomarenko", "Flutter Top", "123456", LocalDate.parse("04.12.2003", formatter));
        User userPerepelica = new User(3L, "Sanya", "Perepelica", "Moodle Top", "1234567", LocalDate.parse("01.01.1900", formatter));

        Course courseMath = new Course(1L, "Math course", LocalDate.parse("11.10.2020", formatter), LocalDate.parse("11.12.2021", formatter), "Compleated", 3L);
        Course courseAssembler = new Course(2L, "Assembler course", LocalDate.parse("21.11.2021", formatter), LocalDate.parse("11.12.2022", formatter), "Compleated", 3L);
        Course courseCompScience = new Course(3L, "Computer Science course", LocalDate.parse("11.10.2023", formatter), LocalDate.parse("15.09.2025", formatter), "In process", 3L);

        CourseResult courseMathResultAndron = new CourseResult(1L, 10, "Nice man, only working without Dota streams", LocalDate.parse("11.12.2021", formatter), 1L);
        CourseResult courseAssemblerResultAndron = new CourseResult(2L, 6, "Only Dota, wtf maaan, good boy in math", LocalDate.parse("11.12.2022", formatter), 1L);
        CourseResult courseMathResultVlad = new CourseResult(3L, 7, "Math was going so bad, but good boy, he need some more time", LocalDate.parse("11.12.2021", formatter), 2L);
        CourseResult courseAssemblerResultVlad= new CourseResult(4L, 10, "Nice code, he had written soft to TU-154 only wit keyboard", LocalDate.parse("11.12.2022", formatter), 2L);

        Role teacher = new Role(1L, "Teacher");
        Role admin = new Role(2L, "Admin");
        Role student = new Role(3L, "Student");

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(URL);
        pgSimpleDataSource.setUser(USER_NAME);
        pgSimpleDataSource.setPassword(PASSWORD);

        UserRepository userRepository = new UserRepository(pgSimpleDataSource);
        userRepository.insert(userAndron);
        userRepository.insert(userVlad);
        userRepository.insert(userPerepelica);

        CourseRepository courseRepository = new CourseRepository(pgSimpleDataSource);
        courseRepository.insert(courseMath);
        courseRepository.insert(courseAssembler);
        courseRepository.insert(courseCompScience);

        RoleRepository roleRepository = new RoleRepository(pgSimpleDataSource);
        roleRepository.insert(teacher);
        roleRepository.insert(admin);
        roleRepository.insert(student);

        CourseResultRepository courseResultRepository = new CourseResultRepository(pgSimpleDataSource);
        courseResultRepository.insert(courseMathResultAndron);
        courseResultRepository.insert(courseAssemblerResultAndron);
        courseResultRepository.insert(courseMathResultVlad);
        courseResultRepository.insert(courseAssemblerResultVlad);

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