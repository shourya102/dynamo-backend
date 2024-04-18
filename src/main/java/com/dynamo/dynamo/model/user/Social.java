package com.dynamo.dynamo.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Social {
    public static enum SocialType {
        FACEBOOK, LINKEDIN, GITHUB, GOOGLE
    }
    @Id
    private Long id;
    private SocialType type;
    private String url;
    @ManyToOne
    private UserProfile userProfile;
}
