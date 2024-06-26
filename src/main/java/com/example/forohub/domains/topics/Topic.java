package com.example.forohub.domains.topics;

import com.example.forohub.domains.courses.Course;
import com.example.forohub.domains.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String title;
    @Setter
    private String message;
    private LocalDateTime creation;
    private boolean status;
    @JoinColumn(name = "course_id")
    @ManyToOne
    private Course course;
    @JoinColumn(name = "author_id")
    @ManyToOne
    private User author;

    public Topic(User user, Course course, String message, String title) {
        this.author = user;
        this.course = course;
        this.message = message;
        this.title = title;
        this.creation = LocalDateTime.now();
        this.status = true;
    }

}
