package com.dmdev.unit.validation;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.util.Util;
import com.dmdev.validator.CreateUserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;


import java.time.LocalDate;

@Testable
public class ValidationTest {


    private CreateUserDto createUserDto;


    private CreateUserValidator createUserValidator;

    @Test
    public void testErrorsAbsence(){
        //data
        createUserDto = Util.buildCreateUserDto("Alex",
                String.valueOf(LocalDate.of(2000, 03, 03)),
                "alex@gmail.com", "1111", "USER", "MALE");

        //test
        Assertions.assertTrue(createUserValidator
                .validate(createUserDto).getErrors().isEmpty());
    }

    @Test
    public void testBirthdayError(){
        //data
        createUserDto = Util.buildCreateUserDto("Alex",
                "o3253k",
                "alex@gmail.com", "1111", "USER", "MALE");

        //test
        Assertions.assertEquals(createUserValidator
                .validate(createUserDto).getErrors().get(0).getCode(),
                "invalid.birthday");
    }

    @Test
    public void testGenderError(){
        //data
        createUserDto = Util.buildCreateUserDto("Alex",
                String.valueOf(LocalDate.of(2000, 03, 03)),
                "alex@gmail.com", "1111", "USER", "MA23LE");

        //test
        Assertions.assertEquals(createUserValidator
                        .validate(createUserDto).getErrors().get(0).getCode(),
                "invalid.gender");
    }

    @Test
    public void testRoleError(){
        //data
        createUserDto = Util.buildCreateUserDto("Alex",
                String.valueOf(LocalDate.of(2000, 03, 03)),
                "alex@gmail.com", "1111", "USseER", "MALE");

        //test
        Assertions.assertEquals(createUserValidator
                        .validate(createUserDto).getErrors().get(0).getCode(),
                "invalid.role");
    }

    @BeforeEach
    private void setUp(){
        createUserValidator = CreateUserValidator.getInstance();
    }

}
