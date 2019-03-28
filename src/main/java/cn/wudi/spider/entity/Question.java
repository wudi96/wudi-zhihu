package cn.wudi.spider.entity;

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

  String id;

  String url;

  @JSONField(name = "crawl_time")
  String crawlTime;

  String title;

  @JSONField(name = "comment_count")
  String commentCount;

  @JSONField(name = "stare_count")
  String stareCount;

  @JSONField(name = "browse_count")
  String browseCount;

  List<String> tags;

  String detail;
}
