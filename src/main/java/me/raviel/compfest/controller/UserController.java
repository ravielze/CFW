package me.raviel.compfest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import me.raviel.compfest.model.User;
import me.raviel.compfest.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();

        model.setViewName("user/login");

        return model;
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView profile(){
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        model.addObject("user", user);
        model.setViewName("home/profile");

        return model;
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.POST)
    public ModelAndView editProfile(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User nowUser = userService.findUserByEmail(auth.getName());

        if (userExists != null){
            if (!userExists.getEmail().equals(user.getEmail()))
                bindingResult.rejectValue("email", "error.user", "This email is already registered.");
        }

        if (bindingResult.hasErrors()){
            model.setViewName("home/profile");
        } else {
            user.setId(nowUser.getId());
            user.setEmail(nowUser.getEmail());
            userService.saveUser(user);
            model.addObject("msg", "Profile has been edited!");
            model.addObject("user", nowUser);
            model.setViewName("home/profile");
        }

        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signup(){
        ModelAndView model = new ModelAndView();
        User user = new User();

        model.addObject("user", user);
        model.setViewName("user/signup");

        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());

        if (userExists != null){
            bindingResult.rejectValue("email", "error.user", "This email is already registered.");
        }

        if (bindingResult.hasErrors()){
            model.setViewName("user/signup");
        } else {
            userService.saveUser(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new User());
            model.setViewName("user/signup");
        }

        return model;
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public ModelAndView accessDenied(){
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }
    
}