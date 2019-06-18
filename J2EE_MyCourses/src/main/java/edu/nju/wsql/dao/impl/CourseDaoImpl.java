package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.CourseDao;
import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.enums.ApprovalStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Repository
public class CourseDaoImpl extends AbstractHibernateDAO<Course> implements CourseDao {
    public CourseDaoImpl() {
        setClazz(Course.class);
    }

    @Override
    public Course findOne(long id) {
        return super.findOne(id);
    }

    @Override
    public String getName(long id) {
        return getOneAttribute(id, "name", String.class);
    }

    @Override
    public Course findOneByNameAndTeacher(String name, String teacher) {
        SelectHelper helper = getSelectHelper();
        Predicate nameEqual = helper.criteriaBuilder.equal(helper.root.get("name"), name);
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("creator").get("id"), teacher);
        Predicate statusNotRejectedRead = helper.criteriaBuilder.notEqual(helper.root.get("status"), ApprovalStatus.REJECTED_READ);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(nameEqual, teacherEqual, statusNotRejectedRead));
        return helper.findOne();
    }

    @Override
    public List<Course> findCoursesByCreator(String creator) {
        SelectHelper helper = getSelectHelper();
        Predicate teacherEqual = helper.criteriaBuilder.equal(helper.root.get("creator").get("id"), creator);
        Predicate statusNotRejectedRead = helper.criteriaBuilder.notEqual(helper.root.get("status"), ApprovalStatus.REJECTED_READ);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(teacherEqual, statusNotRejectedRead));
        return helper.findAll();
    }

    @Override
    public List<Course> findAllSubmitCourses() {
        SelectHelper helper = getSelectHelper();
        Predicate statusSubmit = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.SUBMIT);
        helper.criteriaQuery.where(statusSubmit);
        return helper.findAll();
    }

    @Override
    public void approveCourse(long id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("status"), ApprovalStatus.APPROVED);
        updateCourseStatusWhereAndExecute(helper, id);
    }

    @Override
    public void rejectCourse(long id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("status"), ApprovalStatus.REJECTED);
        updateCourseStatusWhereAndExecute(helper, id);
    }

    @Override
    public void readRejectedCourse(long id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("status"), ApprovalStatus.REJECTED_READ);
        Predicate idEqual = helper.criteriaBuilder.equal(helper.root.get("id"), id);
        Predicate statusRejected = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.REJECTED);
        helper.criteriaUpdate.where(helper.criteriaBuilder.and(idEqual, statusRejected));
        helper.execute();
    }

    @Override
    public List<Object[]> findAllIdAndNames() {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("id"), helper.root.get("name"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED));
        return helper.findAll();
    }

    @Override
    public List<Object[]> findAllIdAndNamesByTeacher(String teacher) {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("id"), helper.root.get("name"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.APPROVED),
                helper.criteriaBuilder.equal(helper.root.get("creator").get("id"), teacher)
        ));
        return helper.findAll();
    }

    @Override
    public String findTeacherId(long id) {
        return getOneAttribute(id, "creator.id", String.class);
    }

    private void updateCourseStatusWhereAndExecute(UpdateHelper helper, long id) {
        Predicate idEqual = helper.criteriaBuilder.equal(helper.root.get("id"), id);
        Predicate statusSubmit = helper.criteriaBuilder.equal(helper.root.get("status"), ApprovalStatus.SUBMIT);
        helper.criteriaUpdate.where(helper.criteriaBuilder.and(idEqual, statusSubmit));
        helper.execute();
    }

}
