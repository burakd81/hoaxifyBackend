package com.hoaxify.ws.user;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericResponse;
import jakarta.validation.Valid;

@RestController
public class UserController {

    //(Field Injection)Autowired otomatik oluşturup enjekte eder
    @Autowired
    UserService userService;

    @PostMapping("/api/users/create")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse createUser(@Valid @RequestBody User user){
        userService.save(user);

        return  new GenericResponse("User created.");

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception){
        ApiError error = new ApiError(400, "Validation Error", "/api/users/create");
        Map<String,String> validationError = new HashMap<>(); 
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()){
            validationError.put(fieldError.getField(), fieldError.getDefaultMessage());

        }
        error.setValidationErrors(validationError);
        return error;
    }
}
