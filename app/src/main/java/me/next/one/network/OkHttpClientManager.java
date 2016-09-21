package me.next.one.network;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by NeXT on 16/6/3.
 */
public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsync(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        return call.execute();
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsync(url);
        return execute.body().string();
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    //*************对外公布的方法************
    public static Response getAsync(String url) throws IOException {
        return getInstance()._getAsync(url);
    }

    public static String getAsyncStr(String url) throws IOException {
        return getInstance()._getAsync(url).body().string();
    }

    public static <T> List<T> getDataList(String url, Class<T> clazz) throws Exception {
        return ParserAction.getBeans(getInstance()._getAsString(url), clazz);
    }

    public static <T> List<T> getDataListByPost(String url, Param[] params, Class<T> clazz) throws Exception {
        return ParserAction.getBeans(getInstance()._postAsString(url, params), clazz);
    }

    public static <T> T getDataByPost(String url, Param[] params, Class<T> clazz) throws Exception {
        return ParserAction.getBean(getInstance()._postAsString(url, params), clazz);
    }

    public static Response download(String url) throws IOException {
        return getInstance()._download(url);
    }

    private Response _download(final String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        return mOkHttpClient.newCall(request).execute();
    }

    public static class Param {

        String key;
        String value;

        public Param() {}

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
