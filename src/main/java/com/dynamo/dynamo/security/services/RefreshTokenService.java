package com.dynamo.dynamo.security.services;

import com.dynamo.dynamo.exceptions.TokenRefreshException;
import com.dynamo.dynamo.model.auth.RefreshToken;
import com.dynamo.dynamo.model.user.User;
import com.dynamo.dynamo.repository.auth.RefreshTokenRepository;
import com.dynamo.dynamo.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public Optional<RefreshToken> findByUser(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        System.out.println(userId);
        User user = userRepository.findById(userId).get();
        if (refreshTokenRepository.existsByUser(user))
            return refreshTokenRepository.deleteByUser(user);
        return 0;
    }

    @Transactional
    @Scheduled(fixedRate = 600000)
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteAllByExpiryDateBefore(new Date().toInstant());
    }
}
