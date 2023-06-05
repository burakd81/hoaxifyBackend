package com.hoaxify.ws.auth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.IUserRepository;
import com.hoaxify.ws.user.User;

@RestController
public class AuthController {

    @Autowired
    IUserRepository iUserRepository;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/api/users/login")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization" , required = false) String authorization){
        if(authorization == null){
            ApiError error = new ApiError(401, "Unauthorized request", "/api/user/create");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

        }
        String base64encoded = authorization.split("Basic ")[1]; //iki parçalı array oluşuyor biz 2. tarafını alacağız
        String decoded =new String (Base64.getDecoder().decode(base64encoded)); //user1:password
        String[] parts = decoded.split(":");
        String username = parts[0];
        String password = parts[1];
        User inDB = iUserRepository.findByUsername(username);
        if(inDB == null){
            ApiError error = new ApiError(401, "Unauthorized request", "/api/user/create");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        String hashedPassword = inDB.getPassword();
        if(!passwordEncoder.matches(password, hashedPassword)){
            ApiError error = new ApiError(401, "Unauthorized request", "/api/user/create");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

        }

        //username, displayName,image
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("Username", inDB.getUsername());
        responseBody.put("Display Name",inDB.getDisplayName());
        responseBody.put("Image", inDB.getImage());


        return ResponseEntity.ok(responseBody);

    }
    
}
