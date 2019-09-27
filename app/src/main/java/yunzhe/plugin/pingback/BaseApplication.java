package yunzhe.plugin.pingback;

import android.app.Application;

import yunzhe.plugin.pingback.keys.SensorKeys;
import yunzhe.plugin.pingback_module.helper.PingBackHelper;

/**
 * Author:  LiuHao
 * Email:   114650501@qq.com
 * TIME:    2019-09-27 --> 11:33
 * Description: BaseApplication 简述：base类
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PingBackHelper.ins().initKeys(new SensorKeys());
    }
}
