package com.socialmedia.repository;

import com.socialmedia.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {


    Boolean existsByEmail(String email);

    Optional<Auth> findOptionalByEmailAndPassword(String email,String password);

    boolean  existsByUsername(String username);



    //Ödev!!! Post microservice
    //userlar post atabilsin
    //post atma tarihi, içerik, photo(img), kimin attığı,

    //yazdığımız servis geç cevap dönüyor ne yapabilirz.



}
