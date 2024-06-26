package com.example.forohub.domains.topics.validations;

import com.example.forohub.domains.topics.DataTopic;
import com.example.forohub.domains.topics.TopicRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageAlreadyExists implements ValidatorNewTopical{

    @Autowired
    TopicRepository repository;

    @Override
    public void validate(DataTopic data){
        var message = data.message();
        var bdMessage = repository.existsByMessage(message);
        if(bdMessage){
            throw new ValidationException("Ya existe un topico con ese mensaje");
        }
    }
}
