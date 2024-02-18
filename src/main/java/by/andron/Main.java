package by.andron;

import by.andron.mapper.UserMapper;
import by.andron.repository.impl.UserRepositoryImpl;
import by.andron.service.UserService;
import by.andron.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.mapstruct.factory.Mappers;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(sessionFactory);
        UserService userService = new UserService(userRepository, Mappers.getMapper(UserMapper.class));

//        RoleRepositoryImpl role = new RoleRepositoryImpl(sessionFactory);
//        RoleService roleService = new RoleService(role, Mappers.getMapper(RoleMapper.class));

        System.out.println(userService.findAll());
        System.out.println('\n');
        //System.out.println(roleService.findAll());
    }

}