package com.dynamo.dynamo.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Social {

    public Social(SocialType type, String url) {
        this.type = type;
        this.url = url;
    }

    public static enum SocialType {
        FACEBOOK, LINKEDIN, GITHUB, GOOGLE
    }
    @Id
    private Long id;
    private SocialType type;
    private String url;
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
}
