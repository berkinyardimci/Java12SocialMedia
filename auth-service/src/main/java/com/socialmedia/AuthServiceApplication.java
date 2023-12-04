package com.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {


    //Validation yapalım,
    //2 fieldda Boş bırakılmasın (username ve password,email)
    //username ve passwordu min 2 max 20 karakter olsun

    //Email içinde email validation yapalım

    //Loign metodu response dönsün(id, username, email, role,status), request alcak username password.

    //kullanıcı sil --> soft delete,
    //ENUM, boolean, 0 , 1

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class);
    }
}