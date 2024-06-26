package com.example.forohub.domains.topics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByMessage(String message);

    boolean existsByTitle(String title);

    @Query("SELECT t FROM Topic t WHERE t.status != 'ELIMINADO'")
    Page<Topic> alls(Pageable page);

    @Query("SELECT t FROM Topic t WHERE t.status != 'ELIMINADO' AND t.id=:id")
    Optional<Topic> findId(Long id);
}
