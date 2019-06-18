package edu.nju.wsql.dao;

import edu.nju.wsql.model.Topic;

public interface TopicDao {
    void save(Topic topic);

    Topic findOne(long id);
}
