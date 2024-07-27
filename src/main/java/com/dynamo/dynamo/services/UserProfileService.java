package com.dynamo.dynamo.services;

import com.dynamo.dynamo.exceptions.UserNotFoundException;
import com.dynamo.dynamo.model.user.Social;
import com.dynamo.dynamo.model.user.User;
import com.dynamo.dynamo.model.user.UserProfile;
import com.dynamo.dynamo.payload.request.UserProfileDto;
import com.dynamo.dynamo.repository.problem.SocialRepository;
import com.dynamo.dynamo.repository.problem.UserProfileRepository;
import com.dynamo.dynamo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    SocialRepository socialRepository;
    @Autowired
    UserRepository userRepository;

    public UserProfile getProfile(Long id) {
        return userProfileRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserProfile updateProfile(Long id, UserProfileDto userProfileDto) {
        UserProfile userProfile = UserProfile.builder().firstName(userProfileDto.getFirstName()).lastName(userProfileDto.getLastName())
                .gender(userProfileDto.getGender()).dateOfBirth(userProfileDto.getDateOfBirth())
                .build();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        List<Social> socials = new ArrayList<>();
        Iterator<String> iterator = userProfileDto.getUrls().iterator();
        userProfileDto.getTypes().forEach(i -> {
            socials.add(new Social(Social.SocialType.valueOf(i), iterator.next()));
        });
        userProfile.setSocials(socials);
        userProfile.setUser(user);
        return userProfileRepository.save(userProfile);
    }
}
