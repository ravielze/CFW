package me.raviel.compfest.service;

import me.raviel.compfest.model.User;

public interface UserService {

    public User findUserByID(int id);

    public User findUserByEmail(String email);

    public void saveUser(User user);
    
}