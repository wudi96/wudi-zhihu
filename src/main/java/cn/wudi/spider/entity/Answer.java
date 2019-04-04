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

  /**
   * 知乎的回答ID
   */
  String id;

  /**
   * url
   */
  String url;

  /**
   * 赞同数目
   */
  @JSONField(name = "vote_count")
  String voteCount;

  /**
   * 评论数量，整数
   */
  @JSONField(name = "comment_count")
  String commentCount;

  /**
   * 建立时间, 格式是 YYYY-mm-dd HH:MM:SS
   */
  @JSONField(name = "create_time")
  String createTime;

  /**
   * 修改时间, 格式是 YYYY-mm-dd HH:MM:SS
   */
  @JSONField(name = "modify_time")
  String modifyTime;

  /**
   * 回答内容，HTML原文片段
   */
  String content;
}
