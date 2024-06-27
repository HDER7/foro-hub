package com.example.forohub.domains.answers;

import java.time.LocalDateTime;

public record AnswerResponseData(Long Id, Long topicId, Long authorId, String message, LocalDateTime creation) {
    public AnswerResponseData(Answer data){
        this(data.getId(),data.getTopic().getId(),data.getAuthor().getId(),data.getMessage(),data.getCreation());
    }
}
