package com.springdemo.services;

import com.springdemo.config.JWTTokenUtil;
import com.springdemo.dto.AuthResponseData;
import com.springdemo.dto.RequestData;
import com.springdemo.dto.ResponseData;
import com.springdemo.securities.UserSecurityDetailAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceInterface{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Override
    public ResponseData login(RequestData requestData) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                requestData.getEmail(), requestData.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenUtil.tokenGenerator(authentication);
        UserDetails userDetails = (UserSecurityDetailAuth) authentication.getPrincipal();

        AuthResponseData authResponseData = new AuthResponseData(jwtToken, userDetails.getUsername());
        return new ResponseData(200, "Login success", authResponseData);
    }
}
