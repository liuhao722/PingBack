package yunzhe.plugin.pingback_module.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @Nullable
    @Override
    public V get(@NonNull Object key) {
        if (key == null) return null;
        return super.get(key);
    }

    @Override
    public boolean containsKey(@NonNull Object key) {
        if (key == null) return false;
        return super.containsKey(key);
    }

    @Nullable
    @Override
    public V put(@NonNull K key, @NonNull V value) {
        if (key == null || value == null) return null;
        return super.put(key, value);
    }
}
