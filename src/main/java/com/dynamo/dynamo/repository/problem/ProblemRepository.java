package com.dynamo.dynamo.repository.problem;

import com.dynamo.dynamo.model.problem.Difficulty;
import com.dynamo.dynamo.model.problem.Problem;
import com.dynamo.dynamo.model.problem.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Boolean existsByName(String name);
    List<Problem> findByDifficultyAndTopic(Difficulty difficulty, Topic topic);

}
