package com.dynamo.dynamo.services;

import com.dynamo.dynamo.exceptions.EnumNotFoundException;
import com.dynamo.dynamo.model.problem.*;
import com.dynamo.dynamo.payload.response.MessageResponse;
import com.dynamo.dynamo.repository.problem.ProblemDetailsRepository;
import com.dynamo.dynamo.repository.problem.ProblemRepository;
import com.dynamo.dynamo.repository.problem.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProblemService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemDetailsRepository problemDetailsRepository;

    @Autowired
    TopicRepository topicRepository;

    public ResponseEntity<?> saveProblem(String name, String difficulty, String problemDescription, String returnType,
                                         String methodName, List<String> parameterNames, List<String> parameterTypes, Set<String> topics) {

        if (problemRepository.existsByName(name))
            return ResponseEntity.badRequest().body(new MessageResponse("Name is already taken"));
        if (!Difficulty.contains(difficulty))
            throw new EnumNotFoundException();
        Problem problem = new Problem(name,
                Difficulty.valueOf(difficulty));
//        String cleaned = Jsoup.clean(problemDescription, Safelist.basic());
        if (!EType.contains(returnType))
            throw new EnumNotFoundException();
        ProblemDetails details = new ProblemDetails(problemDescription, methodName, EType.valueOf(returnType));
        details.setProblem(problem);
        List<Parameter> parameterList = new ArrayList<>();
        for (int i = 0; i < parameterNames.size(); i++) {
            Parameter parameter = new Parameter(parameterNames.get(i),
                    EType.valueOf(parameterTypes.get(i)));
            parameter.setProblemDetails(details);
            parameterList.add(parameter);
        }
        details.setParameters(parameterList);
        problem.setProblemDetails(details);
        Set<Topic> topicSet = new HashSet<>();
        topics.forEach(i -> {
            Topic topic;
            if (!topicRepository.existsByName(i)) {
                topic = new Topic(i);
                topicRepository.save(topic);
            } else {
                topic = topicRepository.findByName(i);
            }
            topicSet.add(topic);
        });
        problem.setTopic(topicSet);
        problemRepository.save(problem);
        return ResponseEntity.ok(new MessageResponse("Problem has been saved successfully!"));
    }

    public ResponseEntity<?> generateCodeSkeleton() {

        return ResponseEntity.ok(new MessageResponse("aa"));
    }
}
