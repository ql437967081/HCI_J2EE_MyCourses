package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.TopicDao;
import edu.nju.wsql.model.Topic;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDaoImpl extends AbstractHibernateDAO<Topic> implements TopicDao {
    public TopicDaoImpl() {
        setClazz(Topic.class);
    }

    @Override
    public Topic findOne(long id) {
        return super.findOne(id);
    }
}
