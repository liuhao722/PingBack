package yunzhe.plugin.pingback;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import yunzhe.plugin.pingback.keys.SensorKeys;
import yunzhe.plugin.pingback_module.helper.PingBackHelper;
import yunzhe.plugin.pingback_module.utils.YZHashMap;
import yunzhe.plugin.pingback_module.utils.YZJSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            pingBack();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 埋点汇报
     */
    private void pingBack() throws JSONException {

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

    }
}
