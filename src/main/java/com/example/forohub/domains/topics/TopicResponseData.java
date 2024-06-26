package com.example.forohub.domains.topics;

import java.time.LocalDateTime;

public record TopicResponseData(Long id,
                                String title,
                                String message,
                                LocalDateTime creationDate,
                                Long courseId,
                                Long authorId) {
    public TopicResponseData(Topic topic) {
        this(topic.getId(), topic.getTitle(),topic.getMessage(), topic.getCreation(),topic.getCourse().getId(),topic.getAuthor().getId());
    }
}
