package by.andron.integration;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;
import by.andron.exception.ServiceException;
import by.andron.service.RoleService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Testcontainers
public class RoleServiceIntegrationTest {

    @Autowired
    private RoleService roleService;

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("db")
            .withUsername("test_user")
            .withPassword("test_password");

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", container::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    @BeforeAll
    public static void setUp() {
        container.setWaitStrategy(
                new LogMessageWaitStrategy()
                        .withRegEx(".*database system is ready to accept connections.*\\s")
                        .withTimes(1)
                        .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS))
        );
        container.start();
    }

    @AfterAll
    public static void close() {
        container.close();
    }

    @Test
    @DisplayName("Find by id test which returns role from db and asserts if objects are equal")
    void findByIdTest_shouldReturnRoleDtoWithId1() {
        Long id = 2L;
        RoleDto expected = RoleDto.builder().id(id).name("Admin").build();

        RoleDto actual = roleService.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find by id test which throws service exception during finding role")
    void findByIdTest_shouldThrowNotFoundException() {
        Assertions.assertThrows(ServiceException.class, () -> roleService.findById(1000L));
    }

    @Test
    @DisplayName("Find all test which returns list of role dto from db and asserts if objects are equal")
    void findAllTest_shouldReturnListOfRoleDto() {
        List<RoleDto> expected = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).name("Teacher").build());
            add(RoleDto.builder().id(2L).name("Admin").build());
            add(RoleDto.builder().id(3L).name("Student").build());
        }};

        List<RoleDto> actual = roleService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Save test which returns role dto after its' creating")
    void saveTest_shouldReturnRoleDtoAfterCreating() {
        RoleCreationDto roleWithoutId = new RoleCreationDto("Bratishka");
        RoleDto expected = RoleDto.builder().id(4L).name("Bratishka").build();

        RoleDto actual = roleService.save(roleWithoutId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Save test which throws exception because of not valid role entity")
    void saveTest_shouldThrowServiceExceptionDuringSavingNullRole() {
        RoleCreationDto role = new RoleCreationDto();

        Assertions.assertThrows(ServiceException.class, () -> roleService.save(role));
    }

    @Test
    @DisplayName("Successful update test which returns equal objects")
    void updateTest_shouldReturnEqualRoleNames() {
        Long id = 3L;
        RoleCreationDto expected = new RoleCreationDto("High skill user");

        roleService.update(id, expected);
        RoleDto actual = roleService.findById(id);

        Assertions.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @DisplayName("Update test which throws service exception due to not found id in db")
    void updateTest_shouldThrowServiceExceptionDueToNotFoundError() {
        Long id = 10000L;
        RoleCreationDto role = new RoleCreationDto("High skill user");

        Assertions.assertThrows(ServiceException.class, () -> roleService.update(id, role));
    }

    @Test
    @DisplayName("Update test which throws service exception due to validation error")
    void updateTest_shouldThrowServiceExceptionDueToValidationError() {
        Long id = 3L;
        RoleCreationDto role = new RoleCreationDto();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> roleService.update(id, role));
    }

    @Test
    @DisplayName("Delete test which throws exception 'there is no such role' after its' deleting")
    void deleteTest_shouldReturnNullAfterDeleting() {
        Long id = 2L;

        roleService.delete(id);

        Assertions.assertThrows(ServiceException.class, () -> roleService.findById(id));
    }

}
