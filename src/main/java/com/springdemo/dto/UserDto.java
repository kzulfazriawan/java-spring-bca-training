package com.springdemo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String name;
    @NotEmpty(message = "Email must be unique and required")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
}
