package com.dmdev.unit;

import com.dmdev.dto.UserDto;
import com.dmdev.entity.User;
import com.dmdev.exception.ValidationException;
import com.dmdev.validator.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;

@Testable
public class UnitTests {

    @Test
    public void checkEqualityOfUsers0(){
        User user0 = new User();
        Assertions.assertTrue(user0.equals(user0));
    }

    @Test
    public void checkEqualityOfUsersToNull(){
        User user0 = new User();
        Assertions.assertFalse(user0.equals(null));
    }

    @Test
    public void checkEqualityOfUsersDto(){
        UserDto userDto1 = UserDto.builder().build();
        UserDto userDto0 = userDto1;
        Assertions.assertTrue(userDto1.equals(userDto0));
    }

    @Test
    public void checkValidateExceptionClass(){
        Error error = Error.of("Name", "Error");
        ValidationException validationException = new ValidationException(List.of(error));
        Assertions.assertEquals(validationException.getErrors().toArray()[0],
                error);
    }

}
