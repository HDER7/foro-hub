package com.example.forohub.domains.topics.validations;

import com.example.forohub.domains.topics.TopicDataUpdate;
import com.example.forohub.domains.users.User;

public interface ValidatorUpdateTopical {
    public void validate(TopicDataUpdate data, User user);
}
