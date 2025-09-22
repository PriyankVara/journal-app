package com.priyank.JournalApplication.Service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.priyank.JournalApplication.Entity.User;
import com.priyank.JournalApplication.Respository.UserRepository;

@Service
public class UserService {
     @Autowired
    private UserRepository userRepository;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public void saveUser(User user){
    userRepository.save(user);
    }

    public List<User> getAll(){
       return userRepository.findAll();
    }

    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }
     public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
     }
}
