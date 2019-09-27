package yunzhe.plugin.pingback_module.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import yunzhe.plugin.pingback_module.utils.YZHashMap;
import yunzhe.plugin.pingback_module.utils.YZJSONObject;

/**
 * 埋点辅助类
 * PingBackHelper.ins().initKeys(new SensorKeys());
 * PingBackHelper.getMap()
 */
public class PingBackHelper {

    static YZHashMap<String, YZHashMap<String, Object>> saveEventKeysHashMap = new YZHashMap<>();   //  保存所有EventTag对应实体类属性的HashMap

    private volatile static PingBackHelper instance;

    public static PingBackHelper ins() {
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
     * 一般放在最先启动APP的地方进行初始化
     */
    public void initKeys(Object keysClass) {
        try {
            Class<?> clz = keysClass.getClass();                                                    //  拿到该类 获取实体类的所有属性，返回Field数组
            clz.newInstance();
            Field[] fields = clz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field filed = fields[i];
                filed.setAccessible(true);
                if (Modifier.isPublic(filed.getModifiers())) {
                    Object value = filed.get(keysClass);
                    if (value == null)
                        continue;                                                                   //  反射的属性，会多出现第一个null，排在第一个
                    YZHashMap<String, Object> saveSensorsEntryPropertiesHashMap = new YZHashMap<>();
                    if (value != null) {
                        saveEventKeysHashMap.put(value.toString(), saveSensorsEntryPropertiesHashMap);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定的map对象可进行put
     *
     * @param eventKey 事件页面的Key
     */
    public static YZHashMap<String, Object> getMap(String eventKey) {
        if (instance == null) {                                                                     //  做一些特殊处理
            ins();
        }
        if (!saveEventKeysHashMap.containsKey(eventKey))                                            //  不包含该key则进行新建填充
            saveEventKeysHashMap.put(eventKey, new YZHashMap<String, Object>());
        return saveEventKeysHashMap.get(eventKey);
    }

    /**
     * 获取obj对象
     * 每次都是获取的新对象，所以当前页面一次性传值使用该方法
     */
    public static YZJSONObject getJSONObject() {
        return new YZJSONObject();
    }

    /**
     * 获取指定的属性
     *
     * @param eventKey 事件页面的Key
     */
    public static JSONObject getJSONObject(String eventKey) {
        if (instance == null) {                                                                     //  做一些特殊处理
            ins();
        }
        if (saveEventKeysHashMap.containsKey(eventKey)) {                                           //  包含该key则进行新建填充
            YZHashMap<String, Object> eventTagProperties = saveEventKeysHashMap.get(eventKey);
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
        if (instance == null) {                                                                     //  做一些特殊处理
            ins();
        }
        if (saveEventKeysHashMap.containsKey(eventKey)) {                                           //  清除并重置
            saveEventKeysHashMap.remove(eventKey);
            saveEventKeysHashMap.put(eventKey, new YZHashMap<String, Object>());
        }
    }

    /**
     * 根据context获取不同activity的label名称
     *
     * @param context
     * @return 对应的activity在AndroidManifest.xml中设置的label
     */
    public static String getActivityLabelName(Context context, Class className) {
        int labelRes = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            for (int i = 0; i < packageInfo.activities.length; i++) {
                ActivityInfo ai = packageInfo.activities[i];
                if (ai.name.equals(className.getName())) {
                    labelRes = ai.labelRes;
                    break;
                }
            }
            if (labelRes != 0)
                return context.getString(labelRes);
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据context获取不同activity的label名称
     */
    @SuppressLint("WrongConstant")
    public static String getActivityLabelName(Context context) {
        try {
            ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(((Activity) context).getComponentName(), PackageManager.GET_ACTIVITIES);
            int labelRes = activityInfo.labelRes;
            String activityName = activityInfo.name;
            return context.getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
