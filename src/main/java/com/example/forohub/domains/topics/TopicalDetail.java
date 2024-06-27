package com.example.forohub.domains.topics;

import com.example.forohub.domains.answers.AnswerResponseData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TopicalDetail(String title, String message, LocalDateTime creationDate, Status status, Long author, Long course) {
    public TopicalDetail (Topic data){
        this(data.getTitle(),data.getMessage(),data.getCreation(),data.getStatus(),data.getAuthor().getId(),data.getCourse().getId());
;    }
}
