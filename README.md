# PingBack埋点汇报辅助
---
使用方式
---

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PingBackHelper.ins().initKeys(new SensorKeys());
    }
}
