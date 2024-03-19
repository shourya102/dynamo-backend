package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.RefreshToken;
import com.dynamo.dynamo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    @Modifying
    int deleteByUser(User user);

    Boolean existsByUser(User user);
}
