package edu.nju.wsql.beans;

import edu.nju.wsql.model.User;

public class UserInfoBean {
    private String id;
    private String name;
    private String email;
    private String portrait;

    public UserInfoBean() {
    }

    public UserInfoBean(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        portrait = "/img/portrait/" + user.getPortrait();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
