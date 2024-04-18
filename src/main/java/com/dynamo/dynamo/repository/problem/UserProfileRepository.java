package com.dynamo.dynamo.repository.problem;

import com.dynamo.dynamo.model.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
