package com.priyank.JournalApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyank.JournalApplication.Entity.User;
import com.priyank.JournalApplication.Service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;
    
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
        }
}
