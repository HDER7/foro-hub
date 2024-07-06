package com.example.forohub.domains.answers.validations;

import com.example.forohub.domains.answers.AnswerRepository;
import com.example.forohub.domains.users.User;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheAuthorAnswer implements ValidatorUpdateAnswer {

    @Autowired
    private AnswerRepository Repository;

    @Override
    public void validate(Long id, User user) {
        var topic = Repository.findById(id).orElse(null);
        assert topic != null;
        if (topic.getAuthor().getId() != user.getId()){
            throw new ValidationException("Esta respuesta no es de este usuario");
        }
    }
}
