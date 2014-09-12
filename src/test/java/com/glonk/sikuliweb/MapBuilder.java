package com.glonk.sikuliweb;

import java.util.HashMap;
import java.util.Map;



public class MapBuilder<K, V> {

  private final Map<K, V> keyVals = new HashMap<>();

  private MapBuilder() {
  }

  public static <K, V> MapBuilder<K, V> mapBuilder() {
    return new MapBuilder<>();
  }

  public MapBuilder<K, V> put(K key, V val) {
    keyVals.put(key, val);
    return this;
  }

  public Map<K, V> build() {
    return keyVals;
  }

}
