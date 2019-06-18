package edu.nju.wsql.service;

import edu.nju.wsql.beans.*;
import edu.nju.wsql.model.CourseClass;
import edu.nju.wsql.model.Courseware;
import edu.nju.wsql.model.enums.Season;

import java.util.Map;
import java.util.Set;

public interface CourseService {
    long createNewCourse(String name, String teacher, Set<Courseware> coursewares);

    CourseBean getCourseInfo(long id);

    String getCourseName(long id);

    boolean existCourse(String name, String teacher);

    void removeCourseware(long wareId);

    void addCoursewares(long id, Set<Courseware> coursewares);

    PublishCourseBean getPublishInfo(String creator);

    long publishCourse(int year, Season season, long course, Set<CourseClass> classes);

    boolean existPublish(int year, Season season, long course);

    TermCourseInfoBean getTermCourseInfo(long id);

    CourseListBean getCoursesICreated(String teacher);

    void readRejectedCourse(long id);

    void readRejectedPublishCourse(long id);

    void publishHomework(long id, String title, String content, String ddl, String fileMaxSize, String fileType);

    CourseHomeworkListBean getHomeworkList(long id, boolean gradeNeed);

    Map<String, String> getHomeworkSubmitsByHomeworkRequestId(long id);

    String getHomeworkTitle(long id);

    void groupEmail(long publishCourseId, String title, String content);

    void publishGrade(long id, long project, String remark, boolean open, String location, Map<String, Double> grades);

    TopicListBean getAllTopics(long id);

    void newTopic(long courseId, String teacher, String title, String content);

    TopicBean getTopicPosts(long id);

    void sayOnTopic(long id, String teacher, String content);

    PublishApprovalListBean getMyPublishCourses(String teacher);

    PublishApprovalListBean getMyPublishCourses(String login, int year, Season season);

    SelectCourseListBean getSelectInfoOfClass(long id);

    TeacherStaticsListBean getAllTeacherStatics();

    StudentStaticsBean getStudentStatics();
}
