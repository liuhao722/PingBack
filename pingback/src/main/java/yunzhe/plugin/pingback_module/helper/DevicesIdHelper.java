package yunzhe.plugin.pingback_module.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * Author:  LiuHao
 * Email:   114650501@qq.com
 * TIME:    2019-11-29 --> 10:34
 * Description: DevicesIdHelper 简述：统一处理设备id的获取方式，分为
 */
public class DevicesIdHelper {
    private static final String TAG = "DevicesIdHelper";
    private static final String PREFS_DEVICE_ID = "key_device_id";  //  正常的获取Token
    private static final String PREFS_DEVICE_ID_APPEND_TAG = "key_device_id_append_tag";//  正常的获取Token追加了标志 比如I(xxx)

    private static String appDevicesID;
    private static String buryPointDevicesId;

    /**
     * 获取token（方式为根据设备信息生成或者UUID生成）
     */
    @SuppressLint("MissingPermission")
    public static synchronized String getAndroidDevicesIdOrUUID(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("YZSharedPreferences", 0);
        appDevicesID = prefs.getString(PREFS_DEVICE_ID, null);
        if (isAboveAndroidPVersion()) {  //  大于安卓O版本
            if (TextUtils.isEmpty(appDevicesID)) {
                String serial;
                String m_szDevIDShort = "35" +
                        Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                        Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                        Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                        Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                        Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                        Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                        Build.USER.length() % 10; //13 位

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        serial = android.os.Build.getSerial();
                    } else {
                        serial = Build.SERIAL;
                    }
                    //API>=9 使用serial号
                    appDevicesID = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
                    prefs.edit().putString(PREFS_DEVICE_ID, appDevicesID).commit();
                    return appDevicesID;

                } catch (Exception exception) {
                    //serial需要一个初始化
                    serial = "serial"; // 随便一个初始化
                }
                //使用硬件信息拼凑出来的15位号码
                appDevicesID = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
                prefs.edit().putString(PREFS_DEVICE_ID, appDevicesID).commit();
                return appDevicesID;
            } else {
                return appDevicesID;
            }

        } else {
            String imei = null;
            try {
                imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(imei)) {
                if (TextUtils.isEmpty(appDevicesID) || isFull(appDevicesID, '0') || isFull(appDevicesID, '*')) {
                    appDevicesID = UUID.randomUUID().toString();
                }
            } else {
                appDevicesID = imei;
            }
            prefs.edit().putString(PREFS_DEVICE_ID, appDevicesID).commit();
            return appDevicesID;
        }
    }

    /**
     * 获取专属的埋点id或者UUID
     */
    @SuppressLint("MissingPermission")
    public static String getBuryPointAndroidDevicesIdOrUUID(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("YZSharedPreferences", 0);
        buryPointDevicesId = prefs.getString(PREFS_DEVICE_ID_APPEND_TAG, null);
        if (isAboveAndroidPVersion()) {  //  大于安卓O版本
            if (TextUtils.isEmpty(buryPointDevicesId)) {
                String serial;
                String m_szDevIDShort = "35" +
                        Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                        Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                        Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                        Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                        Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                        Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                        Build.USER.length() % 10; //13 位

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        serial = android.os.Build.getSerial();
                    } else {
                        serial = Build.SERIAL;
                    }
                    //API>=9 使用serial号
                    buryPointDevicesId = "U(" + new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString() + ")";
                    prefs.edit().putString(PREFS_DEVICE_ID_APPEND_TAG, buryPointDevicesId).commit();
                    return buryPointDevicesId;

                } catch (Exception exception) {
                    //serial需要一个初始化
                    serial = "serial"; // 随便一个初始化
                }
                //使用硬件信息拼凑出来的15位号码
                buryPointDevicesId = "U(" + new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString() + ")";
                prefs.edit().putString(PREFS_DEVICE_ID_APPEND_TAG, buryPointDevicesId).commit();
                return buryPointDevicesId;
            } else {
                return buryPointDevicesId;
            }

        } else {
            String imei = "";
            try {
                imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (isEmpty(imei)) {
                if (isEmpty(buryPointDevicesId) || isFull(buryPointDevicesId, '0') || isFull(buryPointDevicesId, '*')) {
                    buryPointDevicesId = UUID.randomUUID().toString();
                    buryPointDevicesId = "U(" + buryPointDevicesId + ")";
                }
            } else {
                buryPointDevicesId = "I(" + imei + ")";
            }
            prefs.edit().putString(PREFS_DEVICE_ID_APPEND_TAG, buryPointDevicesId).commit();
            return buryPointDevicesId;
        }
    }

    static boolean isEmpty(String content) {
        return TextUtils.isEmpty(content);
    }

    static boolean isFull(String source, char target) {
        for (char ch : source.toCharArray()) {
            if (target != ch) {
                return false;
            }
        }
        return true;
    }

    static boolean isAboveAndroidPVersion() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.P;
    }

}
