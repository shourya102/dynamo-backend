package com.dynamo.dynamo.services;


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
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class ContestService {

//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ContestRepository contestRepository;
//    @Autowired
//    ParticipantRepository participantRepository;
//    @Autowired
//    ProblemRepository problemRepository;
//
//public String createContest(ContestRequest contestRequest){
//    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
//    Contest contest = new Contest();
//    contest.setNumberOfParticipants(contestRequest.getNumberOfParticipants());
//    contest.setNumberOfProblem(contestRequest.getNumberOfProblem());
//    contest.setContestStatus(ContestStatus.UPCOMING);
//    contest.setKey(Long.parseLong(UUID.randomUUID().toString().replace("-", ""), 16));
//    contest.setPassword(contestRequest.getPassword());
//    contest.setCreationDate(new Date());
//    contest.setDurationHour(contestRequest.getDurationHour());
//    contest.setDurationMin(contestRequest.getDurationMin());
//    contest.setStartingHour(contestRequest.getStartingHour());
//    contest.setStartingMin(contestRequest.getStartingMin());
//    contest.setCreationTime(LocalTime.now());
////    contest.setContestCreater(user);
//
//
//    List<String> problemType = contestRequest.getProblemType();
//    Set<Problem> problemList = new HashSet<>();
//    for(int i=0; i<problemType.size(); i++){
//
//    }
//
//
//
//
//
////    contest.setProblemList(new ArrayList<>(problemList));
//    Contest contest1  = contestRepository.save(contest);
//    contest1.setName("#Contest " + contest1.getId());
//    contestRepository.save(contest1);
//
//    return "create contest Successfully !" ;
//}
//
//
//@Scheduled(fixedRate = 60000)
//public String updateContestStatus(){
//    LocalTime now = LocalTime.now();
//    List<Contest> contestList = contestRepository.findAll();
//    contestList.forEach(v->{
//       LocalTime time = v.getCreationTime();
//       time.plusHours(v.getStartingHour());
//       time.plusMinutes(v.getStartingMin());
//       if(time.isAfter(now)){
//           time.plusHours(v.getDurationHour());
//           time.plusMinutes(v.getDurationMin());
//           if(time.isAfter(now)){
//               v.setContestStatus(ContestStatus.CLOSED);
//               contestRepository.save(v);
//           }else {
//               v.setContestStatus(ContestStatus.ONGOING);
//               contestRepository.save(v);
//
//           }
//       }
//    });
//
//
//    return "upadate Contests status ";
//}
//public String ContestRegistration(Long key , String password){
//
//
//
//
//
//    return "Successfully Register !";
//}
//
//
//public ContestResponse joining(Contest contest){
//
//
//    return  null;
//}
//


}
