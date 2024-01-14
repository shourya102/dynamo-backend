package com.dynamo.dynamo.services;

import com.dynamo.dynamo.exceptions.EnumNotFoundException;
import com.dynamo.dynamo.model.Difficulty;
import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.model.ProblemDetails;
import com.dynamo.dynamo.model.Topic;
import com.dynamo.dynamo.payload.response.MessageResponse;
import com.dynamo.dynamo.repository.ProblemDetailsRepository;
import com.dynamo.dynamo.repository.ProblemRepository;
import com.dynamo.dynamo.repository.TopicRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProblemService {

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemDetailsRepository problemDetailsRepository;

    @Autowired
    TopicRepository topicRepository;

    public ResponseEntity<?> saveProblem(String name, String difficulty, String problemDescription, Set<String> topics) {
        if (problemRepository.existsByName(name))
            return ResponseEntity.badRequest().body(new MessageResponse("Name is already taken"));
        if (!Difficulty.contains(difficulty))
            throw new EnumNotFoundException();
        Problem problem = new Problem(name,
                Difficulty.valueOf(difficulty),
                0, 0, 0, 0);
        String cleaned = Jsoup.clean(problemDescription, Safelist.basic());
        ProblemDetails description = new ProblemDetails();
        description.setDescription(cleaned);
        description.setProblem(problem);
        problem.setProblemDetails(description);
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
}
