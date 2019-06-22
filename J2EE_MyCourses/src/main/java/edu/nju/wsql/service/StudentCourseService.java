package edu.nju.wsql.service;

import edu.nju.wsql.beans.*;

import java.time.LocalDateTime;

public interface StudentCourseService {
    StudentPublishCourseListBean getAllCourses(String student);

    long selectCourse(String student, long publishCourseId);

    StudentCourseClassBean getMyCourse(long id);

    StudentCourseClassBean quitCourse(long id);

    CourseBean getCoursewaresBySelectCourseId(long id);

    CourseHomeworkListBean getHomeworkListBySelectCourseId(long id);

    HomeworkBean getHomework(long id, long selectCourseId);

    void submitHomework(long id, long selectCourseId, String submitName, String location);

    LocalDateTime getDdlByHomeworkRequestId(long id);

    StudentGradeListBean getAllGrades(long id);

    TopicListBean getAllTopics(long id);

    void newTopic(long selectCourseId, String student, String title, String content);

    TopicBean getTopicPosts(long id);

    void sayOnTopic(long id, String student, String content);

    StudentPublishCourseListBean getMyCourses(String student);

    StudentPublishCourseListBean getMyCoursesByTermAndCourseAndTeacher(String student, String term, long course, String teacher);

    boolean isTheCourseISelected(long selectCourseId, String student);

    boolean isTheHomeworkOfTheCourse(long homeworkRequestId, long selectCourseId);
}
