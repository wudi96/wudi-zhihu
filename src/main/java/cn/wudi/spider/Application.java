package cn.wudi.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wudi
 */
@SpringBootApplication
@ComponentScan(
    basePackages = {
        "cn.wudi.spider.controller",
        "cn.wudi.spider.service",
    }
)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}