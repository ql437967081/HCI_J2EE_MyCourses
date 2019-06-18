package edu.nju.wsql.dao;

import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.enums.Season;

import java.util.List;

public interface PublishCourseDao {
    void save(PublishCourse publishCourse);

    PublishCourse findOneByYearAndSeasonAndCourse(int year, Season season, long course);

    PublishCourse findOne(long id);

    List<PublishCourse> findAllSubmitPublishCourses();

    void approvePublishCourse(long id);

    void rejectPublishCourse(long id);

    void readRejectedPublishCourse(long id);

    List<PublishCourse> findAllApprovedPublishCourses();

    List<PublishCourse> findAllApprovedPublishCoursesByTeacher(String teacher);

    List<PublishCourse> findAllApprovedPublishCoursesByTeacherAndYearAndSeason(String teacher, int year, Season season);

    List<Object[]> findAllTerms();

    List<Object[]> findAllTermsByTeacher(String teacher);

    List<Object[]> findAllTermsByCourseId(long course);

    List<Object[]> findAllCourseIdAndCourseNamesByYearAndSeason(int year, Season season);

    List<Object[]> findAllTeacherIdAndTeacherNamesByYearAndSeason(int year, Season season);

    List<Object[]> findAllCourseIdAndCourseNamesByYearAndSeasonAndTeacher(int year, Season season, String teacher);
}
