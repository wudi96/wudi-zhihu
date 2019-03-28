package cn.wudi.spider.logger.support;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * @author wudi
 */
public class DateUtils {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.CHINA);
  private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
  private static final DateTimeFormatter YEAR_MONTH_DAT = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .toFormatter(Locale.CHINA);

  public static String cTime() {
    return Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZONE_ID)
        .format(DATE_TIME_FORMATTER);
  }

  static String yearMonthDay() {
    return Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZONE_ID).format(YEAR_MONTH_DAT);
  }
}
