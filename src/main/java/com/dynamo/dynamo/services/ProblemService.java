package com.dynamo.dynamo.services;

import com.dynamo.dynamo.exceptions.EnumNotFoundException;
import com.dynamo.dynamo.model.*;
import com.dynamo.dynamo.model.problem.*;
import com.dynamo.dynamo.model.user.User;
import com.dynamo.dynamo.payload.request.SubmitRequest;
import com.dynamo.dynamo.payload.response.MessageResponse;
import com.dynamo.dynamo.payload.response.ProblemResponse;
import com.dynamo.dynamo.repository.*;
import com.dynamo.dynamo.repository.problem.ProblemDetailsRepository;
import com.dynamo.dynamo.repository.problem.ProblemRepository;
import com.dynamo.dynamo.repository.problem.TopicRepository;
import com.dynamo.dynamo.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

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
    @Autowired
    JavaCodeTesterService javaCodeTesterService;
    ModelMapper modelMapper = new ModelMapper();

    private static final String TEMP_DIR = System.getProperty("user.dir") + "/src/main/java/com/dynamo/dynamo/temp";


    public ResponseEntity<?> saveProblem(String name, String difficulty, String problemDescription, String returnType,
                                         String methodName, List<String> parameterNames, List<String> parameterTypes, Set<String> topics,
                                         List<String> inputList, List<String> outputList, String testCode) {
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
        List<TestCase> testCases = new ArrayList<>();
        IntStream.range(0, inputList.size()).forEach(i -> {
            TestCase testCase = new TestCase(inputList.get(i), outputList.get(i), details);
            testCases.add(testCase);
        });
        details.setTestCases(testCases);
        details.setTestCode(testCode);
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

    public List<Boolean> getResults(SubmitRequest submitRequest) {
        ProblemDetails problemDetails = problemDetailsRepository.findByProblemId(submitRequest.getProblemId()).get();
        List<String> paramTypes = problemDetails.getParameters().stream().map(i -> i.getType().name()).toList();
        List<TestCase> testCases = problemDetails.getTestCases();
        List<Boolean> resultList = new ArrayList<>();
        try {
            Path tempDirPath = Paths.get(TEMP_DIR);
            Path tempFile = Files.createTempFile(tempDirPath, "Tester", ".java");
            try(FileWriter writer = new FileWriter(tempFile.toFile())) {
                writer.write(submitRequest.getCode());
            }
            testCases.forEach(testCase -> {
                resultList.add(javaCodeTesterService.getResult(testCase.getInputs(), testCase.getOutput(), tempFile, paramTypes));
            });
            Files.delete(tempFile);
        }
       catch (Exception e) {
            logger.error(e.toString());
       }
        return  resultList;
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

    public List<ProblemResponse> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        List<ProblemResponse> problemResponses  = problems.stream().map(i -> modelMapper.map(i, ProblemResponse.class)).toList();
        return problemResponses;
    }
}
