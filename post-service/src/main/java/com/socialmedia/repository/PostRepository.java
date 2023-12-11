package com.socialmedia.repository;

import com.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
