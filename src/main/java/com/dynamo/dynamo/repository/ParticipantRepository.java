package com.dynamo.dynamo.repository;


import com.dynamo.dynamo.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository  extends JpaRepository<Participant , Long> {
}
