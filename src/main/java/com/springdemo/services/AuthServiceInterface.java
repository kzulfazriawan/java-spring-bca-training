package com.springdemo.services;

import com.springdemo.dto.RequestData;
import com.springdemo.dto.ResponseData;

public interface AuthServiceInterface {
    ResponseData login(RequestData requestData) throws Exception;
}
