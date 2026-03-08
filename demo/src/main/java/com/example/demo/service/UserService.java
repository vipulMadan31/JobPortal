package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void remove(String email){
        Optional<User> user = findByEmail(email);
        if(user.isPresent()){
            userRepository.delete(user.get());
        }
    }

    public boolean isDuplicateEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public void addNew(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        System.out.println(findAll());
    }
}
