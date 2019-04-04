package cn.wudi.spider;

import cn.wudi.spider.http.IClient;
import cn.wudi.spider.http.Request;
import cn.wudi.spider.utils.TimeFormatUtils;
import java.util.Date;
import org.junit.Test;

public class TestCrawler {

  @Test
  public void crawler() {
    System.out.println(new Request().client(IClient.newBuilder().build()).GET()
        .url("https://www.zhihu.com/topic/19570679/top-answers").send().string());
  }

  @Test
  public void time(){
    System.out.println(TimeFormatUtils.valueOf(new Date(Long.valueOf("1522522385000")), "yyyy-MM-dd HH:mm:ss"));
  }
}
