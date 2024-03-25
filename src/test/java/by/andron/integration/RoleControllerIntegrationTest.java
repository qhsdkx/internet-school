package by.andron.integration;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;
import by.andron.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerIntegrationTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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
        container.start();
    }

    @Test
    @DisplayName("Successful find by id test for roles with access to read")
    @WithMockUser(authorities = "READ_ROLE")
    public void findByIdTest_shouldReturnRoleAndStatus200ForUserWithAuthority() throws Exception {
        // given
        Long id = 2L;
        RoleDto expected = RoleDto.builder().id(id).name("Admin").build();

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles/2"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(expected))
                ).andReturn();

        // then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden find by id test for unauthorized user")
    public void findById_shouldReturnStatus403ForUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(authorities = "READ_ROLE")
    @DisplayName(" Not found find by id test for user with read authority")
    public void findById_shouldReturnStatus404ForUserWithAuthority() throws Exception {
        // given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Successful find all test for roles with access to read")
    @WithMockUser(authorities = "READ_ROLE")
    public void findAllTest_shouldReturnRolesAndStatus200ForUserWithAuthority() throws Exception {
        // given
        List<RoleDto> roles = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).name("Admin").build());
            add(RoleDto.builder().id(2L).name("Student").build());
            add(RoleDto.builder().id(3L).name("Teacher").build());
        }};

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapper.writeValueAsString(roles))
                )
                .andReturn();

        // then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden find all test for an unauthorized user")
    public void findAllTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLE")
    @DisplayName("Successful save test for a user with create authority")
    public void saveTest_shouldReturnRoleDtoAndStatus201ForUserWithAuthority() throws Exception {
        // given
        RoleDto roleWithId = RoleDto.builder().id(2L).name("Teacher").build();
        RoleCreationDto roleWithoutId = new RoleCreationDto("Teacher");

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(mapper.writeValueAsString(roleWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        content().json(mapper.writeValueAsString(roleWithId)),
                        content().contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        // then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLE")
    @DisplayName("Save test with validation exception for a user with create authority")
    public void saveTestWithNullRole_shouldReturnStatus400ForUserWithAuthority() throws Exception {
        // given
        RoleCreationDto role = new RoleCreationDto();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(mapper.writeValueAsString(role))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "CREATE_ROLE")
    @DisplayName("Save test with validation exception for a user with create authority")
    public void saveTestWithEmptyRole_shouldReturnStatus400ForUserWithAuthority() throws Exception {
        // given
        RoleCreationDto role = new RoleCreationDto("");

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .content(mapper.writeValueAsString(role))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden save test for an unauthorized user")
    public void saveTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/roles"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLE")
    @DisplayName("Update test with not found role for user with update authority")
    public void updateTestWithNotFoundRole_shouldReturnStatus400ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = new RoleCreationDto("Student");

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1000")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLE")
    @DisplayName("Update test with validation exception for user with update authority")
    public void updateTestWithNullRole_shouldReturnStatus400ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = new RoleCreationDto();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLE")
    @DisplayName("Update test with validation exception for user with update authority")
    public void updateTestWithEmptyRole_shouldReturnStatus400ForUserWithOrderWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = new  RoleCreationDto("");

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_ROLE")
    @DisplayName("Successful update test for user with update authority")
    public void updateTest_shouldReturnRoleAndStatus200ForUserWithWriteAuthority() throws Exception {
        //given
        RoleCreationDto roleCreationDto = new RoleCreationDto("Admin");
        RoleDto roleDto = RoleDto.builder().id(2L).name("Admin").build();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/3")
                        .content(mapper.writeValueAsString(roleCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden update test for unauthorized user")
    public void updateTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "DELETE_ROLE")
    @DisplayName("Successful delete test for user with delete authority")
    public void deleteTest_shouldReturnStatus204ForUserWithAuthority() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Forbidden delete test for unauthorized user")
    public void deleteTest_shouldReturnStatus403ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/1"))
                .andExpect(status().isForbidden());
    }

}
