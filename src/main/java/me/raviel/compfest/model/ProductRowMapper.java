package me.raviel.compfest.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product pd = new Product();

        pd.setProductid(rs.getInt("product_id"));
        pd.setCategory(rs.getString("category"));
        pd.setName(rs.getString("name"));
        pd.setDescription(rs.getString("description"));
        pd.setPrice(rs.getFloat("price"));

        return pd;
    }
    
}