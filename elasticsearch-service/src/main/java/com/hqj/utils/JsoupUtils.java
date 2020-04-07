package com.hqj.utils;

import com.alibaba.fastjson.JSON;
import com.hqj.common.constant.Constant;
import com.hqj.common.exception.ServiceException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class JsoupUtils {
    private static final String USER_AGENT = "User-Agent";
    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    /**
     * 发送http请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doHttp(String url, Map<String, Object> params){
        try {
            Connection connection = Jsoup.connect(url);
            connection.header(USER_AGENT, USER_AGENT_VALUE)
                    .header(CONTENT_TYPE, APPLICATION_JSON);
            if(!CollectionUtils.isEmpty(params)) {
                connection.requestBody(JSON.toJSONString(params));
            }
            Connection.Response response = connection
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(120000)
                    .method(Connection.Method.POST)
                    .maxBodySize(0)
                    .execute();
            return new String(response.bodyAsBytes(), Constant.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 发送http请求
     *
     * @param url
     * @return
     */
    public static String doHttp(String url){
        return doHttp(url, null);
    }

}
