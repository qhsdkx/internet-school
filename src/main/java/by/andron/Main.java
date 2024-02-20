package by.andron;

import by.andron.configuration.ConfigurationClass;
import by.andron.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
       ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationClass.class);
        UserService service = context.getBean(UserService.class);
        System.out.println(service.findAll());
    }

}