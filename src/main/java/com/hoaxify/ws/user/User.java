package com.hoaxify.ws.user;

import lombok.Data;

//bu lombok eklentisi getset tostring gibi methodları otomatik yapmaktadır.
@Data
public class User {

    private String username;
    private String displayName;
    private String password;


    
    
}
