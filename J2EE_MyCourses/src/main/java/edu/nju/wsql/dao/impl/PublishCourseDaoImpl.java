package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.PublishCourseDao;
import edu.nju.wsql.model.PublishCourse;
import edu.nju.wsql.model.enums.ApprovalStatus;
import edu.nju.wsql.model.enums.Season;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Repository
public class PublishCourseDaoImpl extends AbstractHibernateDAO<PublishCourse> implements PublishCourseDao {
    public PublishCourseDaoImpl() {
        setClazz(PublishCourse.class);
    }

    @Override
    public PublishCourse findOneByYearAndSeasonAndCourse(int year, Season season, long course) {
        SelectHelper helper = getSelectHelper();
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("season"), season);
        Predicate courseEqual = helper.criteriaBuilder.equal(helper.root.get("course").get("id"), course);
        Predicate statusNotRejected = helper.criteriaBuilder.notEqual(helper.root.get("status"), ApprovalStatus.REJECTED_READ);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(yearEqual, seasonEqual, courseEqual, statusNotRejected));
        return helper.findOne();
    }

    @Override
    public PublishCourse findOne(long id) {
        return super.findOne(id);
    }

    @Override
    public List<PublishCourse> findAllSubmitPublishCourses() {
        SelectHelper helper = getSelectHelper();
        Predicate statusSubmit = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.SUBMIT);
        helper.criteriaQuery.where(statusSubmit);
        return helper.findAll();
    }

    @Override
    public void approvePublishCourse(long id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("status"), ApprovalStatus.APPROVED);
        updateCourseStatusWhereAndExecute(helper, id);
    }

    @Override
    public void rejectPublishCourse(long id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("status"), ApprovalStatus.REJECTED);
        updateCourseStatusWhereAndExecute(helper, id);
    }

    @Override
    public void readRejectedPublishCourse(long id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("status"), ApprovalStatus.REJECTED_READ);
        Predicate idEqual = helper.criteriaBuilder.equal(helper.root.get("id"), id);
        Predicate statusRejected = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.REJECTED);
        helper.criteriaUpdate.where(helper.criteriaBuilder.and(idEqual, statusRejected));
        helper.execute();
    }

    @Override
    public List<PublishCourse> findAllApprovedPublishCourses() {
        SelectHelper helper = getSelectHelper();
        Predicate statusApproved = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED);
        helper.criteriaQuery.where(statusApproved);
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<PublishCourse> findAllApprovedPublishCoursesByTeacher(String teacher) {
        SelectHelper helper = getSelectHelper();
        Predicate statusApproved = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("course").get("creator").get("id"), teacher);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(statusApproved, teacherEqual));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<PublishCourse> findAllApprovedPublishCoursesByTeacherAndYearAndSeason(String teacher, int year, Season season) {
        SelectHelper helper = getSelectHelper();
        Predicate statusApproved = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("course").get("creator").get("id"), teacher);
        Predicate yearEqual = helper.criteriaBuilder.equal(helper.root.get("year"), year);
        Predicate seasonEqual = helper.criteriaBuilder.equal(helper.root.get("season"), season);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(statusApproved, teacherEqual, yearEqual, seasonEqual));
        helper.criteriaQuery.orderBy(helper.criteriaBuilder.desc(helper.root.get("id")));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllTerms() {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("year"), helper.root.get("season"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllTermsByTeacher(String teacher) {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("year"), helper.root.get("season"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED),
                helper.criteriaBuilder.equal(helper.root.get("course").get("creator").get("id"), teacher)
        ));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllTermsByCourseId(long course) {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("year"), helper.root.get("season"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED),
                helper.criteriaBuilder.equal(helper.root.get("course").get("id"), course)
        ));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllCourseIdAndCourseNamesByYearAndSeason(int year, Season season) {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("course").get("id"), helper.root.get("course").get("name"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("year"), year),
                helper.criteriaBuilder.equal(helper.root.get("season"), season),
                helper.criteriaBuilder.equal(helper.root.get("course").get("status"), ApprovalStatus.APPROVED)
        ));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllTeacherIdAndTeacherNamesByYearAndSeason(int year, Season season) {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(
                helper.root.get("course").get("creator").get("id"),
                helper.root.get("course").get("creator").get("name")
        );
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("year"), year),
                helper.criteriaBuilder.equal(helper.root.get("season"), season),
                helper.criteriaBuilder.equal(helper.root.get("course").get("status"), ApprovalStatus.APPROVED)
        ));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllCourseIdAndCourseNamesByYearAndSeasonAndTeacher(int year, Season season, String teacher) {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(
                helper.root.get("course").get("id"),
                helper.root.get("course").get("name")
        );
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("year"), year),
                helper.criteriaBuilder.equal(helper.root.get("season"), season),
                helper.criteriaBuilder.equal(helper.root.get("course").get("creator").get("id"), teacher),
                helper.criteriaBuilder.equal(helper.root.get("course").get("status"), ApprovalStatus.APPROVED)
        ));
        return helper.findAll();
    }

    private void updateCourseStatusWhereAndExecute(UpdateHelper helper, long id) {
        Predicate idEqual = helper.criteriaBuilder.equal(helper.root.get("id"), id);
        Predicate statusSubmit = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.SUBMIT);
        helper.criteriaUpdate.where(helper.criteriaBuilder.and(idEqual, statusSubmit));
        helper.execute();
    }
}
