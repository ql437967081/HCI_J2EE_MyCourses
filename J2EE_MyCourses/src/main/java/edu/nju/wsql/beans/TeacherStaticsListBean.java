package edu.nju.wsql.beans;

import java.util.List;

public class TeacherStaticsListBean {
    private List<TeacherStaticsBean> list;

    public TeacherStaticsListBean() {
    }

    public TeacherStaticsListBean(List<TeacherStaticsBean> list) {
        this.list = list;
    }

    public List<TeacherStaticsBean> getList() {
        return list;
    }

    public void setList(List<TeacherStaticsBean> list) {
        this.list = list;
    }
}
