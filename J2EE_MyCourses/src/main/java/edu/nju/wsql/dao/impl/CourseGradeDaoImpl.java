package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.CourseGradeDao;
import edu.nju.wsql.model.CourseGrade;
import org.springframework.stereotype.Repository;

@Repository
public class CourseGradeDaoImpl extends AbstractHibernateDAO<CourseGrade> implements CourseGradeDao {
    public CourseGradeDaoImpl() {
        setClazz(CourseGrade.class);
    }
}
