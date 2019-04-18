package cn.wudi.spider.entity;

/**
 * @author wudi
 */

public enum ResultType {
  //成功
  SUCCESS("0", ""),
  ERR("1", "error");

  private final String code;
  private final String msg;

  ResultType(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }


  public String getMsg() {
    return msg;
  }
}
