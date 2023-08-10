package com.library.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements IUserDTO{
    private int id;
    private String login;
    private String email;
    private String fullName;
    private String phone;
    private String fineAmount;
    private String status;
    private String role;
    private String subscription;
}
