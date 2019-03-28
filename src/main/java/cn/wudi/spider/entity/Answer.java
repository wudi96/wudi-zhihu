package cn.wudi.spider.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author wudi
 */
@Getter
@Setter
@Accessors(chain = true)
public class Answer {

  String id;

  String url;

  @JSONField(name = "vote_count")
  String voteCount;

  @JSONField(name = "comment_count")
  String commentCount;

  @JSONField(name = "create_time")
  String createTime;

  @JSONField(name = "modify_time")
  String modifyTime;

  String content;
}
