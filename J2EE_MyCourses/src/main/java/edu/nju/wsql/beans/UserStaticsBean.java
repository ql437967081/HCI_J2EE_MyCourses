package edu.nju.wsql.beans;

import edu.nju.wsql.model.User;

import java.util.List;

public class UserStaticsBean {
    private int teacherNum;
    private int undergraduateNum;
    private int graduateNum;
    private int doctorNum;

    public UserStaticsBean() {
    }

    public UserStaticsBean(List<User> allTeachers, List<User> allStudents) {
        setTeacherNum(allTeachers.size());
        undergraduateNum = graduateNum = doctorNum = 0;
        for (User student: allStudents) {
            String studentType = student.calcStudentType();
            if (studentType.equals("本科生"))
                undergraduateNum++;
            else if (studentType.equals("研究生"))
                graduateNum++;
            else if (studentType.equals("博士生"))
                doctorNum++;
        }
    }

    public int getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(int teacherNum) {
        this.teacherNum = teacherNum;
    }

    public int getUndergraduateNum() {
        return undergraduateNum;
    }

    public void setUndergraduateNum(int undergraduateNum) {
        this.undergraduateNum = undergraduateNum;
    }

    public int getGraduateNum() {
        return graduateNum;
    }

    public void setGraduateNum(int graduateNum) {
        this.graduateNum = graduateNum;
    }

    public int getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(int doctorNum) {
        this.doctorNum = doctorNum;
    }
}
