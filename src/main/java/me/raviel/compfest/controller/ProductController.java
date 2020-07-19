package me.raviel.compfest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import me.raviel.compfest.model.Product;
import me.raviel.compfest.model.User;
import me.raviel.compfest.service.ProductServiceImpl;
import me.raviel.compfest.service.UserService;

@Controller
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserService userService;
//th:if="${prod.ownerid} == ${userid}"
    @RequestMapping(value = {"/home"}, method=RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Product> all = productService.getAllProducts();

        model.addObject("username", user.getFirstname() + " " + user.getLastname());
        model.addObject("userid", user.getId());
        model.addObject("all_product_list", all);
        model.setViewName("home/home");
        return model;
    }

    @RequestMapping(value = "/home/update-product/{id}", method = RequestMethod.GET)
    public ModelAndView editProduct(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        
        Product pd = productService.findProductByID(id);

        model.addObject("productForm", pd);
        
        model.setViewName("home/product_form");
        return model;
    }

    @RequestMapping(value="/home/add-product", method=RequestMethod.GET)
    public ModelAndView addProduct() {
        ModelAndView model = new ModelAndView();
        
        Product pd = new Product();
        model.addObject("productForm", pd);
        
        model.setViewName("home/product_form");
        return model;
    }

    @RequestMapping(value="/home/save-product", method=RequestMethod.GET)
    public ModelAndView saveGet() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value="/home/save-product", method=RequestMethod.POST)
    public ModelAndView saveOrUpdate(@ModelAttribute("productForm") Product pd, BindingResult bindingResult) {
        switch(pd.getCategory().toUpperCase()){
            case "VGA":
            case "RAM":
            case "PROCESSOR":
            case "MOTHERBOARD":
            case "STORAGE":
            pd.setCategory(pd.getCategory().toUpperCase());
                break;
            default:
                bindingResult.rejectValue("category", "error.category", "This category is not exist.");
                break;
        }

        if (pd.getPrice() <= 0.01){
            bindingResult.rejectValue("price", "error.price", "The allowed minimum price is $0.01");
        }
        if (!bindingResult.hasErrors()){
            
            if(pd.getProductid() != 0) {
                productService.updateProduct(pd);
            } else {
                productService.addProduct(pd);
            }
        } else {
            return new ModelAndView("/home/product_form");
        }
        
        return new ModelAndView("redirect:/home");
    }
    
    @RequestMapping(value="/home/delete-product/{product_id}", method=RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable("product_id") int id) {

        productService.deleteProduct(id); 
        
        return new ModelAndView("redirect:/home");
    }
    
}