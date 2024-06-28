package com.example.forohub.controller;

import com.example.forohub.domains.courses.Course;
import com.example.forohub.domains.courses.CourseData;
import com.example.forohub.domains.courses.CourseRepository;
import com.example.forohub.domains.courses.CourseResponseData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    @Autowired
    private CourseRepository repository;

    @PostMapping
    public ResponseEntity<CourseResponseData> addCourse(@RequestBody @Valid CourseData data, UriComponentsBuilder uriBuilder) {
        Course course = repository.save(new Course(data));
        CourseResponseData res = new CourseResponseData(course.getId(),course.getName(),course.getCategory().toString());
        URI url = uriBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(url).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseData>> allCourses(Pageable page) {
        return ResponseEntity.ok(repository.findAll(page).map(CourseResponseData::new));
    }

}
