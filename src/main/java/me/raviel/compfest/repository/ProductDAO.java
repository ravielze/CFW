package me.raviel.compfest.repository;

import java.util.List;

import me.raviel.compfest.model.Product;

public interface ProductDAO {

    public List<Product> getAllProducts();
    public Product findProductByID(int id);
    public List<Product> findProductByName(String name);
    public void addProduct(Product pd);
    public void updateProduct(Product pd);
    public void deleteProduct(int id);
    
}