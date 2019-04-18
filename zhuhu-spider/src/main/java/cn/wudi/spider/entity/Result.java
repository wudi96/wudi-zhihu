package cn.wudi.spider.entity;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author wudi
 */
@Getter
@Setter
@Accessors(chain = true)
public class Result<T> implements Serializable {

  private String returnCode;
  private T data;
  private String message;

  public static <R> Result<R> ok() {
    return Result.ok(null);
  }

  public static <R> Result<R> ok(R data) {
    Result<R> result = new Result<>();
    result.returnCode = ResultType.SUCCESS.getCode();
    result.data = data;
    result.message = ResultType.SUCCESS.getMsg();
    return result;
  }

  public static <R> Result<R> fail(R data) {
    Result<R> result = new Result<>();
    result.returnCode = ResultType.ERR.getCode();
    result.data = data;
    result.message = ResultType.ERR.getMsg();
    return result;
  }

  @Override
  public String toString() {
    return JSONObject.toJSONString(this);
  }
}
