package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.problem.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsByName(String name);

    Topic findByName(String name);
}
