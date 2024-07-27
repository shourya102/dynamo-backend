package com.dynamo.dynamo.payload.request;

import com.dynamo.dynamo.model.user.UserProfile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private UserProfile.Gender gender;
    private List<String> types;
    private List<String> urls;

}
