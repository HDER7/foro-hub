package com.example.forohub.domains.answers;

import com.example.forohub.domains.topics.Topic;
import com.example.forohub.domains.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "answers")
@Entity(name = "Answer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String message;
    private LocalDateTime creation;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Answer(User user, Topic topic, String message) {
        this.creation = LocalDateTime.now();
        this.topic = topic;
        this.message = message;
        this.author = user;
    }
}
