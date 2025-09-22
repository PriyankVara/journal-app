package com.priyank.JournalApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyank.JournalApplication.Entity.User;
import com.priyank.JournalApplication.Service.UserService;

@RestController
@RequestMapping("public")
public class PublicController {
    
     @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> addNewUser(@RequestBody User newUser){
    userService.saveNewUser(newUser);
    return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

}
