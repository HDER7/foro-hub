package com.example.forohub.domains.topics.validations;

import com.example.forohub.domains.topics.TopicDataUpdate;
import com.example.forohub.domains.topics.TopicRepository;
import com.example.forohub.domains.users.User;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheAuthor implements ValidatorUpdateTopical{

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(TopicDataUpdate data, User user) {
        var topic = topicRepository.findById(data.id()).orElse(null);

        assert topic != null;
        if (topic.getAuthor().getId() != user.getId()){
            throw new ValidationException("Este topico no le pertenece a este usuario");
        }
    }
}
