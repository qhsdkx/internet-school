package by.andron.controller;


import by.andron.mapper.RoleMapper;
import by.andron.model.Course;
import by.andron.model.CourseResult;
import by.andron.repository.AuthorityRepository;
import by.andron.repository.RoleRepository;
import by.andron.repository.UserRepository;
import by.andron.service.RoleService;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MockBean(classes = {
        RoleRepository.class,
        AuthorityRepository.class,
        UserRepository.class,
        CourseResult.class,
        Course.class
})
@AutoConfigureMockMvc
public class CommonControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected RoleService roleService;

    @Spy
    protected RoleMapper roleMapper;

}
