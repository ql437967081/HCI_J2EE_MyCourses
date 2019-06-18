package edu.nju.wsql.service.utils;

import edu.nju.wsql.beans.TermCourseInfoBean;
import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.enums.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermUtil {
    public static Map<Integer, List<Season>> getAllTerms(List<Object[]> listOfTerms) {
        Map<Integer, List<Season>> terms = new HashMap<>();
        for (Object[] pair: listOfTerms) {
            int year = (int) pair[0];
            Season season = (Season) pair[1];
            if (terms.containsKey(year))
                terms.get(year).add(season);
            else {
                List<Season> list = new ArrayList<>();
                list.add(season);
                terms.put(year, list);
            }
        }
        return terms;
    }

    public static Map<Long, String> getCourses(List<Object[]> listOfCourses) {
        Map<Long, String> courses = new HashMap<>();
        for (Object[] pair: listOfCourses)
            courses.put((long) pair[0], (String) pair[1]);
        return courses;
    }

    public static Map<String, String> getTeachers(List<Object[]> listOfTeachers) {
        Map<String, String> teachers = new HashMap<>();
        for (Object[] pair: listOfTeachers)
            teachers.put((String) pair[0], (String) pair[1]);
        return teachers;
    }

    public static TermCourseInfoBean wrapSimpleTermCourseInfo(PublishCourse publishCourse) {
        TermCourseInfoBean bean = new TermCourseInfoBean();
        if (publishCourse != null) {
            Course course = publishCourse.getCourse();
            bean.setCourseId(course.getId());
            bean.setName(course.getName());
            bean.setYear(publishCourse.getYear());
            bean.setSeason(publishCourse.getSeason().toChinese());
            bean.setPublisher(course.getCreator().getName());
        }
        return bean;
    }
}
