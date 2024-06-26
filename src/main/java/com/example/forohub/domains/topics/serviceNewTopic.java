package com.example.forohub.domains.topics;

import com.example.forohub.domains.courses.CourseRepository;
import com.example.forohub.domains.topics.validations.ValidatorNewTopical;
import com.example.forohub.domains.topics.validations.ValidatorUpdateTopical;
import com.example.forohub.domains.users.User;
import com.example.forohub.domains.users.UserRepository;
import com.example.forohub.infra.errors.IntegrityValidation;
import com.example.forohub.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class serviceNewTopic {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private List<ValidatorNewTopical> validations;
    @Autowired
    private List<ValidatorUpdateTopical> updateTopicals;
    @Autowired
    private TokenService tokenService;

    public TopicResponseData add(DataTopic data, HttpServletRequest request){
        var user = getAuthenticatedUser(request);
        if(userRepository.findById(user.getId()).isEmpty()){
            throw new IntegrityValidation("Este id para usurio no existe");
        }
        if(!courseRepository.existsById(data.courseId())){
            throw new IntegrityValidation("Este id para el curso no existe");
        }

        validations.forEach(v -> v.validate(data));

        user = userRepository.findById(user.getId()).get();
        var course = courseRepository.findById(data.courseId()).orElse(null);

        var topic = topicRepository.save(new Topic(user, course,data.message(),data.title()));

        return new TopicResponseData(topic);
    }


    public TopicResponseData update(TopicDataUpdate data, HttpServletRequest request) {
        if (data.title() == null && data.message() ==null){
            throw new IntegrityValidation("No hay nada para editar");
        }
        if(!topicRepository.existsById(data.id())){
            throw new IntegrityValidation("No hay topicos con ese id");
        }

        var user = getAuthenticatedUser(request);
        DataTopic dataTopic = new DataTopic(data.title(),data.message(),null);
        validations.forEach(v -> v.validate(dataTopic));
        updateTopicals.forEach(v -> v.validate(data,user));

        var topic = topicRepository.getReferenceById(data.id());
        if(data.title() != null && data.message() !=null){
            topic.setMessage(data.message());
            topic.setTitle(data.title());
        } else if (data.title() == null) {
            topic.setMessage(data.message());
        }
        else {
            topic.setTitle(data.title());
        }

        return new TopicResponseData(topic);
    }

    private User getAuthenticatedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        return (User) userRepository.findByMail(subject);
    }

}
