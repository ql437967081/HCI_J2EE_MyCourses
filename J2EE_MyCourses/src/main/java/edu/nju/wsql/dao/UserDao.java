package edu.nju.wsql.dao;

import edu.nju.wsql.model.User;

import java.util.List;

public interface UserDao {
    User findOne(String id);

    void save(User user);

    void validate(String id);

    void update(String id, String password, String name);

    String findName(String id);

    void updateName(String id, String name);

    void updatePortrait(String id, String portrait);

    void invalidate(String id);

    List<Object[]> findAllTeachers();

    List<User> getAllTeachers();

    List<User> getAllStudents();
}
