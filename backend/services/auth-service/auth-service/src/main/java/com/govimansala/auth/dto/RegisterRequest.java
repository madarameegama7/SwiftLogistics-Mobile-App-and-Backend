package com.govimansala.auth.dto;

import com.govimansala.auth.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Role role;
}