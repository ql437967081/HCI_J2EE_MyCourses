package edu.nju.wsql.dao;

import edu.nju.wsql.model.SelectCourse;
import edu.nju.wsql.model.enums.Season;

import java.time.LocalDateTime;
import java.util.List;

public interface SelectCourseDao {
    List<SelectCourse> findNonExitByStudentId(String id);

    void save(SelectCourse selectCourse);

    SelectCourse findOne(long id);

    void quitCourse(long id, LocalDateTime time);

    List<SelectCourse> findNonExitByStudentIdAndTeacherId(String student, String teacher);

    List<SelectCourse> findNonExitByStudentIdAndCourseId(String student, long course);

    List<SelectCourse> findNonExitByStudentIdAndYearAndSeason(String student, int year, Season season);

    List<SelectCourse> findNonExitByStudentIdAndYearAndSeasonAndTeacher(String student, int year, Season season, String teacher);

    List<SelectCourse> findNonExitByStudentIdAndYearAndSeasonAndCourse(String student, int year, Season season, long course);

    List<SelectCourse> findExitByStudentId(String student);

    List<SelectCourse> findExitByStudentIdAndTeacherId(String student, String teacher);

    List<SelectCourse> findExitByStudentIdAndCourseId(String student, long course);

    List<SelectCourse> findExitByStudentIdAndYearAndSeason(String student, int year, Season season);

    List<SelectCourse> findExitByStudentIdAndYearAndSeasonAndTeacher(String student, int year, Season season, String teacher);

    List<SelectCourse> findExitByStudentIdAndYearAndSeasonAndCourse(String student, int year, Season season, long course);

    SelectCourse findNonExitByStudentIdAndPublishCourseId(String student, long publishCourseId);
}
