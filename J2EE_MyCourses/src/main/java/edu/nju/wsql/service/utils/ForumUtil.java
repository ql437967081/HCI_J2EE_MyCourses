package edu.nju.wsql.service.utils;

import edu.nju.wsql.beans.PostBean;
import edu.nju.wsql.beans.TopicBean;
import edu.nju.wsql.beans.TopicListBean;
import edu.nju.wsql.beans.TopicSimpleBean;
import edu.nju.wsql.model.Course;
import edu.nju.wsql.model.Post;
import edu.nju.wsql.model.Topic;
import edu.nju.wsql.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ForumUtil {
    public static TopicListBean getAllTopics(Course course) {
        List<TopicSimpleBean> list = new ArrayList<>();
        for (Topic topic: course.getForum())
            list.add(new TopicSimpleBean(topic));
        return new TopicListBean(list);
    }

    public static Topic newTopic(Course course, User user, String title, String content) {
        Topic topic = new Topic();
        topic.setCourse(course);
        topic.setTitle(title);
        Post post = getNewPost(topic, user, content);
        topic.setFirstPost(post);
        topic.setLastPost(post);
        Set<Post> posts = new HashSet<>();
        posts.add(post);
        topic.setPosts(posts);
        return topic;
    }

    public static TopicBean getTopicPosts(Topic topic) {
        TopicBean bean = new TopicBean();
        bean.setTitle(topic.getTitle());
        List<PostBean> list = new ArrayList<>();
        for (Post post: topic.getPosts())
            list.add(new PostBean(post));
        bean.setPosts(list);
        return bean;
    }

    public static void sayOnTopic(Topic topic, User user, String content) {
        Post post = getNewPost(topic, user, content);
        topic.setLastPost(post);
        topic.getPosts().add(post);
    }

    private static Post getNewPost(Topic topic, User user, String content) {
        Post post = new Post();
        post.setTopic(topic);
        post.setPoster(user);
        post.setContent(content);
        post.setTime(LocalDateTime.now());
        return post;
    }
}
