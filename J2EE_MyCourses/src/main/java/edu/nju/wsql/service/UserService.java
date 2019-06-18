package edu.nju.wsql.service;

import edu.nju.wsql.beans.UserInfoBean;
import edu.nju.wsql.beans.UserStaticsBean;

public interface UserService {
    UserInfoBean getUserInfo(String id);

    void modifyUserInfo(String id, String name, String portrait);

    void unregister(String id);

    UserStaticsBean getUserStatics();
}
