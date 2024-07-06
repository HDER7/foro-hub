package com.example.forohub.domains.topics;

import com.example.forohub.domains.answers.AnswerResponseData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TopicAndAnswers(String title, String message, LocalDateTime creationDate, Status status, Long author, Long course, List<AnswerResponseData> answers) {
    public TopicAndAnswers(Topic topic) {
        this(topic.getTitle(), topic.getMessage(), topic.getCreation(), topic.getStatus(), topic.getAuthor().getId(),topic.getCourse().getId(),topic.getAnswers().stream().map(AnswerResponseData::new).collect(Collectors.toList()));
    }
}
