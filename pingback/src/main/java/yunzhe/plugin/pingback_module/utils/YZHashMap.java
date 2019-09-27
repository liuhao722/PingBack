package yunzhe.plugin.pingback_module.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:  LiuHao
 * Email:   114650501@qq.com
 * TIME:    2019-09-04 --> 11:31
 * Description: YZHashMap 简述：线程安全的map
 */
public class YZHashMap<K, V> extends ConcurrentHashMap<K, V> {
    public YZHashMap() {
        super();
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) return false;
        return super.containsKey(key);
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) return null;
        return super.put(key, value);
    }
}
