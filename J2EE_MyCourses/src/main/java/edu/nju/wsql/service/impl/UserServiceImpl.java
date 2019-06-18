package edu.nju.wsql.service.impl;

import edu.nju.wsql.beans.UserInfoBean;
import edu.nju.wsql.beans.UserStaticsBean;
import edu.nju.wsql.dao.UserDao;
import edu.nju.wsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserInfoBean getUserInfo(String id) {
        return new UserInfoBean(userDao.findOne(id));
    }

    @Override
    @Transactional
    public void modifyUserInfo(String id, String name, String portrait) {
        if (! name.equals(userDao.findName(id)))
            userDao.updateName(id, name);
        if (portrait != null)
            userDao.updatePortrait(id, portrait);
    }

    @Override
    @Transactional
    public void unregister(String id) {
        userDao.invalidate(id);
    }

    @Override
    @Transactional
    public UserStaticsBean getUserStatics() {
        return new UserStaticsBean(userDao.getAllTeachers(), userDao.getAllStudents());
    }
}
