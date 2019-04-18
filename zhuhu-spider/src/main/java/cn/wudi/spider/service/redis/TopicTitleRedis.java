package cn.wudi.spider.service.redis;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.CreateResult;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 */
@Service
public class TopicTitleRedis {

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  public String get(CommonQuery query) {
    return stringRedisTemplate.opsForValue().get(query.getTopicTitle());
  }

  public void set(CreateResult createResult) {
    stringRedisTemplate.opsForValue().set(createResult.getTopicTitle(), createResult.getTopicId());
  }
}
