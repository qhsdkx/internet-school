package by.andron;

import by.andron.configuration.ConfigurationClass;
import by.andron.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationClass.class);
        while (true) {
            Thread.sleep(1000);
            System.out.println(context.getBean(UserRepository.class).findById(2L));

        }

    }

}