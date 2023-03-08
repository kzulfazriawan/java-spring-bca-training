package com.springdemo.controllers;

import com.springdemo.dto.UserDto;
import com.springdemo.entities.User;
import com.springdemo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class Auth {
    @Autowired
    private UserService userService;

    public Auth(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto, BindingResult result) {
        // get existing email from user
        User user = this.userService.findUserByEmail(userDto.getEmail());

        if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Email is already taken");
        }

        this.userService.save(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("User " + userDto.getName() + " successfully created");
    }


    @GetMapping("/shows")
    public ResponseEntity<List<UserDto>> shows(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.showAll());
    }


}