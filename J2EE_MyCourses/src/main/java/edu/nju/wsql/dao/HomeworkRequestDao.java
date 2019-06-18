package edu.nju.wsql.dao;

import edu.nju.wsql.model.HomeworkRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface HomeworkRequestDao {
    void save(HomeworkRequest homeworkRequest);

    HomeworkRequest findOne(long id);

    LocalDateTime findDdl(long id);

    String findTitle(long id);
}
