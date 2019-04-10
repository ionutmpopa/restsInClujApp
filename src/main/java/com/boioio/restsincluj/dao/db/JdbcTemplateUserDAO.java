package com.boioio.restsincluj.dao.db;

import com.boioio.restsincluj.dao.UserDAO;
import com.boioio.restsincluj.domain.Role;
import com.boioio.restsincluj.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class JdbcTemplateUserDAO implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JdbcTemplateUserDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean delete(User model) {
        return false;
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("select * from app_user",
                new UserMapper());
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("select * from app_user where id = ?",
                new UserMapper(), id);
    }

    @Override
    public User update(User model) {

        String sql = "";
        String sql2 = "";
        Long newId = null;
        Long newRoleId = null;

        Role newRole = new Role();

        sql = "insert into app_user (email,first_name,last_name, password, active) "
                + "values (?,?,?,?,?) returning id";

        newId = jdbcTemplate.queryForObject(sql, new Object[]{
                model.getEmail(),
                model.getFirstName(),
                model.getLastName(),
                bCryptPasswordEncoder.encode(model.getPassword()),
                true
        }, new RowMapper<Long>() {
            public Long mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getLong(1);
            }
        });
        //}
        model.setId(newId);

        sql2 = "insert into user_role (user_id,role_id) "
                + "values (?,?) returning id";

        newRoleId = jdbcTemplate.queryForObject(sql2, new Object[]{
                model.getId(),
                2
        }, new RowMapper<Long>() {
            public Long mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getLong(1);
            }
        });

        newRole.setId(newRoleId);

        return model;
    }

    @Override
    public Collection<User> searchByName(String query) { return null; }


    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int arg1) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("active"));
            return user;
        }
    }

    private static class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int arg1) throws SQLException {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setUser_id(rs.getLong("user_id"));
            role.setRole_id(rs.getLong("role_id"));
            role.setId(rs.getLong("id"));
            return role;
        }
    }

}
