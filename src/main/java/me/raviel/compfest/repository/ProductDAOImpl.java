package me.raviel.compfest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import me.raviel.compfest.model.Product;
import me.raviel.compfest.model.ProductRowMapper;

@Transactional
@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getAllProducts() {
        String query = "SELECT * from product;";
        RowMapper<Product> rowMapper = new ProductRowMapper();
        List<Product> result = jdbcTemplate.query(query, rowMapper);
        return result;
    }

    @Override
    public Product findProductByID(int id) {
        String query = "SELECT * from product where product_id = ?;";
        RowMapper<Product> rowMapper = new BeanPropertyRowMapper<Product>(Product.class);
        Product result = jdbcTemplate.queryForObject(query, rowMapper, id);
        return result;
    }

    @Override
    public List<Product> findProductByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addProduct(Product pd) {
        String query = "INSERT INTO product(product_id, name, description, category, price) VALUES(?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, pd.getProductid(), pd.getName(), pd.getDescription(), pd.getCategory(), pd.getPrice());
    }

    @Override
    public void updateProduct(Product pd) {
        String query = "UPDATE product SET name=?, description=?, category=?, price=? WHERE product_id=?;";
        jdbcTemplate.update(query, pd.getName(), pd.getDescription(), pd.getCategory(), pd.getPrice(), pd.getProductid());

    }

    @Override
    public void deleteProduct(int id) {
        String query = "DELETE FROM product WHERE product_id=?;";
        jdbcTemplate.update(query, id);
    }
    
}