package me.raviel.compfest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.raviel.compfest.model.Product;
import me.raviel.compfest.repository.ProductDAOImpl;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAOImpl productDAO;

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public Product findProductByID(int id) {
        return productDAO.findProductByID(id);
    }

    @Override
    public void addProduct(Product pd) {
        productDAO.addProduct(pd);
    }

    @Override
    public void updateProduct(Product pd) {
        productDAO.updateProduct(pd);
    }

    @Override
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
    
}