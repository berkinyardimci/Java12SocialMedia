package com.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class AuthServiceApplication {


    //Validation yapalım,
    //2 fieldda Boş bırakılmasın (username ve password,email)
    //username ve passwordu min 2 max 20 karakter olsun

    //Email içinde email validation yapalım

    //Loign metodu response dönsün(id, username, email, role,status), request alcak username password.

    //kullanıcı sil --> soft delete,
    //ENUM, boolean, 0 , 1

    //auth ile user service config servere taşıyalım.

    //rabbit mq üzerinden hesabı aktif edelim.

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class);
    }
}