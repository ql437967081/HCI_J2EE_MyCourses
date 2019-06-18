package edu.nju.wsql.model;

import edu.nju.wsql.model.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private String id;
    private String password;
    private String name;
    private UserType type;
    private String email;
    private String portrait;
    private boolean valid;
    private Set<Course> courses;
    private Set<SelectCourse> selectCourses;

    public User() {
    }

    @Id
    @Column(updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(updatable = false)
    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Column(updatable = false)
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @OneToMany(mappedBy = "creator")
    @OrderBy(value = "id ASC")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @OneToMany(mappedBy = "student")
    @OrderBy(value = "id ASC")
    public Set<SelectCourse> getSelectCourses() {
        return selectCourses;
    }

    public void setSelectCourses(Set<SelectCourse> selectCourses) {
        this.selectCourses = selectCourses;
    }

    public String calcStudentType() {
        if (type != UserType.STUDENT)
            return "不是学生";
        char first = id.toCharArray()[0];
        if (first >= '0' && first <= '9')
            return "本科生";
        if (first == 'M')
            return "研究生";
        if (first == 'D')
            return "博士生";
        return "无效的学生类型";
    }

    public int calcSelectSum() {
        int sum = 0;
        for (SelectCourse selectCourse: selectCourses) {
            if (selectCourse.getQuitTime() == null)
                sum++;
        }
        return sum;
    }
}
