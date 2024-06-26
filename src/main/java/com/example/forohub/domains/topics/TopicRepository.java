package com.example.forohub.domains.topics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByMessage(String message);

    boolean existsByTitle(String title);

    Page<Topic> findByStatusTrue(Pageable page);
}
