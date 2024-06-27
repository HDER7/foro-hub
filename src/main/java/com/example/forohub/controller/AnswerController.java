package com.example.forohub.controller;

import com.example.forohub.domains.answers.*;
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
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository repository;
    @Autowired
    private ServiceAnswer serviceAnswer;

    @PostMapping
    public ResponseEntity<AnswerResponseData> addAnswer(@RequestBody @Valid AnswerData data, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        var response = serviceAnswer.add(data,request);
        AnswerResponseData res = new AnswerResponseData(response.getId(),response.getTopic().getId(),response.getAuthor().getId(),response.getMessage(),response.getCreation());
        URI url = uriBuilder.path("/answers/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<AnswerResponseData>> getAllAnswers(@PageableDefault(sort = "creation", direction = Sort.Direction.ASC, size = 10) Pageable page) {
        return ResponseEntity.ok(repository.findAllBy(page).map(AnswerResponseData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AnswerResponseData>> getAnswer(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).map(AnswerResponseData::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AnswerResponseData> updateAnswer(@RequestBody @Valid AnswerDataUpdate data, HttpServletRequest request) {
        var res = serviceAnswer.update(data,request);
        return ResponseEntity.ok(res);
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id, HttpServletRequest request){
//        serviceAnswer.delete(id,request);
//        return ResponseEntity.noContent().build();
//    }




}
