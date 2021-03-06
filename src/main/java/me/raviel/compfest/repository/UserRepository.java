package me.raviel.compfest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.raviel.compfest.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByEmail(String email);

}