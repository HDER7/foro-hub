package com.example.forohub.domains.topics;

import com.example.forohub.domains.courses.Course;
import com.example.forohub.domains.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private boolean status;
    @JoinColumn(name = "course_id")
    @ManyToOne
    private Course curso;
    @JoinColumn(name = "author_id")
    @ManyToOne
    private User author;

}
