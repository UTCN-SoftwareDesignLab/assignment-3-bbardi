package edu.bbardi_softwaredesign.clinic.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String fullname;
    private Set<String> roles;
}