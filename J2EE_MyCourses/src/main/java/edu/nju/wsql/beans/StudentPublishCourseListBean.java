package edu.nju.wsql.beans;

import edu.nju.wsql.model.enums.Season;
import edu.nju.wsql.service.utils.SortUtil;

import java.util.List;
import java.util.Map;

public class StudentPublishCourseListBean {
    private List<StudentTermCourseBean> termCourseList;
    private List<StudentQuitCourseBean> quitCourseList;
    private List<String> terms;
    private String termSelection;
    private Map<Long, String> courses;
    private long courseSelection;
    private Map<String, String> teachers;
    private String teacherSelection;


    public StudentPublishCourseListBean() {
    }

    public StudentPublishCourseListBean(List<StudentTermCourseBean> termCourseList) {
        this.termCourseList = termCourseList;
    }

    public StudentPublishCourseListBean(List<StudentTermCourseBean> termCourseList, List<StudentQuitCourseBean> studentQuitCourseBeans, Map<Integer, List<Season>> allTerms, Map<Long, String> courses, Map<String, String> teachers) {
        this(termCourseList);
        setTerms(SortUtil.getSortedTerms(allTerms));
        setQuitCourseList(studentQuitCourseBeans);
        setCourses(courses);
        setTeachers(teachers);
        setTermSelection("all");
        setCourseSelection(-1);
        setTeacherSelection("all");
    }

    public StudentPublishCourseListBean(List<StudentTermCourseBean> termCourseList, List<StudentQuitCourseBean> studentQuitCourseBeans, Map<Integer, List<Season>> terms, Map<Long, String> courses, Map<String, String> teachers, String term, long course, String teacher) {
        this(termCourseList, studentQuitCourseBeans, terms, courses, teachers);
        setTermSelection(term);
        setCourseSelection(course);
        setTeacherSelection(teacher);
    }

    public List<StudentTermCourseBean> getTermCourseList() {
        return termCourseList;
    }

    public void setTermCourseList(List<StudentTermCourseBean> termCourseList) {
        this.termCourseList = termCourseList;
    }

    public List<StudentQuitCourseBean> getQuitCourseList() {
        return quitCourseList;
    }

    public void setQuitCourseList(List<StudentQuitCourseBean> quitCourseList) {
        this.quitCourseList = quitCourseList;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public String getTermSelection() {
        return termSelection;
    }

    public void setTermSelection(String termSelection) {
        this.termSelection = termSelection;
    }

    public Map<Long, String> getCourses() {
        return courses;
    }

    public void setCourses(Map<Long, String> courses) {
        this.courses = courses;
    }

    public long getCourseSelection() {
        return courseSelection;
    }

    public void setCourseSelection(long courseSelection) {
        this.courseSelection = courseSelection;
    }

    public Map<String, String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Map<String, String> teachers) {
        this.teachers = teachers;
    }

    public String getTeacherSelection() {
        return teacherSelection;
    }

    public void setTeacherSelection(String teacherSelection) {
        this.teacherSelection = teacherSelection;
    }
}
