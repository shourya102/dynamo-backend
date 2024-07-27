package com.dynamo.dynamo.model.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserProfile {

    public static enum Gender {
        MALE, FEMALE
    }

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userProfile")
    private List<Social> socials;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserProfile(String firstName, String lastName, Date dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
}
