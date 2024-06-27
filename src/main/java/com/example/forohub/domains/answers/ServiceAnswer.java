package com.example.forohub.domains.answers;

import com.example.forohub.domains.answers.validations.ValidatorUpdateAnswer;
import com.example.forohub.domains.topics.TopicRepository;
import com.example.forohub.domains.users.User;
import com.example.forohub.domains.users.UserRepository;
import com.example.forohub.infra.errors.IntegrityValidation;
import com.example.forohub.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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

    public Answer add(AnswerData data, HttpServletRequest request){
        if(!topicRepository.existsById(data.topicId())){
            throw new IntegrityValidation("Topic not found");
        }
        var user = getAuthenticatedUser(request);
        if(userRepository.findById(user.getId()).isEmpty()){
            throw new IntegrityValidation("Este id para usuario no existe");
        }
        if(!topicRepository.existsById(data.topicId())){
            throw new IntegrityValidation("Este id para el topico no existe");
        }
        user = userRepository.findById(user.getId()).get();
        var topic = topicRepository.findId(data.topicId()).orElse(null);

        return answerRepository.save(new Answer(user, topic, data.message()));
    }

    public AnswerResponseData update(AnswerDataUpdate data, HttpServletRequest request) {
        if(data.message() == null || data.message().isEmpty()){
            throw new IntegrityValidation("no hay nada para actulizar");
        }
        if(!answerRepository.existsById(data.id())){
            throw new IntegrityValidation("Este id para la respuesta no existe");
        }
        var user = getAuthenticatedUser(request);
        validatorUpdateAnswers.forEach(v -> v.validate(data, user));

        var answer = answerRepository.getReferenceById(data.id());
        answer.setMessage(data.message());

        return new AnswerResponseData(answer);
    }

//    public void delete(Long id, HttpServletRequest request) {
//        AnswerDataUpdate data = new AnswerDataUpdate(id,null);
//        var user = getAuthenticatedUser(request);
//        validatorUpdateAnswers.forEach(v -> v.validate(data, user));
//        var a =answerRepository.getReferenceById(id);
//        answerRepository.delete(a);
//        answerRepository.flush();
//    }

    private User getAuthenticatedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        return (User) userRepository.findByMail(subject);
    }

}
