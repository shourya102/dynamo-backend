package com.dynamo.dynamo.services;

import com.dynamo.dynamo.exceptions.EnumNotFoundException;
import com.dynamo.dynamo.model.*;
import com.dynamo.dynamo.payload.response.MessageResponse;
import com.dynamo.dynamo.repository.ProblemDetailsRepository;
import com.dynamo.dynamo.repository.ProblemRepository;
import com.dynamo.dynamo.repository.TopicRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProblemService {

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
                Difficulty.valueOf(difficulty),
                0, 0, 0, 0);
        String cleaned = Jsoup.clean(problemDescription, Safelist.basic());
        if (!Type.contains(returnType))
            throw new EnumNotFoundException();
        ProblemDetails details = new ProblemDetails(cleaned, methodName, Type.valueOf(returnType));
        details.setProblem(problem);
        List<Parameter> parameterList = new ArrayList<>();
        for (int i = 0; i < parameterNames.size(); i++) {
            Parameter parameter = new Parameter(parameterNames.get(i),
                    Type.valueOf(parameterTypes.get(i)));
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
