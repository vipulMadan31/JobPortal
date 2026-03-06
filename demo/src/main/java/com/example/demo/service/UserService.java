package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
   // @Autowired
   // private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }

    public void remove(String email){
        userRepository.delete(findByEmail(email));
    }

    public boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public void addNew(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        System.out.println(findAll());

    }
}
