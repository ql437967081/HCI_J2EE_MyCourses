package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.SelectCourseDao;
import edu.nju.wsql.model.SelectCourse;
import edu.nju.wsql.model.enums.Season;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SelectCourseDaoImpl extends AbstractHibernateDAO<SelectCourse> implements SelectCourseDao {
    public SelectCourseDaoImpl() {
        setClazz(SelectCourse.class);
    }

    @Override
    public List<SelectCourse> findNonExitByStudentId(String id) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), id);
        Predicate quitTimeNull = helper.criteriaBuilder.isNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public SelectCourse findOne(long id) {
        return super.findOne(id);
    }

    @Override
    public void quitCourse(long id, LocalDateTime time) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("quitTime"), time);
        helper.criteriaUpdate.where(helper.criteriaBuilder.equal(helper.root.get("id"), id));
        helper.execute();
    }

    @Override
    public List<SelectCourse> findNonExitByStudentIdAndTeacherId(String student, String teacher) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("creator").get("id"), teacher);
        Predicate quitTimeNull = helper.criteriaBuilder.isNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, teacherEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findNonExitByStudentIdAndCourseId(String student, long course) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate courseEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("id"), course);
        Predicate quitTimeNull = helper.criteriaBuilder.isNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, courseEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findNonExitByStudentIdAndYearAndSeason(String student, int year, Season season) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("season"), season);
        Predicate quitTimeNull = helper.criteriaBuilder.isNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, yearEqual, seasonEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findNonExitByStudentIdAndYearAndSeasonAndTeacher(String student, int year, Season season, String teacher) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("season"), season);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("creator").get("id"), teacher);
        Predicate quitTimeNull = helper.criteriaBuilder.isNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, yearEqual, seasonEqual, teacherEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findNonExitByStudentIdAndYearAndSeasonAndCourse(String student, int year, Season season, long course) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("season"), season);
        Predicate courseEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("id"), course);
        Predicate quitTimeNull = helper.criteriaBuilder.isNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, yearEqual, seasonEqual, courseEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findExitByStudentId(String student) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate quitTimeNotNull = helper.criteriaBuilder.isNotNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, quitTimeNotNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findExitByStudentIdAndTeacherId(String student, String teacher) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("creator").get("id"), teacher);
        Predicate quitTimeNull = helper.criteriaBuilder.isNotNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, teacherEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findExitByStudentIdAndCourseId(String student, long course) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate courseEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("id"), course);
        Predicate quitTimeNull = helper.criteriaBuilder.isNotNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, courseEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findExitByStudentIdAndYearAndSeason(String student, int year, Season season) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("season"), season);
        Predicate quitTimeNull = helper.criteriaBuilder.isNotNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, yearEqual, seasonEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findExitByStudentIdAndYearAndSeasonAndTeacher(String student, int year, Season season, String teacher) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("season"), season);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("creator").get("id"), teacher);
        Predicate quitTimeNull = helper.criteriaBuilder.isNotNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, yearEqual, seasonEqual, teacherEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<SelectCourse> findExitByStudentIdAndYearAndSeasonAndCourse(String student, int year, Season season, long course) {
        SelectHelper helper = getSelectHelper();
        Predicate studentEqual = helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("season"), season);
        Predicate courseEqual = helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("course").get("id"), course);
        Predicate quitTimeNull = helper.criteriaBuilder.isNotNull(helper.root.get("quitTime"));
        helper.criteriaQuery.where(helper.criteriaBuilder.and(studentEqual, yearEqual, seasonEqual, courseEqual, quitTimeNull));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public SelectCourse findNonExitByStudentIdAndPublishCourseId(String student, long publishCourseId) {
        SelectHelper helper = getSelectHelper();
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("student").get("id"), student),
                helper.criteriaBuilder.equal(helper.root.get("courseClass").get("publishCourse").get("id"), publishCourseId),
                helper.criteriaBuilder.isNull(helper.root.get("quitTime"))
        ));
        return helper.findOne();
    }
}
