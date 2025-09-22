package com.priyank.JournalApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.priyank.JournalApplication.Entity.User;

import com.priyank.JournalApplication.Service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping()
    public ResponseEntity<?> editUser(@RequestBody User newUser) {
       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
        User oldUser = userService.findByUserName(userName);
        if(oldUser != null){
            oldUser.setUserName(newUser.getUserName());
            oldUser.setPassword(newUser.getPassword());
            userService.saveNewUser(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
