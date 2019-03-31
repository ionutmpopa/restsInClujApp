package com.boioio.restsincluj.dao.db;

import com.boioio.restsincluj.dao.RestaurantDAO;
import com.boioio.restsincluj.domain.RestType;
import com.boioio.restsincluj.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class JdbcTemplateRestaurantDAO implements RestaurantDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Restaurant> getAll() {
        return jdbcTemplate.query("select * from restaurant",
                new RestaurantMapper());
    }

    @Override
    public Restaurant findById(Long id) {
        return jdbcTemplate.queryForObject("select * from restaurant where id = ?",
                new RestaurantMapper(), id);
    }

    @Override
    public Restaurant update(Restaurant model) {

        String sql = "";
        Long newId = null;
        if (model.getId() > 0) {
            sql = "update restaurant set rest_address=?, rest_name=?, rest_type=?, capacity=?, description=? "
                    + "where id = ? returning id";
            newId = jdbcTemplate.queryForObject(sql, new Object[]{
                    model.getAddress(),
                    model.getName(),
                    model.getRestType().toString(),
                    model.getCapacity(),
                    model.getDescription(),
                    model.getId()

            }, new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int arg1) throws SQLException {
                    return rs.getLong(1);
                }
            });
        } else {
            sql = "insert into restaurant (rest_address, rest_name, rest_type, capacity, description) "
                    + "values (?, ?, ?, ?, ?) returning id";

            newId = jdbcTemplate.queryForObject(sql, new Object[]{
                    model.getAddress(),
                    model.getName(),
                    model.getRestType().toString(),
                    model.getCapacity(),
                    model.getDescription(),

            }, new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int arg1) throws SQLException {
                    return rs.getLong(1);
                }
            });
        }
        model.setId(newId);

        return model;
    }

    @Override
    public boolean delete(Restaurant model) {
        return jdbcTemplate.update("delete from restaurant where id = ?", model.getId()) > 0;
    }

    @Override
    public Collection<Restaurant> searchByName(String query) {
        return null;
    }

    private static class RestaurantMapper implements RowMapper<Restaurant> {

        @Override
        public Restaurant mapRow(ResultSet rs, int arg1) throws SQLException {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(rs.getLong("id"));
            restaurant.setAddress(rs.getString("rest_address"));
            restaurant.setCapacity(rs.getInt("capacity"));
            restaurant.setName(rs.getString("rest_name"));
            restaurant.setRestType(RestType.valueOf(rs.getString("rest_type")));
            restaurant.setDescription(rs.getString("description"));

            return restaurant;
        }

    }
}
