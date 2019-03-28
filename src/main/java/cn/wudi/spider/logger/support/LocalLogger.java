package cn.wudi.spider.logger.support;

import static cn.wudi.spider.logger.support.DateUtils.yearMonthDay;

import cn.wudi.spider.concurrent.Once;
import cn.wudi.spider.logger.ILogger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author wudi
 */
@Slf4j
public class LocalLogger implements ILogger {

  private final String name;
  private final String homePath;
  private final Once once;
  private String path;
  private PrintWriter out;

  LocalLogger(String name, String homePath) {
    this.name = name;
    this.homePath = homePath;
    this.once = new Once();
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public ILogger println(String msg) {
    return info(msg);
  }

  @Override
  public ILogger println(Throwable throwable) {
    return info(null, throwable);
  }

  @Override
  public ILogger info(String format, Object... arguments) {
    try {
      initFile();
    } catch (IOException e) {
      log.error(name, e);
      return this;
    }
    FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
    out.println(formattingTuple.getMessage());
    Throwable throwable = formattingTuple.getThrowable();
    if (throwable == null) {
      return this;
    }
    throwable.printStackTrace(out);
    return this;
  }

  @Override
  public ILogger error(String format, Object... arguments) {
    return info(format, arguments);
  }

  @Override
  public ILogger writeFile(String filename, String msg) {
    return writeFile(filename, msg.getBytes());
  }

  @Override
  public ILogger writeFile(String filename, byte[] bytes) {
    once.doRun(this::createFile);
    try {
      Files.write(Paths.get(path + "/" + filename), bytes);
    } catch (IOException ignore) {
    }
    return this;
  }

  @Override
  public void close() {
    synchronized (this) {
      if (out != null) {
        out.flush();
        out.close();
      }
    }
  }

  private void initFile() throws IOException {
    once.doRun(this::createFile);
    if (out != null) {
      return;
    }
    synchronized (this) {
      if (out != null) {
        return;
      }
      out = new PrintWriter(new BufferedWriter(new FileWriter(path + "/logs.log", true)),
          true);
    }
  }

  private void createFile() {
    String time = yearMonthDay() + "/";
    path = homePath.endsWith("/") ? homePath + time + name : homePath + "/" + time + name;
    //noinspection ResultOfMethodCallIgnored
    new File(path).mkdirs();
  }
}
