package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.CourseClassDao;
import edu.nju.wsql.model.CourseClass;
import org.springframework.stereotype.Repository;

@Repository
public class CourseClassDaoImpl extends AbstractHibernateDAO<CourseClass> implements CourseClassDao {
    public CourseClassDaoImpl() {
        setClazz(CourseClass.class);
    }

    @Override
    public CourseClass findOne(long id) {
        return super.findOne(id);
    }
}
