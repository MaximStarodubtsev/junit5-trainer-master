package com.dmdev.integration;

import com.dmdev.dao.UserDao;
import com.dmdev.dto.CreateUserDto;
import com.dmdev.dto.UserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.mapper.CreateUserMapper;
import com.dmdev.mapper.UserMapper;
import com.dmdev.service.UserService;
import com.dmdev.util.LocalDateFormatter;
import com.dmdev.validator.CreateUserValidator;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Optional;

@Testable
public class InstanceOfIT extends IntegrationTestBase {

    @Test
    public void checkGettingFromTable(){
        UserDao userDao = UserDao.getInstance();
        Assertions.assertEquals(userDao.findById(1).stream().findFirst().get().getEmail(),
                "ivan@gmail.com");
    }

    @Test
    public void checkGettingFromTableByEmailAndPassword(){
        UserDao userDao = UserDao.getInstance();
        Assertions.assertEquals(userDao.
                findByEmailAndPassword("sveta@gmail.com", "321")
                .stream().findFirst().get()
                .getName(), "Sveta");
    }

    @Test
    public void checkGettingAllFromTable(){
        UserDao userDao = UserDao.getInstance();
        String[] array = {"ivan@gmail.com", "petr@gmail.com",
                "sveta@gmail.com", "vlad@gmail.com", "kate@gmail.com"};
        Object[] stringArray = userDao.findAll().toArray();

        Assertions.assertEquals(((User)stringArray[0]).getEmail(), array[0]);
        Assertions.assertEquals(((User)stringArray[1]).getEmail(), array[1]);
        Assertions.assertEquals(((User)stringArray[2]).getEmail(), array[2]);
        Assertions.assertEquals(((User)stringArray[3]).getEmail(), array[3]);
        Assertions.assertEquals(((User)stringArray[4]).getEmail(), array[4]);

    }

    @Test
    public void checkDeletingFromTable(){
        UserDao userDao = UserDao.getInstance();
        userDao.delete(1);
        userDao.delete(2);
        userDao.delete(4);

        Assertions.assertEquals(userDao.findAll().toArray().length, 2);
        Assertions.assertEquals(userDao.findAll()
                .stream().filter(x->x.getId()==3).findFirst().get()
                .getEmail(), "sveta@gmail.com");
    }

    @Test
    public void checkSavingInTable(){
        UserDao userDao = UserDao.getInstance();
        User user = User.builder()
                .birthday(LocalDateFormatter.format("1995-10-19"))
                .email("masha@gmail.com")
                        .password("1234")
                                .role(Role.USER).build();
        userDao.save(user);

        Assertions.assertEquals(userDao.findAll()
                .stream().filter(x->x.getId()==6).findFirst().get()
                .getEmail(), "masha@gmail.com");

    }

    @Test
    public void checkUpdatingRawInTable(){
        UserDao userDao = UserDao.getInstance();
        User user = User.builder()
                .id(1)
                .birthday(LocalDateFormatter.format("1995-10-19"))
                .email("masha@gmail.com")
                .password("1234")
                .role(Role.USER).build();
        userDao.update(user);
        Assertions.assertEquals(userDao.findAll()
                .stream().filter(x->x.getId()==1).findFirst().get()
                .getEmail(), "masha@gmail.com");

    }

    @Test
    public void mappingUser(){
        User user = User.builder()
                .id(1)
                .name("Max")
                .email("max@gmail.com")
                .password("1234")
                .birthday(LocalDateFormatter.format("1994-02-03"))
                .role(Role.USER)
                .gender(Gender.MALE)
                .build();
        UserMapper userMapper = UserMapper.getInstance();
        UserDto userDto = userMapper.map(user);
        Assertions.assertEquals(userDto.getId(), 1);
        Assertions.assertEquals(userDto.getName(), "Max");
        Assertions.assertEquals(userDto.getEmail(), "max@gmail.com");
        Assertions.assertEquals(userDto.getBirthday().toString(), "1994-02-03");
        Assertions.assertEquals(userDto.getRole(), Role.USER);
        Assertions.assertEquals(userDto.getGender(), Gender.MALE);
    }

    @Test
    public void checkCreateUserMapper(){
        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("Max")
                .email("max@gmail.com")
                .birthday("1994-02-03")
                .role(Role.USER.toString())
                .gender(Gender.MALE.toString())
                .password("1234")
                .build();
        CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
        User user = createUserMapper.map(createUserDto);
        Assertions.assertEquals(user.getName(), "Max");
        Assertions.assertEquals(user.getEmail(), "max@gmail.com");
        Assertions.assertEquals(user.getBirthday().toString(), "1994-02-03");
        Assertions.assertEquals(user.getRole(), Role.USER);
        Assertions.assertEquals(user.getGender(), Gender.MALE);
    }

    @Test
    public void checkServiceCreate(){
        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("Max")
                .email("max@gmail.com")
                .birthday("1994-02-03")
                .role(Role.USER.toString())
                .gender(Gender.MALE.toString())
                .password("1234")
                .build();
        UserService userService = UserService.getInstance();
        UserDto userDto = userService.create(createUserDto);

        Assertions.assertEquals(userDto.getName(), createUserDto.getName());
        Assertions.assertEquals(userDto.getEmail(), createUserDto.getEmail());
        Assertions.assertEquals(userDto.getBirthday().toString(),
                createUserDto.getBirthday());
        Assertions.assertEquals(userDto.getRole().toString(),
                createUserDto.getRole());
        Assertions.assertEquals(userDto.getGender().toString(),
                createUserDto.getGender());

    }

    @Test
    public void checkServiceLogin(){
        UserService service = UserService.getInstance();
        Optional<UserDto> userDto = service.login("ivan@gmail.com", "111");
        Assertions.assertEquals(userDto.stream().findFirst().get().getEmail(),
                "ivan@gmail.com");
    }

    @Test
    public void checkValidateMethod(){
        CreateUserDto createUserDto = CreateUserDto.builder()
                .build();
        Assertions.assertEquals(CreateUserValidator.getInstance()
                        .validate(createUserDto).getErrors().isEmpty(), false);
    }
}
