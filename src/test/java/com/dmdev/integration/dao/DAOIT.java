package com.dmdev.integration.dao;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.util.LocalDateFormatter;
import com.dmdev.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DAOIT extends IntegrationTestBase {

    private UserDao userDao;

    @BeforeEach
    public void initializingUserDAO(){
        userDao = UserDao.getInstance();
    }

    @Test
    public void checkGettingFromTable() {
        //test
        Assertions.assertEquals(userDao.findById(1).stream().findFirst().get().getEmail(),
                "ivan@gmail.com");
    }

    @Test
    public void checkGettingFromTableByEmailAndPassword() {
        //test
        Assertions.assertEquals(userDao.
                findByEmailAndPassword("sveta@gmail.com", "321")
                .stream().findFirst().get()
                .getName(), "Sveta");
    }

    @Test
    public void checkGettingAllFromTable() {
        //data
        List<String> expected = new ArrayList<> (List.of("ivan@gmail.com", "petr@gmail.com",
                "sveta@gmail.com", "vlad@gmail.com", "kate@gmail.com"));
        List<User> actual = userDao.findAll();
        List<String> actualEmailList = new ArrayList<>(actual.stream()
                .map(User::getEmail)
                .toList());
        Collections.sort(actualEmailList);
        Collections.sort(expected);

        //test
        Assertions.assertEquals(actualEmailList, expected);
    }

    @Test
    public void checkDeletingFromTable() {
        //data
        userDao.delete(1);
        userDao.delete(2);
        userDao.delete(4);

        //test
        List<User> userList = userDao.findAll();
        Assertions.assertEquals(userList.size(), 2);
        Assertions.assertEquals(userList
                .stream().filter(x -> x.getId() == 3).findFirst().get()
                .getEmail(), "sveta@gmail.com");
    }

    @Test
    public void checkSavingRawInTable() {
        //data
        User user = Util.buildUser(null, null,
                LocalDateFormatter.format("1995-10-19"),"masha@gmail.com",
                "1234", Role.USER, null);
        userDao.save(user);

        //test
        Assertions.assertEquals(userDao.findAll()
                .stream().filter(x -> x.getId() == 6).findFirst().get()
                .getEmail(), "masha@gmail.com");

    }

    @Test
    public void checkUpdatingRawInTable() {
        //data
        User user = Util.buildUser(1, null,
                LocalDateFormatter.format("1995-10-19"),"masha@gmail.com",
                "1234", Role.USER, null);
        userDao.update(user);

        //test
        Assertions.assertEquals(userDao.findAll()
                .stream().filter(x -> x.getId() == 1).findFirst().get()
                .getEmail(), "masha@gmail.com");

    }
}
