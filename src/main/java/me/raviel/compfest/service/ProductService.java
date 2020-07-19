package me.raviel.compfest.service;

import java.util.List;

import me.raviel.compfest.model.Product;

public interface ProductService {

    public List<Product> getAllProducts();
    public Product findProductByID(int id);
    //TODO public List<Product> findProductByName(String name);
    public void addProduct(Product pd);
    public void updateProduct(Product pd);
    public void deleteProduct(int id);
    
}