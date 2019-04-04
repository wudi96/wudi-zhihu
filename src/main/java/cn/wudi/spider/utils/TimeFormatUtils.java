package cn.wudi.spider.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wudi
 */
public class TimeFormatUtils {

  public static String valueOf(Date date, String pattern) {
    if (date == null) {
      return null;
    } else {
      SimpleDateFormat formatter = new SimpleDateFormat(pattern);
      return formatter.format(date);
    }
  }
}
