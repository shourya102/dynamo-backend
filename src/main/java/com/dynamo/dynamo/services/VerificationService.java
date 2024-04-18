package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.auth.VerificationToken;
import com.dynamo.dynamo.model.user.User;
import com.dynamo.dynamo.payload.response.MessageResponse;
import com.dynamo.dynamo.repository.user.UserRepository;
import com.dynamo.dynamo.repository.auth.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class VerificationService {

    @Autowired
    VerificationTokenRepository repository;

    @Autowired
    UserRepository userRepository;

    public VerificationToken createVerification(User user) {
        VerificationToken verificationToken = new VerificationToken();
        String token = UUID.randomUUID().toString();
        verificationToken.setToken(token);
        verificationToken.setExpirationDate(new Date(new Date().getTime() + 600000));
        verificationToken.setUser(user);
        verificationToken = repository.save(verificationToken);
        return verificationToken;
    }

    public ResponseEntity<?> verifyUser(String token) {
        VerificationToken verificationToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token is incorrect or not found."));
        User user = verificationToken.getUser();
        user.setEnabled(true);
        repository.deleteByUser(user);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("User has been verified"));
    }

    @Transactional
    @Scheduled(fixedRate = 600000)
    public void deleteExpiredTokens() {
        repository.deleteAllByExpirationDateBefore(new Date());
    }
}
