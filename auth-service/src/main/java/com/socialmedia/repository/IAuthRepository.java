package com.socialmedia.repository;

import com.socialmedia.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {


    Boolean existsByEmail(String email);

    Optional<Auth> findOptionalByEmailAndPassword(String email,String password);

}
