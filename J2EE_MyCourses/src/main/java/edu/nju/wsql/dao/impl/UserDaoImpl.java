package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.UserDao;
import edu.nju.wsql.model.User;
import edu.nju.wsql.model.enums.UserType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractHibernateDAO<User> implements UserDao {
    public UserDaoImpl() {
        setClazz(User.class);
    }

    @Override
    public User findOne(String id) {
        return super.findOne(id);
    }

    @Override
    public void validate(String id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("valid"), true);
        updateWhereAndExecute(helper, id);
    }

    @Override
    public void update(String id, String password, String name) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("password"), password);
        helper.criteriaUpdate.set(helper.root.get("name"), name);
        updateWhereAndExecute(helper, id);
    }

    @Override
    public String findName(String id) {
        return getOneAttribute(id, "name", String.class);
    }

    @Override
    public void updateName(String id, String name) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("name"), name);
        updateWhereAndExecute(helper, id);
    }

    @Override
    public void updatePortrait(String id, String portrait) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("portrait"), portrait);
        updateWhereAndExecute(helper, id);
    }

    @Override
    public void invalidate(String id) {
        UpdateHelper helper = getUpdateHelper();
        helper.criteriaUpdate.set(helper.root.get("valid"), false);
        updateWhereAndExecute(helper, id);
    }

    @Override
    public List<Object[]> findAllTeachers() {
        MultiselectHelper helper = getMultiselectHelper();
        helper.criteriaQuery.multiselect(helper.root.get("id"), helper.root.get("name"));
        helper.criteriaQuery.distinct(true);
        helper.criteriaQuery.where(helper.criteriaBuilder.equal(helper.root.get("type"), UserType.TEACHER));
        return helper.findAll();
    }

    @Override
    public List<User> getAllTeachers() {
        SelectHelper helper = getSelectHelper();
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("type"), UserType.TEACHER),
                helper.criteriaBuilder.equal(helper.root.get("valid"), true)
        ));
        return helper.findAll();
    }

    @Override
    public List<User> getAllStudents() {
        SelectHelper helper = getSelectHelper();
        helper.criteriaQuery.where(helper.criteriaBuilder.and(
                helper.criteriaBuilder.equal(helper.root.get("type"), UserType.STUDENT),
                helper.criteriaBuilder.equal(helper.root.get("valid"), true)
        ));
        return helper.findAll();
    }

    private int updateWhereAndExecute(UpdateHelper helper, String id) {
        helper.criteriaUpdate.where(helper.criteriaBuilder.equal(helper.root.get("id"), id));
        return helper.execute();
    }
}
