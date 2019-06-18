package edu.nju.wsql.dao;

import edu.nju.wsql.model.Course;

import java.util.List;

public interface CourseDao {
    void save(Course course);

    Course findOne(long id);

    String getName(long id);

    Course findOneByNameAndTeacher(String name, String teacher);

    List<Course> findCoursesByCreator(String creator);

    List<Course> findAllSubmitCourses();

    void approveCourse(long id);

    void rejectCourse(long id);

    void readRejectedCourse(long id);

    List<Object[]> findAllIdAndNames();

    List<Object[]> findAllIdAndNamesByTeacher(String teacher);

    String findTeacherId(long id);
}
