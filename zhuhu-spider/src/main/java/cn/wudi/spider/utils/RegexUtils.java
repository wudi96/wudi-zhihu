package cn.wudi.spider.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wudi
 */
public class RegexUtils {

  private static final ConcurrentHashMap<String, Pattern> PATTERN_CACHED = new ConcurrentHashMap<>();

  public static Pattern getPattern(String regex) {
    return getPattern(regex, 0);
  }

  public static Pattern getPattern(String regex, int flags) {
    Pattern pattern = PATTERN_CACHED.get(regex + "-" + flags);
    if (pattern == null) {
      pattern = Pattern.compile(regex, flags);
      PATTERN_CACHED.putIfAbsent(regex, pattern);
      return pattern;
    }
    return pattern;
  }

  public static Matcher getMatcher(CharSequence input, String regex) {
    return getMatcher(input, regex, 0);
  }

  public static Matcher getMatcher(CharSequence input, String regex, int flags) {
    Pattern pattern = getPattern(regex, flags);
    return pattern.matcher(input);
  }
}
