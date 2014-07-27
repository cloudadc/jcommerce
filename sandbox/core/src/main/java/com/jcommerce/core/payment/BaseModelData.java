package com.jcommerce.core.payment;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Default <code>ModelData</code> implementation.
 */
public class BaseModelData implements Serializable {

  protected Map map;

  /**
   * Creates a new model data instance.
   */
  public BaseModelData() {
  }

  /**
   * Creates a new model with the given properties.
   * 
   * @param properties the initial properties
   */
  public BaseModelData(Map<String, Object> properties) {
    super();
    setProperties(properties);
  }

  public <X> X get(String property) {
    return map == null ? null : (X) map.get(property);
  }

  /**
   * Returns a property value.
   * 
   * @param property the property name
   * @param valueWhenNull
   * @return the value
   */
  public <X> X get(String property, X valueWhenNull) {
    X value = (X) get(property);
    return (value == null) ? valueWhenNull : value;
  }

  public Map<String, Object> getProperties() {
    HashMap<String, Object> newMap = new HashMap<String, Object>();
    if (map != null) {
      newMap.putAll(map);
    }
    return newMap;
  }

  public Collection<String> getPropertyNames() {
    Set<String> set = new HashSet<String>();
    if (map != null) {
      set.addAll(map.keySet());
    }
    return set;
  }


  public <X> X remove(String property) {
    return map == null ? null : (X) map.remove(property);
  }

  /**
   * Sets the property and fires an <i>Update</i> event.
   * 
   * @param property the property name
   * @param value the property value
   */
  public <X> X set(String property, X value) {
    if (map == null) {
      map = new HashMap();
    }
    return (X) map.put(property, value);
  }


  /**
   * Sets the properties.
   * 
   * @param properties the properties
   */
  public void setProperties(Map<String, Object> properties) {
    for (String property : properties.keySet()) {
      set(property, properties.get(property));
    }
  }

  @Override
  public String toString() {
    return map == null ? "empty" : map.toString();
  }

}

