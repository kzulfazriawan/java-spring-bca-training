package com.springdemo.controllers;

import com.springdemo.dto.RequestData;
import com.springdemo.dto.ResponseData;
import com.springdemo.dto.UserDto;
import com.springdemo.entities.User;
import com.springdemo.services.AuthServiceInterface;
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

    @Autowired
    private AuthServiceInterface authServiceInterface;

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

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody RequestData requestData) throws Exception{
        ResponseData responseData = authServiceInterface.login(requestData);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/shows")
    public ResponseEntity<List<UserDto>> shows(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.showAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody UserDto userDto, BindingResult result){
        // cari user existing berdasarkan id
        User user = this.userService.findById(id);
        if (user == null) {
            result.rejectValue("id", null, "Id is not exists");
        }
        this.userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body("User " + userDto.getName() + " successfully update");
    }
}
