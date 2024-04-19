package com.dynamo.dynamo.services;


import com.dynamo.dynamo.model.Contest;
import com.dynamo.dynamo.model.Participant;
import com.dynamo.dynamo.model.problem.Problem;
import com.dynamo.dynamo.model.user.User;
import com.dynamo.dynamo.payload.request.ContestRequest;
import com.dynamo.dynamo.payload.response.ContestResponse;
import com.dynamo.dynamo.repository.ContestRepository;
import com.dynamo.dynamo.repository.ParticipantRepository;
import com.dynamo.dynamo.repository.problem.ProblemRepository;
import com.dynamo.dynamo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dynamo.dynamo.model.Contest.ContestStatus;

import java.time.LocalTime;
import java.util.*;

@Service
public class ContestService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ParticipantRepository participantRepository;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

public String createContest(ContestRequest contestRequest){
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
    LocalTime now  = LocalTime.now();
    LocalTime contestTime = LocalTime.parse(contestRequest.getStartingTime());
    LocalTime contestEnding = LocalTime.parse(contestRequest.getEndingTime());
    Contest contest = new Contest();

    if(now.isBefore(contestTime))return "Contest not create in past";
    now.plusMinutes(5);
    if (contestTime.isBefore(now))return " contest must start after 5 min !" ;
    if(contestTime.isBefore(contestEnding))return "Differ b/w starting and ending time  must be positive !";
    contest.setStartingTime(contestTime);
    contestTime.plusMinutes(20);
    if(contestTime.isBefore(contestEnding))return "Contest Duration Must more than 20 min";


    contest.setNumberOfParticipants(contestRequest.getNumberOfParticipants());
    contest.setNumberOfProblem(contestRequest.getNumberOfProblem());
    contest.setContestStatus(ContestStatus.UPCOMING);
    contest.setContestId(Long.parseLong(UUID.randomUUID().toString().replace("-", ""), 16));
    contest.setPassword(passwordEncoder.encode(contestRequest.getPassword()));
    contest.setCreationDate(new Date());
    contest.setEndingTime(contestEnding);
    contest.setCreationTime(LocalTime.now());
    contest.setRegisterParticipants(1);
    contest.setContestCreater(user);


    List<String> problemType = contestRequest.getProblemType();
    Set<Problem> problemList = new HashSet<>();
    for(int i=0; i<problemType.size(); i++){

    }





//    contest.setProblemList(new ArrayList<>(problemList));
    Contest contest1  = contestRepository.save(contest);
    contest1.setName("#Contest " + contest1.getId());
    contestRepository.save(contest1);

    return "create contest Successfully !" ;
}


@Scheduled(fixedRate = 60000)
public void updateContestStatus(){
    LocalTime now = LocalTime.now();
    Date curDate = new Date();
    List<Contest> contestList = contestRepository.findAll();
    contestList.forEach(v->{

       LocalTime time = v.getCreationTime();
       Date date = v.getCreationDate();
       int result = curDate.compareTo(date);

        if (result>=0) {
            LocalTime startingTime  = v.getStartingTime();
            LocalTime endingTime   = v.getEndingTime();
           if (now.isAfter(startingTime)) {

               if (now.isAfter(endingTime)) {
                   v.setContestStatus(ContestStatus.CLOSED);
                   contestRepository.save(v);
               } else {
                   v.setContestStatus(ContestStatus.ONGOING);
                   contestRepository.save(v);

               }
           }
       }
    });
}
public String ContestRegistration(Long key ){
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
    Optional<Contest> contest = contestRepository.findByContestId(key);
if(contest.isPresent()) {
    if (contest.get().getContestStatus() == ContestStatus.UPCOMING) {
        if (contest.get().getNumberOfParticipants() > contest.get().getRegisterParticipants()){
            contest.get().setRegisterParticipants(contest.get().getRegisterParticipants()+1);
            Contest contest1 = contestRepository.save(contest.get());
            Participant participant = new Participant();
            participant.setUser(user);
            participant.setContest(contest1);
            participantRepository.save(participant);
            return "Successfully Register !";

        }
    }
}



    return "seat full on  contest !";
}


public ContestResponse joining(Long key , String password){

    Optional<Contest> contest = contestRepository.findByContestId(key);
    if(passwordEncoder.matches(password , contest.get().getPassword())){
        ContestResponse contestResponse = new ContestResponse();
        contestResponse.setName(contest.get().getName());
        contestResponse.setContestCreater(contest.get().getContestCreater());
        contestResponse.setContestStatus(contest.get().getContestStatus());
        contestResponse.setCreationTime(contest.get().getCreationTime());
        contestResponse.setCreationDate(contest.get().getCreationDate());
        contestResponse.setProblemList(contest.get().getProblemList());
        contestResponse.setRegisterParticipants(contest.get().getRegisterParticipants());
        contestResponse.setNumberOfParticipants(contest.get().getNumberOfParticipants());
        contestResponse.setNumberOfProblem(contest.get().getNumberOfProblem());
        return contestResponse;
    }

    return  null;
}



}
