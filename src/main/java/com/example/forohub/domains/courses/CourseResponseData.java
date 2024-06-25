package com.example.forohub.domains.courses;

public record CourseResponseData(Long id,
                                 String name,
                                 String category) {

    public CourseResponseData(Course course){
        this(course.getId(),course.getName(),course.getCategory().toString());
    }

}
