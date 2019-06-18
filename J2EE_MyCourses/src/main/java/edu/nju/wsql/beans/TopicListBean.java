package edu.nju.wsql.beans;

import java.util.List;

public class TopicListBean {
    private List<TopicSimpleBean> topicList;

    public TopicListBean() {
    }

    public TopicListBean(List<TopicSimpleBean> topicList) {
        this.topicList = topicList;
    }

    public List<TopicSimpleBean> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicSimpleBean> topicList) {
        this.topicList = topicList;
    }
}
