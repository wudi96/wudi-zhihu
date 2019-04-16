package cn.wudi.spider.service.mongo;

import cn.wudi.spider.entity.topic.TopicContent;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 */
@Service
public class TopicContentMongo {

  @Resource
  private MongoTemplate mongoTemplate;

  public void set(TopicContent t) {
    mongoTemplate.insert(t);
  }

  public List<TopicContent> get(String s) {
    Query query = Query.query(Criteria.where("topicId").is(s));
    return mongoTemplate.find(query, TopicContent.class);
  }
}
