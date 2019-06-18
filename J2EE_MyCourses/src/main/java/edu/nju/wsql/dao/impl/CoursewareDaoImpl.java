package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.CoursewareDao;
import edu.nju.wsql.model.Courseware;
import org.springframework.stereotype.Repository;

@Repository
public class CoursewareDaoImpl extends AbstractHibernateDAO<Courseware> implements CoursewareDao {
    public CoursewareDaoImpl() {
        setClazz(Courseware.class);
    }

    @Override
    public void deleteById(long id) {
        super.deleteById(id);
    }
}
