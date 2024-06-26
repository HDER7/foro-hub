package com.example.forohub.domains.topics.validations;

import com.example.forohub.domains.topics.DataTopic;
import com.example.forohub.domains.topics.TopicRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleAlreadyExists implements ValidatorNewTopical{

    @Autowired
    TopicRepository repository;

    @Override
    public void validate(DataTopic data){
        var title = data.title();
        var bdTitles = repository.existsByTitle(title);
        if(bdTitles){
            throw new ValidationException("Ya existe un topico con ese titulo");
        }
    }
}
