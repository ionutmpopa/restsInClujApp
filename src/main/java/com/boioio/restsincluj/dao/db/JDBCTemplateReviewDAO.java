package com.boioio.restsincluj.dao.db;


import com.boioio.restsincluj.dao.ReviewDAO;
import com.boioio.restsincluj.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

@Repository
public class JDBCTemplateReviewDAO implements ReviewDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JDBCTemplateReviewDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Review> getAll() {
        return jdbcTemplate.query("select * from review",
                new ReviewMapper());
    }

    @Override
    public Review findById(Long id) {
        return jdbcTemplate.queryForObject("select * from review where id = ?",
                new ReviewMapper(), id);
    }

    @Override
    public Review update(Review model) {

        String sql = "";
        Long newId = null;
        if (model.getId() > 0) {
            sql = "update review set restaurant_id=?, review=?, date_of_visit=?, date_of_review=?, rating=?"
                    + "where id = ? returning id";
            newId = jdbcTemplate.queryForObject(sql, new Object[]{
                    model.getRestaurant_id(),
                    model.getReview(),
                    new Timestamp(model.getDateOfVisit().getTime()),
                    new Timestamp(model.getDateOfReview().getTime()),
                    model.getRating().toString(),
                    model.getId()

            }, new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int arg1) throws SQLException {
                    return rs.getLong(1);
                }
            });
        } else {
            sql = "insert into review (restaurant_id, review, date_of_visit, date_of_review, rating) "
                    + "values (?, ?, ?, ?, ?) returning id";

            newId = jdbcTemplate.queryForObject(sql, new Object[]{
                    model.getRestaurant_id(),
                    model.getReview(),
                    new Timestamp(model.getDateOfVisit().getTime()),
                    new Timestamp(model.getDateOfReview().getTime()),
                    model.getRating()
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
    public boolean delete(Review model) {
        return jdbcTemplate.update("delete from review where id = ?", model.getId()) > 0;
    }

    @Override
    public Collection<Review> searchByTitle(String query) {
        return jdbcTemplate.query("select * from review "
                        + "where lower title like ?",
                new String[]{"%" + query.toLowerCase() + "%"},
                new ReviewMapper());
    }

    @Override
    public Collection<Review> searchByRating(String query) {
        return jdbcTemplate.query("select * from review "
                            + "where rating like ?",
                            new String[]{"%" + query.toLowerCase() + "%"},
                            new ReviewMapper());

    }

    private static class ReviewMapper implements RowMapper<Review> {

        @Override
        public Review mapRow(ResultSet rs, int arg1) throws SQLException {
            Review review = new Review();
            review.setId(rs.getLong("id"));
            review.setRestaurant_id(rs.getLong("restaurant_id"));
            review.setReview(rs.getString("review"));
            review.setDateOfVisit(new Date(rs.getTimestamp("date_of_visit").getTime()));
            review.setDateOfReview(new Date(rs.getTimestamp("date_of_review").getTime()));
            review.setRating(rs.getString("rating"));
            return review;
        }

    }

}