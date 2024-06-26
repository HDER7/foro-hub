package com.example.forohub.controller;

import com.example.forohub.domains.topics.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    serviceNewTopic service;
    @Autowired
    TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseData> addTopic(@Valid @RequestBody DataTopic data, HttpServletRequest request){
        var response = service.add(data, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseData>> getTopics(@PageableDefault(sort = "creation", direction = Sort.Direction.ASC, size = 10) Pageable page){
        return ResponseEntity.ok(topicRepository.findByStatusTrue(page).map(TopicResponseData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TopicalDetail>> getTopic(@PathVariable Long id){
        return ResponseEntity.ok(topicRepository.findById(id).map(TopicalDetail::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicResponseData> updateTopic(@Valid @RequestBody TopicDataUpdate data, HttpServletRequest request){
        var response = service.update(data,request);
        return ResponseEntity.ok(response);
    }

}
