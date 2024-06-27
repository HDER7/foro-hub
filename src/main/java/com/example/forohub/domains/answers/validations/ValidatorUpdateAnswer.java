package com.example.forohub.domains.answers.validations;

import com.example.forohub.domains.answers.AnswerDataUpdate;
import com.example.forohub.domains.users.User;

public interface ValidatorUpdateAnswer {
    void validate(AnswerDataUpdate data, User user);
}
