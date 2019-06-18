package edu.nju.wsql.beans;

import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.SelectCourse;

import java.util.ArrayList;
import java.util.List;

public class SelectCourseListBean {
    private List<SelectCourseBean> selectCourseBeans;
    private int classId;
    private String name;
    private String term;

    public SelectCourseListBean() {
    }

    public List<SelectCourseBean> getSelectCourseBeans() {
        return selectCourseBeans;
    }

    public void setSelectCourseBeans(List<SelectCourseBean> selectCourseBeans) {
        this.selectCourseBeans = selectCourseBeans;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setCourseClass(CourseClass courseClass) {
        setClassId(courseClass.getClassId());
        PublishCourse publishCourse = courseClass.getPublishCourse();
        setName(publishCourse.getCourse().getName());
        setTerm(publishCourse.calcTerm());
        selectCourseBeans = new ArrayList<>();
        for (SelectCourse selectCourse: courseClass.getSelectCourses()) {
            if (selectCourse.getQuitTime() == null)
                selectCourseBeans.add(new SelectCourseBean(selectCourse));
        }
    }
}
