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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    ServiceNewTopic service;
    @Autowired
    TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseData> addTopic(@Valid @RequestBody DataTopic data, HttpServletRequest request, UriComponentsBuilder uriBuilder){
        var response = service.add(data, request);
        var res = new TopicResponseData(response);
        URI url = uriBuilder.path("/topics/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseData>> getTopics(@PageableDefault(sort = "creation", direction = Sort.Direction.ASC, size = 10) Pageable page){
        return ResponseEntity.ok(topicRepository.alls(page).map(TopicResponseData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TopicalDetail>> getTopic(@PathVariable Long id){
        return ResponseEntity.ok(topicRepository.findId(id).map(TopicalDetail::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicalDetail> updateTopic(@Valid @RequestBody TopicDataUpdate data, HttpServletRequest request){
        var response = service.update(data,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id,HttpServletRequest request){
        service.delete(id,request);
        return ResponseEntity.noContent().build();
    }

}
