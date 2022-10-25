package com.dmdev.util;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;

import java.time.LocalDate;

public class Util{

    public static User buildUser(Integer id, String name, LocalDate birthday,
                                 String email, String password,
                                 Role role, Gender gender) {
       return User.builder()
               .id(id)
               .name(name)
               .birthday(birthday)
               .email(email)
               .password(password)
               .role(role)
               .gender(gender)
               .build();
    }

    public static CreateUserDto buildCreateUserDto( String name, String birthday,
                                                   String email, String password,
                                                   String role, String gender) {
        return CreateUserDto.builder()
                .name(name)
                .birthday(birthday)
                .email(email)
                .password(password)
                .role(role)
                .gender(gender)
                .build();
    }
}
