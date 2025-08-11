package com.example.jwtdome.controller;

import com.example.jwtdome.domain.User;
import com.example.jwtdome.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getById(id);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.list();
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        userService.updateById(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.removeById(id);
    }
}
