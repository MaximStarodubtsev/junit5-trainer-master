package com.dmdev.integration;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Arrays;
import java.util.Collections;

@Testable
public class IntegrationTest extends IntegrationTestBase {

    @Test
    public void checkGettingFromTable(){
        UserDao userDao = UserDao.getInstance();
        Assertions.assertEquals(userDao.findById(1).stream().findFirst().get().getName(),
                "Ivan");
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
        String[] array = {"Ivan", "Petr", "Sveta", "Vlad", "Kate"};
        Object[] stringArray = userDao.findAll().toArray();

        Assertions.assertEquals(((User)stringArray[0]).getName(), array[0]);
        Assertions.assertEquals(((User)stringArray[1]).getName(), array[1]);
        Assertions.assertEquals(((User)stringArray[2]).getName(), array[2]);
        Assertions.assertEquals(((User)stringArray[3]).getName(), array[3]);
        Assertions.assertEquals(((User)stringArray[4]).getName(), array[4]);

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
                .getName(), "Sveta");
        Assertions.assertEquals(userDao.findAll()
                .stream().filter(x->x.getId()==5).findFirst().get()
                .getName(), "Kate");
    }
}
