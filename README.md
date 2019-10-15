

# PingBack埋点汇报辅助

## 使用方法：

##### AndroidStudio中gradle引入：

```java
implementation 'yunzhe.plugin:PingBack:1.0.2'
```

##### 如果遇到引入失败：出现以下错误

```java
ERROR: Failed to resolve: yunzhe.plugin:PingBack:1.0.2
```

##### 解决办法：

**那么请在项目根root中的build.gradle文件中添加**

```java
buildscript {
    repositories {
        google()
        maven { url "https://dl.bintray.com/liukun722914/Pingback" }
        jcenter()
    }
}

allprojects {
    repositories {
        google()
        maven { url "https://dl.bintray.com/liukun722914/Pingback" }
        jcenter()
    }
}
```

##### 初始化插件

```java
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PingBackHelper.ins().initKeys(new SensorKeys());
    }
}
```

##### 具体使用方式：

```java
//  方式一：根据key进行获取指定的集合，该集合根据个人情况进行决定是否清除原有的数据
YZHashMap<String, Object> map = PingBackHelper.getMap(SensorKeys.Authorization);
map.put("title", "个人中心");
map.put("desc", "点击了个人中心");
// send(request, map);

//  方式一衍生：不需要之前信息可以先调用clear进行清除
YZHashMap<String, Object> mapClear = PingBackHelper.getMap(SensorKeys.Authorization);
mapClear.clear();
mapClear.put("title", "个人中心");
mapClear.put("desc", "点击了个人中心");
// send(request, mapClear);

//  方式二 根据指定的Key获取已经存储的信息JsonObject对象
JSONObject jsonObject = PingBackHelper.getJSONObject(SensorKeys.Authorization);
jsonObject.put("time", System.currentTimeMillis());
// send(request, jsonObject);

//  方式三 获取一个新的JsonObject对象进行key-value存储
YZJSONObject jsonObjectInstance = PingBackHelper.getJSONObject();
jsonObjectInstance.put("title", "个人中心");
jsonObjectInstance.put("desc", "点击了个人中心");
// send(request, jsonObjectInstance);
```

