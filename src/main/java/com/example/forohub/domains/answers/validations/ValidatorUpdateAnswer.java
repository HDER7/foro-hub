package com.example.forohub.domains.answers.validations;

import com.example.forohub.domains.users.User;

public interface ValidatorUpdateAnswer {
    void validate(Long id, User user);
}
