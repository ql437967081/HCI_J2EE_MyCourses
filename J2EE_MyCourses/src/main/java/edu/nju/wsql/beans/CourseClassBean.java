package edu.nju.wsql.beans;

import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.SelectCourse;

public class CourseClassBean {
    private long id;
    private int classId;
    private int selectedNum;
    private int limitNum;

    public CourseClassBean() {
    }

    public CourseClassBean(CourseClass courseClass) {
        setId(courseClass.getId());
        setClassId(courseClass.getClassId());
        selectedNum = courseClass.calcSelectedNum();
        setLimitNum(courseClass.getLimitNum());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSelectedNum() {
        return selectedNum;
    }

    public void setSelectedNum(int selectedNum) {
        this.selectedNum = selectedNum;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }
}
