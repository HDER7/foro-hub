package com.example.forohub.domains.answers;

import com.example.forohub.domains.answers.validations.ValidatorUpdateAnswer;
import com.example.forohub.domains.topics.TopicRepository;
import com.example.forohub.domains.users.User;
import com.example.forohub.domains.users.UserRepository;
import com.example.forohub.infra.errors.IntegrityValidation;
import com.example.forohub.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAnswer {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private List<ValidatorUpdateAnswer> validatorUpdateAnswers;

    public Answer add(AnswerData data, HttpServletRequest request,Long id){
        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("Topic not found");
        }
        var user = getAuthenticatedUser(request);
        if(userRepository.findById(user.getId()).isEmpty()){
            throw new IntegrityValidation("Este id para usuario no existe");
        }
        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("Este id para el topico no existe");
        }
        user = userRepository.findById(user.getId()).get();
        var topic = topicRepository.findId(id).orElse(null);

        return answerRepository.save(new Answer(user, topic, data.message()));
    }


    public AnswerResponseData update(AnswerData data, HttpServletRequest request,Long id) {
        if(data.message() == null || data.message().isEmpty()){
            throw new IntegrityValidation("no hay nada para actulizar");
        }
        if(!answerRepository.existsById(id)){
            throw new IntegrityValidation("Este id para la respuesta no existe");
        }
        var user = getAuthenticatedUser(request);
        validatorUpdateAnswers.forEach(v -> v.validate(id, user));

        var answer = answerRepository.getReferenceById(id);
        answer.setMessage(data.message());

        return new AnswerResponseData(answer);
    }

    public Answer delete(Long id, HttpServletRequest request) {
        var user = getAuthenticatedUser(request);
        validatorUpdateAnswers.forEach(v -> v.validate(id, user));
        return answerRepository.getReferenceById(id);
    }

    private User getAuthenticatedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        return (User) userRepository.findByMail(subject);
    }

}
