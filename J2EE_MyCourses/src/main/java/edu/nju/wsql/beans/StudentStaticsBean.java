package edu.nju.wsql.beans;

import edu.nju.wsql.model.User;

import java.util.List;

public class StudentStaticsBean {
    private double undergraduateSelectNum;
    private double graduateSelectNum;
    private double doctorSelectNum;
    private int undergraduateSelectSum;
    private int graduateSelectSum;
    private int doctorSelectSum;

    public StudentStaticsBean() {
    }

    public StudentStaticsBean(List<User> allStudents) {
        undergraduateSelectSum = graduateSelectSum = doctorSelectSum = 0;
        int undergraduateNum = 0, graduateNum = 0, doctorNum = 0;
        for (User student: allStudents) {
            String studentType = student.calcStudentType();
            if (studentType.equals("本科生")) {
                undergraduateNum++;
                undergraduateSelectSum += student.calcSelectSum();
            }
            else if (studentType.equals("研究生")) {
                graduateNum++;
                graduateSelectSum += student.calcSelectSum();
            }
            else if (studentType.equals("博士生")) {
                doctorNum++;
                doctorSelectSum += student.calcSelectSum();
            }
        }
        undergraduateSelectNum = undergraduateNum == 0 ? 0 : 1.0 * undergraduateSelectSum / undergraduateNum;
        graduateSelectNum = graduateNum == 0 ? 0 : 1.0 * graduateSelectSum / graduateNum;
        doctorSelectNum = doctorNum == 0 ? 0 : 1.0 * doctorSelectSum / doctorNum;
    }

    public double getUndergraduateSelectNum() {
        return undergraduateSelectNum;
    }

    public void setUndergraduateSelectNum(double undergraduateSelectNum) {
        this.undergraduateSelectNum = undergraduateSelectNum;
    }

    public double getGraduateSelectNum() {
        return graduateSelectNum;
    }

    public void setGraduateSelectNum(double graduateSelectNum) {
        this.graduateSelectNum = graduateSelectNum;
    }

    public double getDoctorSelectNum() {
        return doctorSelectNum;
    }

    public void setDoctorSelectNum(double doctorSelectNum) {
        this.doctorSelectNum = doctorSelectNum;
    }

    public int getUndergraduateSelectSum() {
        return undergraduateSelectSum;
    }

    public void setUndergraduateSelectSum(int undergraduateSelectSum) {
        this.undergraduateSelectSum = undergraduateSelectSum;
    }

    public int getGraduateSelectSum() {
        return graduateSelectSum;
    }

    public void setGraduateSelectSum(int graduateSelectSum) {
        this.graduateSelectSum = graduateSelectSum;
    }

    public int getDoctorSelectSum() {
        return doctorSelectSum;
    }

    public void setDoctorSelectSum(int doctorSelectSum) {
        this.doctorSelectSum = doctorSelectSum;
    }
}
