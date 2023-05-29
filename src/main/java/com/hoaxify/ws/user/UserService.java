package com.hoaxify.ws.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    IUserRepository iUserRepository;


    PasswordEncoder passwordEncoder;

    //constructors injection
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void save(User user){
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        iUserRepository.save(user);
    }
    
}
