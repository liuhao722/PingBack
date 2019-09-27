package yunzhe.plugin.pingback_module.helper;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import yunzhe.plugin.pingback_module.utils.YZHashMap;

/**
 * 埋点辅助类
 */
public class PingBackHelper {
    //  保存所有EventTag对应实体类属性的HashMap
    static YZHashMap<String, YZHashMap<String, Object>> saveEventTagHashMap = new YZHashMap<>();

    private volatile static PingBackHelper instance;

    public static PingBackHelper getInstance() {
        if (instance == null) {
            synchronized (PingBackHelper.class) {
                if (instance == null) {
                    instance = new PingBackHelper();
                }
            }
        }
        return instance;
    }


    private PingBackHelper() {
    }

    /**
     * 初始化所有页面的key值--放所有的key进行初始化
     */
    public void initKeys(Object object) {
        reflectSensorsProperties(object);
    }

    /**
     * 获取指定的map对象可进行put
     *
     * @param eventKey 事件页面的Key
     */
    public static YZHashMap<String, Object> getMap(String eventKey) {
        if (instance == null) {                             //  做一些特殊处理
            getInstance();
        }
        if (!saveEventTagHashMap.containsKey(eventKey))     //  不包含该key则进行新建填充
            saveEventTagHashMap.put(eventKey, new YZHashMap<String, Object>());
        return saveEventTagHashMap.get(eventKey);
    }

    /**
     * 获取指定的属性
     *
     * @param eventKey 事件页面的Key
     */
    public static JSONObject getJSONObject(String eventKey) {
        if (instance == null) {                             //  做一些特殊处理
            getInstance();
        }
        if (saveEventTagHashMap.containsKey(eventKey)) {    //  包含该key则进行新建填充
            YZHashMap<String, Object> eventTagProperties = saveEventTagHashMap.get(eventKey);
            return new JSONObject(eventTagProperties);
        } else {
            return new JSONObject();
        }
    }

    /**
     * 汇报完事件以后对map内无用的参数进行清理；
     * 因为数据量太多 所以做清理操作
     *
     * @param eventKey 事件页面的Key
     */
    public static void removeEventKey(String eventKey) {
        if (instance == null) {                             //  做一些特殊处理
            getInstance();
        }
        if (saveEventTagHashMap.containsKey(eventKey)) {    //  清除并重置
            saveEventTagHashMap.remove(eventKey);
            saveEventTagHashMap.put(eventKey, new YZHashMap<String, Object>());
        }
    }

    /**
     * 反射所有页面的属性key
     *
     * @param object 类对象
     */
    private final void reflectSensorsProperties(Object object) {
        try {
            Class<?> clz = object.getClass();       // 拿到该类 获取实体类的所有属性，返回Field数组
            clz.newInstance();
            Field[] fields = clz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field filed = fields[i];
                filed.setAccessible(true);
                if (Modifier.isPublic(filed.getModifiers())) {
                    Object value = filed.get(object);
                    if (value == null) continue;//反射的属性，会多出现第一个null，排在第一个
                    YZHashMap<String, Object> saveSensorsEntryPropertiesHashMap = new YZHashMap<>();
                    if (value != null) {
                        saveEventTagHashMap.put(value.toString(), saveSensorsEntryPropertiesHashMap);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
