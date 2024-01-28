package org.example.repository;

import org.example.exception.RepositoryException;
import org.example.model.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

    private final DataSource dataSource;

    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM roles WHERE id = ?";
    public static final String SELECT_QUERY = "SELECT * FROM roles";
    public static final String INSERT_QUERY = "INSERT INTO roles(name) VALUES (?);";
    public static final String UPDATE_BY_ID_QUERY = "UPDATE roles SET name = ? WHERE id = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM roles WHERE id = ?";

    public RoleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Role findById(Long id) {
        Role role = new Role();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                fillRole(role, rs);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find role by id");
        }
        return role;
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Role role = new Role();
                fillRole(role, rs);
                roles.add(role);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all roles");
        }
        return roles;
    }

    public Role insert(Role role) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getName());
            int value = preparedStatement.executeUpdate();
            if (value == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getLong(1));
                }
            }
            return role;
        } catch (Exception e) {
            throw new RepositoryException("Cannot insert role");
        }
    }

    public void update(Long id, Role role) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException("Cannot update role by id");
        }
    }

    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException("Cannot delete role by id");
        }
    }

    private void fillRole(Role role, ResultSet rs) throws SQLException {
        role.setId(rs.getLong("id"));
        role.setName(rs.getString("name"));
    }

}
