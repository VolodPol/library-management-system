package com.library.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibrarianDTO implements IUserDTO{
    private int id;
    private String login;
    private String fullName;
    private String email;
    private String phone;
    private int age;
}
