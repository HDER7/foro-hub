package com.example.forohub.domains.topics;

import java.time.LocalDateTime;

public record TopicalDetail(String title, String message, LocalDateTime creationDate, Boolean status, Long author, Long course) {
    public TopicalDetail (Topic data){
        this(data.getTitle(),data.getMessage(),data.getCreation(),data.isStatus(),data.getAuthor().getId(),data.getCourse().getId())
;    }
}
