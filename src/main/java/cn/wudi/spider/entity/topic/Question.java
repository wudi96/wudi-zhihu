package cn.wudi.spider.entity.topic;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author wudi
 */
@Getter
@Setter
@Accessors(chain = true)
public class Question {

  /**
   * 知乎的问题ID
   */
  String id;

  /**
   * 问题的 URL
   */
  String url;

  /**
   * 时间格式是 YYYY-mm-dd HH:MM:SS
   */
  @JSONField(name = "crawl_time")
  String crawlTime;

  /**
   * 纯文本标题
   */
  String title;

  /**
   * 评论数量，整数
   */
  @JSONField(name = "comment_count")
  String commentCount;

  /**
   * 关注者数量
   */
  @JSONField(name = "stare_count")
  String stareCount;

  /**
   * 浏览数量，整数
   */
  @JSONField(name = "browse_count")
  String browseCount;

  /**
   * 话题(标签)列表
   */
  List<String> tags;

  /**
   * 问题的细节，HTML原文片段
   */
  String detail;
}
