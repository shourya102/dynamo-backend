package com.dynamo.dynamo.services;

import com.dynamo.dynamo.exceptions.EnumNotFoundException;
import com.dynamo.dynamo.model.*;
import com.dynamo.dynamo.payload.response.MessageResponse;
import com.dynamo.dynamo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemDetailsRepository problemDetailsRepository;

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProblemLikesRepository problemLikesRepository;

    public ResponseEntity<?> saveProblem(String name, String difficulty, String problemDescription, String returnType,
                                         String methodName, List<String> parameterNames, List<String> parameterTypes, Set<String> topics) {
        if (problemRepository.existsByName(name))
            return ResponseEntity.badRequest().body(new MessageResponse("Name is already taken"));
        if (!Difficulty.contains(difficulty))
            throw new EnumNotFoundException();
        Problem problem = new Problem(name,
                Difficulty.valueOf(difficulty),
                0, 0, 0, 0);
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



    public String toggleLikeOnProblem(Long problem_id) {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Problem problem = problemRepository.findById(problem_id).orElseThrow(()->new RuntimeException("Problem are not found" +problem_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<ProblemLikes> islikes = problemLikesRepository.findByUserAndProblem(user ,problem);
        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)){
            problem.setLikes(problem.getLikes()-1);
            problemLikesRepository.delete(islikes.get());
            problemRepository.save(problem);
            return " remove like on problem id "+problem_id +" by user name " +userDetails.getUsername();
        }
        else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)) {
            problem.setLikes(problem.getLikes()+1);
            problem.setDislikes(problem.getDislikes()-1);
            problemRepository.save(problem);
            islikes.get().setLikeStatus(LikeStatus.LIKE);
            problemLikesRepository.save(islikes.get());
        }
        else {
            problem.setLikes(problem.getLikes()+1);
            Problem problem1= problemRepository.save(problem);
            ProblemLikes problemLikes = new ProblemLikes();
            problemLikes.setProblem(problem1);
            problemLikes.setUser(user);
            problemLikes.setLikeStatus(LikeStatus.LIKE);
            problemLikesRepository.save(problemLikes);
        }
        return "like on problem id "+problem_id +" by user name " +userDetails.getUsername();
    }



    public String toggleDisLikeOnProblem(Long problem_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Problem problem = problemRepository.findById(problem_id).orElseThrow(()->new RuntimeException("Problem are not found" +problem_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        Optional<ProblemLikes> islikes = problemLikesRepository.findByUserAndProblem(user ,problem);

        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)){
            problem.setDislikes(problem.getDislikes()-1);
            problemLikesRepository.delete(islikes.get());
            problemRepository.save(problem);
            return "remove dislike on problem id "+problem_id +" by user name " +userDetails.getUsername();

        } else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)) {
            problem.setLikes(problem.getLikes()-1);
            problem.setDislikes(problem.getDislikes()+1);
            problemRepository.save(problem);
            islikes.get().setLikeStatus(LikeStatus.DISLIKE);
            problemLikesRepository.save(islikes.get());
        } else {
            problem.setDislikes(problem.getDislikes()+1);
            Problem problem1= problemRepository.save(problem);
            ProblemLikes problemLikes = new ProblemLikes();
            problemLikes.setProblem(problem1);
            problemLikes.setUser(user);
            problemLikes.setLikeStatus(LikeStatus.DISLIKE);
            problemLikesRepository.save(problemLikes);
        }
        return "Dislike on problem id "+problem_id +" by user name " +userDetails.getUsername();
    }

    public String isLikeOrDisLikeOrNormal(Long problem_id ){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Problem problem = problemRepository.findById(problem_id).orElseThrow(()->new RuntimeException("Problem are not found" +problem_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        Optional<ProblemLikes> islikes = problemLikesRepository.findByUserAndProblem(user ,problem);
        if(islikes.isPresent()){
            return islikes.get().getLikeStatus().toString();
        }
        return "NORMAL";
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }
}
