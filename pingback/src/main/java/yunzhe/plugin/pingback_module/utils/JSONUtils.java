package yunzhe.plugin.pingback_module.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取json对象
 */
public class JSONUtils {
    /**
     * 获取obj对象
     */
    public static SensorsJSONObject getJSONObject() {
        return new SensorsJSONObject();
    }

    /**
     * 获取array对象
     */
    public static SensorsJSONArray getJSONArray() {
        return new SensorsJSONArray();
    }

    public static class SensorsJSONObject {
        private SensorsJSONObject() {
        }

        JSONObject jsonObject = new JSONObject();

        public SensorsJSONObject put(String key, Object value) {
            try {
                jsonObject.put(key, value);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

        public JSONObject create() {
            return jsonObject;
        }
    }

    public static class SensorsJSONArray {
        private SensorsJSONArray() {
        }

        JSONArray jsonArray = new JSONArray();

        public SensorsJSONArray put(String itemData) {
            jsonArray.put(itemData);
            return this;
        }

        public JSONArray create() {
            return jsonArray;
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
