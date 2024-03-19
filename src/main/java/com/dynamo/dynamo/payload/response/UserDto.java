package com.dynamo.dynamo.payload.response;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Entity
public class UserDto {
    @Id
    private  Long id;
    @NotBlank
    @Size(max = 20)
    private String username;
}
