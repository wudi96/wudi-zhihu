package cn.wudi.spider.service.context;

import com.alibaba.fastjson.util.TypeUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class MemStore implements Store {

  /**
   * 通常情况下{@link Store}里的{@link Entry}个数小于20，用{@link java.util.Map}有点浪费
   */
  private List<Entry> entries;

  public MemStore() {
    this.entries = new LinkedList<>();
  }

  @Override
  public Object set(String key, Object value) {
    for (Entry entry : entries) {
      if (entry.getKey().equals(key)) {
        Object lastValue = entry.getValue();
        // 存在则replace
        entry.setValue(value);
        return lastValue;
      }
    }
    entries.add(new Entry(key, value));
    return null;
  }

  @Override
  public Object setIfAbsent(String key, Object value) {
    Object o = get(key);
    if (o == null) {
      entries.add(new Entry(key, value));
    }
    return o;
  }

  @Override
  public Object get(String key) {
    for (Entry entry : entries) {
      if (entry.getKey().equals(key)) {
        return entry.getValue();
      }
    }
    return null;
  }

  @Override
  public <T> T get(String key, Class<T> clazz) {
    for (Entry entry : entries) {
      if (!entry.getKey().equals(key)) {
        continue;
      }
      Object o = entry.getValue();
      if (o == null) {
        return null;
      }
      T t = TypeUtils.castToJavaBean(o, clazz);
      if (t != o) {
        entry.setValue(t);
      }
      return t;
    }
    return null;
  }

  @Override
  public Object remove(String key) {
    for (Iterator<Entry> it = entries.iterator(); it.hasNext(); ) {
      Entry entry = it.next();
      if (entry.getKey().equals(key)) {
        Object lastValue = entry.getValue();
        it.remove();
        return lastValue;
      }
    }
    return null;
  }

  @Override
  public String getString(String key) {
    Object o = get(key);
    if (!(o instanceof String)) {
      return null;
    }
    return (String) o;
  }

  @Override
  public String getStringDefault(String key, String def) {
    String value = getString(key);
    if (value == null) {
      return def;
    }
    return value;
  }

  @Override
  public Integer getInteger(String key) {
    Object o = get(key);
    if (!(o instanceof Integer)) {
      return null;
    }
    return (Integer) o;
  }

  @Override
  public Integer getIntegerDefault(String key, Integer def) {
    Integer value = getInteger(key);
    if (value == null) {
      return def;
    }
    return value;
  }

  @Override
  public Boolean getBoolean(String key) {
    Object o = get(key);
    if (!(o instanceof Boolean)) {
      return null;
    }
    return (Boolean) o;
  }

  @Override
  public Boolean getBooleanDefault(String key, Boolean def) {
    Boolean value = getBoolean(key);
    if (value == null) {
      return def;
    }
    return value;
  }

  @Override
  public Double getDouble(String key) {
    Object o = get(key);
    if (!(o instanceof Double)) {
      return null;
    }
    return (Double) o;
  }

  @Override
  public Double getDoubleDefault(String key, Double def) {
    Double value = getDouble(key);
    if (value == null) {
      return def;
    }
    return value;
  }

  @Override
  public void forEach(BiConsumer<String, Object> action) {
    entries.forEach(entry -> action.accept(entry.getKey(), entry.getValue()));
  }

  @Override
  public void clear() {
    entries.clear();
  }

  @Override
  public int size() {
    return entries.size();
  }

}
