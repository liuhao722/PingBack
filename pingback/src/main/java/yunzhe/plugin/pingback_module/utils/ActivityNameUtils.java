package yunzhe.plugin.pingback_module.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Author:  LiuHao
 * Email:   114650501@qq.com
 * TIME:    2019-09-27 --> 11:29
 * Description: ActivityNameUtils 简述：获取制定的activity的label名称
 */
public class ActivityNameUtils {
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
