package com.example.forohub.controller;

import com.example.forohub.domains.answers.AnswerData;
import com.example.forohub.domains.answers.AnswerRepository;
import com.example.forohub.domains.answers.AnswerResponseData;
import com.example.forohub.domains.answers.ServiceAnswer;
import com.example.forohub.domains.topics.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private ServiceNewTopic service;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ServiceAnswer serviceAnswer;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseData> addTopic(@Valid @RequestBody DataTopic data, HttpServletRequest request, UriComponentsBuilder uriBuilder){
        var response = service.add(data, request);
        var res = new TopicResponseData(response);
        URI url = uriBuilder.path("/topics/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(res);
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<AnswerResponseData> addAnswer(@RequestBody @Valid AnswerData data, UriComponentsBuilder uriBuilder, HttpServletRequest request, @PathVariable Long id) {
        var response = serviceAnswer.add(data,request,id);
        AnswerResponseData res = new AnswerResponseData(response.getId(),response.getTopic().getId(),response.getAuthor().getId(),response.getMessage(),response.getCreation());
        URI url = uriBuilder.path("/answers/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(res);
    }


    @GetMapping
    public ResponseEntity<Page<TopicResponseData>> getTopics(@PageableDefault(sort = "creation", direction = Sort.Direction.ASC, size = 10) Pageable page){
        return ResponseEntity.ok(topicRepository.alls(page).map(TopicResponseData::new));
    }

    //obtiene topico solo
    @GetMapping("/{id}")
    public ResponseEntity<Optional<TopicalDetail>> getTopic(@PathVariable Long id){
        return ResponseEntity.ok(topicRepository.findId(id).map(TopicalDetail::new));
    }

    //Obtine topicos con su respuesta
    @GetMapping("/{id}/answers")
    public ResponseEntity<Optional<TopicAndAnswers>> getTopicAndAnswers(@PathVariable Long id){
        return ResponseEntity.ok(topicRepository.findId(id).map(TopicAndAnswers::new));
    }

    //Obtine Respuesta
    @GetMapping("/answers/{id}")
    public ResponseEntity<Optional<AnswerResponseData>> getAnswer(@PathVariable Long id){
        return ResponseEntity.ok(answerRepository.findById(id).map(AnswerResponseData::new));
    }

    @GetMapping("/answers")
    public ResponseEntity<Page<AnswerResponseData>> getAllAnswers(@PageableDefault(sort = "creation", direction = Sort.Direction.ASC, size = 10) Pageable page) {
        return ResponseEntity.ok(answerRepository.findAllBy(page).map(AnswerResponseData::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicalDetail> updateTopic(@Valid @RequestBody TopicDataUpdate data, HttpServletRequest request){
        var response = service.update(data,request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/answers/{id}")
    @Transactional
    public ResponseEntity<AnswerResponseData> updateAnswer(@RequestBody @Valid AnswerData data, HttpServletRequest request, @PathVariable Long id) {
        var res = serviceAnswer.update(data,request,id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id,HttpServletRequest request){
        service.delete(id,request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/answers/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id, HttpServletRequest request){
        var res = serviceAnswer.delete(id,request);
        answerRepository.delete(res);
        return ResponseEntity.noContent().build();
    }

}
