package cn.wudi.spider.service.context;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;

/**
 * @author wudi
 */
@JSONType(deserializer = Store.DeserializerStore.class)
public interface Store {

  /**
   * 如果存在key，则替换，不存在则存入并返回旧值
   */
  Object set(String key, Object value);

  /**
   * 如果存在key，则返回旧值
   */
  Object setIfAbsent(String key, Object value);

  /**
   * 根据key返回值
   */
  Object get(String key);

  /**
   * 根据key返回制定类型的数据
   */
  <T> T get(String key, Class<T> clazz);

  /**
   * 删除
   */
  Object remove(String key);

  String getString(String key);

  String getStringDefault(String key, String def);

  Integer getInteger(String key);

  Integer getIntegerDefault(String key, Integer def);

  Boolean getBoolean(String key);

  Boolean getBooleanDefault(String key, Boolean def);

  Double getDouble(String key);

  Double getDoubleDefault(String key, Double def);

  void forEach(BiConsumer<String, Object> action);

  void clear();

  /**
   * 返回数量
   */
  int size();

  /**
   * 默认的序列化方案
   */
  class DeserializerStore implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
      //noinspection unchecked
      return (T) parser.parseObject(MemStore.class);
    }

    @Override
    public int getFastMatchToken() {
      return JSONToken.LBRACE;
    }
  }
}
