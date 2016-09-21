package me.next.one.network;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.next.one.utils.AppLogger;

/**
 * Created by NeXT on 16/6/3.
 */
public class ParserAction {

    private static final List emptyList = new ArrayList();

    public static <T> List<T> getBeans(String content, Class<T> clazz) throws Exception {
        AppLogger.d("content : " + content);
        return JSON.parseArray(getDataString(content), clazz);
    }

    public static <T> T getBean(String content, Class<T> clazz) throws Exception {
        AppLogger.d("content : " + content);
        return JSON.parseObject(getDataString(content), clazz);
    }

    public static <T> List<T> getAVObjectByKey(String content, String key, Class<T> clazz) {
        try {
            return JSON.parseArray(getDataStringByKey(content, key), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String getDataStringByKey(String dataJsonString, String dataKey) {
        try {
            JSONObject jsonObject = new JSONObject(dataJsonString);
            return jsonObject.getString(dataKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
    {
      "hpEntity": {
        "strLastUpdateDate": "2016-09-17 18:33:56",
        "strDayDiffer": "",
        "strHpId": "1472",
        "strHpTitle": "VOL.1444",
        "strThumbnailUrl": "http://image.wufazhuce.com/FlfuxmKEt4_8ZSiPe4vglgjP9WGH",
        "strOriginalImgUrl": "http://image.wufazhuce.com/FlfuxmKEt4_8ZSiPe4vglgjP9WGH",
        "strAuthor": "阳光照进回忆&@黑桃花椒 作品",
        "strContent": "梦，真是人活着最奇怪的东西，那么私密亲切到仿佛跟自己都不好透露，可又遥远恍惚得好像跟你没相干，是个陌生人的造访。by 唐诺",
        "strMarketTime": "2016-09-20",
        "sWebLk": "http://m.wufazhuce.com/one/1472",
        "strPn": "10788",
        "wImgUrl": ""
      },
      "result": "SUCCESS"
    }
     */
    public static String getDataString(String content) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject(content);
            String result = jsonObject.getString("result");
            if ("SUCCESS".equals(result)) {
                String jsonData = jsonObject.getString("hpEntity");
                AppLogger.d("parse data : " + jsonData);
                return jsonData;
            } else {
                AppLogger.d("result code : " + result);
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}

