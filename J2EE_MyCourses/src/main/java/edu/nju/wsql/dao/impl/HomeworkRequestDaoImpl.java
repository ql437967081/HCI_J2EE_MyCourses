package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.HomeworkRequestDao;
import edu.nju.wsql.model.HomeworkRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class HomeworkRequestDaoImpl extends AbstractHibernateDAO<HomeworkRequest> implements HomeworkRequestDao {
    public HomeworkRequestDaoImpl() {
        setClazz(HomeworkRequest.class);
    }

    @Override
    public HomeworkRequest findOne(long id) {
        return super.findOne(id);
    }

    @Override
    public LocalDateTime findDdl(long id) {
        return getOneAttribute(id, "ddl", LocalDateTime.class);
    }

    @Override
    public String findTitle(long id) {
        return getOneAttribute(id, "title", String.class);
    }
}
