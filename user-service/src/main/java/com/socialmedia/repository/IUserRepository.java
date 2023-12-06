package com.socialmedia.repository;

import com.socialmedia.entity.UserProfile;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserProfile,Long> {


    Optional<UserProfile> findByAuthId(Long id);

}
