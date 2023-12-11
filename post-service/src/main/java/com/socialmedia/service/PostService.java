package com.socialmedia.service;

import com.socialmedia.dto.request.PostSaveRequestDto;
import com.socialmedia.entity.Post;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.util.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post, Long> {

    private final PostRepository postRepository;
    private final IUserManager userManager;

    public PostService(PostRepository postRepository, IUserManager userManager) {
        super(postRepository);
        this.postRepository = postRepository;
        this.userManager = userManager;
    }

    public Post createPost(PostSaveRequestDto request) {

        Long id = userManager.getUserIdfromToken(request.getToken());

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .photo(request.getPhoto())
                .userId(id)
                .build();
        return save(post);
    }
}
