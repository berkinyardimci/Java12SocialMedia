package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveStatusConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "activeStatusQueue")
    public void activeStatus(Long authId){
        userProfileService.acivateUser(authId);
    }
}
