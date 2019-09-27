package yunzhe.plugin.pingback_module.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:  LiuHao
 * Email:   114650501@qq.com
 * TIME:    2019-09-27 --> 11:24
 * Description: YZJSONObject 简述：一个生成jsonObject的类，链式调用方式
 */
public class YZJSONObject {
    public YZJSONObject() {
    }

    JSONObject jsonObject = new JSONObject();

    public YZJSONObject put(String key, Object value) {
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
